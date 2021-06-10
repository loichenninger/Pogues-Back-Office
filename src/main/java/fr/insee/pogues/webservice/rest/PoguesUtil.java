package fr.insee.pogues.webservice.rest;

import static fr.insee.pogues.utils.json.XPathVTL.parseToVTL;
import fr.insee.pogues.conversion.JSONDeserializer;
import fr.insee.pogues.model.Questionnaire;
import fr.insee.pogues.user.model.User;
import fr.insee.pogues.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Component
@Path("/util")

public class PoguesUtil {


    @Context
    HttpServletRequest request;

    @Autowired
    private UtilService utilService;

    final static Logger logger = LogManager.getLogger(PoguesUtil.class);


    @POST
    @Path("Xpath2VTL")
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
    public Response parseQuestionnaireXptah2VTL(@RequestBody JSONObject json) throws Exception {
        try {
        	JSONObject jsonParsed;
        	Object o=json.compute("Expression", (key,value) -> parseToVTL(value.toString())); 
            return null;
        } catch (PoguesException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }



}
.