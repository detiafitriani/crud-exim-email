package id.kawahEdukasi.controller;

import id.kawahEdukasi.model.Item;
import id.kawahEdukasi.service.ItemService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;


@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

    @Inject
    ItemService itemService;

    @GET
    public List<Item> findAll() {
        return itemService.findAll();
    }

    @GET
    @Path("/{id}")
    public Item findById(@PathParam("id") Long id) {
        return itemService.findById(id);
    }
    @POST
    public Response post(Map<String, Object> request) {
        return itemService.post(request);
    }

    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") Long id, Map<String, Object> request) {
        return itemService.put(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return itemService.delete(id);
    }

}

