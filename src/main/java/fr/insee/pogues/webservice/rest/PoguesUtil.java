package fr.insee.pogues.webservice.rest;

import static fr.insee.pogues.utils.json.XPathVTL.parseToVTL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import fr.insee.pogues.transforms.JSONToXML;
import fr.insee.pogues.transforms.XMLToJSON;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Component
@Path("/util")

public class PoguesUtil {

	@Autowired
	JSONToXML jSONToXML;
	
	@Autowired
	XMLToJSON xMLToJSON;

    @Context
    HttpServletRequest request;


    final static Logger logger = LogManager.getLogger(PoguesUtil.class);


    @POST
    @Path("util/Xpath2VTL/questionnaire")
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
    public String parseQuestionnaireXptah2VTL(@RequestBody String json) throws Exception {
        try {
        	
        	String xml = jSONToXML.transform(json, null, null);
        	
        	String possibleNodes = "(Expression|Formula|Minimum|Maximum|Filter)";
    		Pattern pattern = Pattern.compile("(<"+possibleNodes+">)((.)*?)(</"+possibleNodes+">)");

    		Matcher matcher = pattern.matcher(xml);
    		StringBuffer stringBuffer = new StringBuffer();
    		while(matcher.find()){
    			//matcher.group(0)=all expression
    			//matcher.group(1)=start of xml node ex:<label>
    			//matcher.group(2)=name of xml node ex:label
    			//matcher.group(3)=content of xml node
    			//matcher.group(4)=last char in group(3) ?
    			//matcher.group(5)=end of xml node ex:</label>
    			//matcher.group(6)=name of xml node ex:label
    			String replacement = matcher.group(1) + parseToVTL(matcher.group(3)) + matcher.group(5);
    			matcher.appendReplacement(stringBuffer,"");
    			stringBuffer.append(replacement);
    		}
    		matcher.appendTail(stringBuffer);
    		stringBuffer.toString();
        	
    		String jsonf = xMLToJSON.transform(xml, null, null);
        	return jsonf;
        } catch (PoguesException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        
    }



}