package fr.insee.pogues.webservice.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.insee.pogues.transforms.JSONToXML;
import fr.insee.pogues.transforms.PipeLine;
import fr.insee.pogues.transforms.XMLToJSON;
import fr.insee.pogues.transforms.XpathToVTL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@Path("/util")
@Api(value = "Pogues Utils")
public class PoguesUtil {

	@Autowired
	private JSONToXML jSONToXML;
	
	@Autowired
	private XMLToJSON xMLToJSON;
	
	@Autowired
	private XpathToVTL xpathToVTL;

    @Context
    HttpServletRequest request;


    final static Logger logger = LogManager.getLogger(PoguesUtil.class);

    //No way to parameterize org.json.simple.JSONOBject by its design. It is the only way to suppress the warnings on questionnaireOut.put()
    @SuppressWarnings("unchecked")
	@POST
    @Path("Xpath2VTL/questionnaire")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @ApiOperation(
            value = "Parsing pogues questionnaire Xpath formulas into VTL formulas",
            notes = "Return a pogues questionnaire with VTL formulas from a pogues questionnaire with XPath formulas",
            response = String.class
    )
    @ApiImplicitParams(value = {
			@ApiImplicitParam(name = "json body", value = "JSON questionnaire", paramType = "body", dataType = "org.json.simple.JSONObject") })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Parsed"),
    })
    public String parseQuestionnaireXptah2VTL(@Context final HttpServletRequest request) throws Exception {
        try {
        	PipeLine pipeline = new PipeLine();
        	String questionnaireName = "VTL";
        	
        	StringWriter writer = new StringWriter();
            String encoding = StandardCharsets.UTF_8.name();
            IOUtils.copy(request.getInputStream(), writer, encoding);
        	String json = writer.toString();
        	
        	//We need to store the value for the owner and lastUpdatedDate as the transformation doesn't carry the information
        	JSONParser parser = new JSONParser();
			JSONObject questionnaire = (JSONObject) parser.parse(json);
			String owner = questionnaire.get("owner").toString();
			String lastUpdatedDate = questionnaire.get("lastUpdatedDate").toString();
			logger.info("Owner : "+owner+" and lastUpdatedDate: "+lastUpdatedDate);
			
        	String xml = jSONToXML.transform(json, null, null);
        	        	
        	String possibleNodes = "(Expression|Formula|Minimum|Maximum|Filter)";
        	Map<String, Object> params = new HashMap<>();
    		params.put("nodes", possibleNodes);
    		
    		String xmlOut="";
    		try {
    			StreamingOutput stream = output -> {
    				try {
    					output.write(pipeline.from(new ByteArrayInputStream(xml.getBytes()))
    							.map(xpathToVTL::transform, params, questionnaireName).transform().getBytes());
    				} catch (Exception e) {
    					logger.error(e.getMessage());
    					throw new PoguesException(500, e.getMessage(), null);
    				}
    			};
    			ByteArrayOutputStream StringOutput = new ByteArrayOutputStream();
    			stream.write(StringOutput);
    			xmlOut = new String(StringOutput.toByteArray(), "UTF-8");
    		} catch (Exception e) {
    			logger.error(e.getMessage(), e);
    			throw e;
    		}	
    		String jsonf = xMLToJSON.transform(xmlOut, null, null);
    		JSONObject questionnaireOut = (JSONObject) parser.parse(jsonf);
    		
    		questionnaireOut.put("owner",owner);
    		questionnaireOut.put("lastUpdatedDate",lastUpdatedDate);
    		
        	return questionnaireOut.toJSONString();
        } catch (PoguesException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        
    }



}