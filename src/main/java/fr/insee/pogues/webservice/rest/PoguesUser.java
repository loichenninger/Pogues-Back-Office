package fr.insee.pogues.webservice.rest;

import fr.insee.pogues.user.model.User;
import fr.insee.pogues.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Component
@Path("/user")
@Tag(name = "Pogues Users")
public class PoguesUser {


    @Context
    HttpServletRequest request;

    @Autowired
    private UserService userService;

    final static Logger logger = LogManager.getLogger(PoguesUser.class);


    @GET
    @Path("id")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    @Operation(
    		operationId = "getID",
            summary = "Get current user id",
            description = "Get the user id of the current session user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created"),
            @ApiResponse(responseCode = "403", description = "Not authenticated")
    })
    public Response getID() throws Exception {
        try {
            JSONObject result = userService.getUserID(request);
            return Response.status(Status.OK).entity(result).build();
        } catch (PoguesException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }


    @GET
    @Path("attributes")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
    		operationId = "getAttribute",
            summary = "Get current user attribute",
            description = "Get the user id of the current session user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created"),
            @ApiResponse(responseCode = "403", description = "Not authenticated")
    })
    public Response getAttribute() throws Exception {
        try {
            User user = userService.getNameAndPermission(request);
            return Response.status(Status.OK).entity(user).build();
        } catch(Exception e){
            logger.error(e.getMessage(), e);
            throw e;
        }

    }


    @GET
    @Path("permissions")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
    		operationId = "getPermissions",
            summary = "Get all permissions",
            description = "Get a list of available permissions"/*,
            response = String.class,
            responseContainer = "List"*/
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
    })
    public Response getPermissions() throws Exception {
        try {
            return Response.ok(userService.getPermissions()).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

}
