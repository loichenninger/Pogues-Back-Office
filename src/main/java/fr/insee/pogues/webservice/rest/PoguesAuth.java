package fr.insee.pogues.webservice.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Component
@Path("/auth")
@Api(value = "Authentication")
public class PoguesAuth {
	
	/**
	 * Endpoint giving the type of authentication needed on the server
	 */

    @Context
    HttpServletRequest request;

    final static Logger logger = LogManager.getLogger(PoguesAuth.class);
    
    @Value("${fr.insee.pogues.authentication}")
	Boolean authentication;

    @GET
    @Path("init")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @ApiOperation(
            value = "Get the type of authentication",
            notes = "Get the type of authentication required by the server",
            response = String.class
    )
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Created"),
//            @ApiResponse(code = 403, message = "Not authenticated")
//    })
    public Response getAuthenticationType() throws Exception {
    	JSONObject authenticationType = new JSONObject();
    	if (authentication) {
    		authenticationType.put("authType", "oidc");
    	} else {
    		authenticationType.put("authType", "none");
    	}
    	return Response.status(Status.OK).entity(authenticationType).build();
    }
    
}
