/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Michael
 */
@Path("/message")
@ApplicationScoped
public class MessageService {

    @Inject
    private MessageController messageController;

    private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(messageController.getAllJson()).build();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        JsonObject json = messageController.getByIdJson(id);
        if (json != null) {
            return Response.ok(json).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{from}/{to}")
    @Produces("application/json")
    public Response getByDate(@PathParam("from") String fromStr, @PathParam("to") String toStr) {
        try {
            return Response.ok(messageController.getByDateJson(sd.parse(fromStr), sd.parse(toStr))).build();
        } catch (ParseException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(JsonObject json) {
        return Response.ok(messageController.addJson(json)).build();
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response edit(@PathParam("id") int id, JsonObject json) {
        JsonObject jsonWithId = messageController.editJson(id, json);
        if (jsonWithId != null) {
            return Response.ok(jsonWithId).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response del(@PathParam("id") int id) {
        if (messageController.deleteById(id)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}   
    
//    @GET
//    @Produces("application/json")
//    public JsonObject getJson() {
//        JsonObjectBuilder json = Json.createObjectBuilder();
//        json.add("messageId", 1);
//        json.add("title", "Sample Title");
//        json.add("contents", "Some sample content");
//        json.add("author", "A. Sample");
//        json.add("sentTime", "2016-03-31T13:24:11.135Z");
//        return json.build();
//    }
//
//    @POST
//    @Consumes("application/json")
//    @Produces("application/json")
//    public JsonObject postJson(JsonObject json) {
//        System.out.println(json.getInt("messageId", 1));
//        System.out.println(json.getString("title", "Sample Title"));
//        System.out.println(json.getString("contents", "Some sample content"));
//        System.out.println(json.getString("author", "A. Sample"));
//        System.out.println(json.getString("sentTime", "2016-03-31T13:24:11.135Z"));
//        return json;
//    }
//    }
