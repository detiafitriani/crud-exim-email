package id.kawahEdukasi.controller;

import model.Item;
import org.apache.commons.lang3.RandomStringUtils;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;


@Path("/item")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public class ItemController {

    @GET
    public Response get(@PathParam("nama") String name){
        return Response.status(Response.Status.OK).entity(Item.findAll()).build();
    }

    @POST
    @Transactional
    public Response post(Map<String, Object> request) {

        Item item = new Item();
        item.name = request.get("name").toString();
        item.count = Integer.valueOf(request.get("count").toString());
        item.price = Double.valueOf(request.get("price").toString());
        item.type = request.get("type").toString();
        item.description =request.get("description").toString();

        item.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();

    }
    @PUT
    @Path("/{id}")
    @Transactional
    public Response put(@PathParam("id") Long id, Map<String, Object> request) {
        Item item = Item.findById(id);
        if(item == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        item.name = request.get("name").toString();
        item.count = Integer.valueOf(request.get("count").toString());
        item.price = Double.valueOf(request.get("price").toString());
        item.type = request.get("type").toString();
        item.description =request.get("description").toString();

        item.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Item item = Item.findById(id);

        if (item == null) {
            throw new WebApplicationException("Item with id of " + id + " does not exist.", Response.Status.NOT_FOUND);
        }

        item.delete();

        return Response.status(Response.Status.NO_CONTENT).entity(new HashMap<>()).build();
    }


}
