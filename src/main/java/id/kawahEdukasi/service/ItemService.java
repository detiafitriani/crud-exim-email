package id.kawahEdukasi.service;

import id.kawahEdukasi.model.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
    @ApplicationScoped
    public class ItemService {
        @Transactional
        public List<Item> getAll() {

            return Item.listAll();
        }

        public Item DetailById(Long id) {

            return Item.findById(id);
        }

    @Transactional
   public Response create(Map<String, Object> request) {

        Item item = new Item();
        item.name = request.get("name").toString();
        item.count = request.get("count").hashCode();
        item.price = request.get("price").hashCode();
        item.type = request.get("type").toString();
        item.description =request.get("description").toString();

        item.persist();

        return Response.status(Response.Status.CREATED).entity(item).build();
    }
    @Transactional
    public Response update(@PathParam("id") Long id, Map<String, Object> request) {
        Item item = Item.findById(id);
        if(item == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        item.name = request.get("name").toString();
        item.count = request.get("count").hashCode();
        item.price = request.get("price").hashCode();
        item.type = request.get("type").toString();
        item.description =request.get("description").toString();

        item.persist();

        return Response.status(Response.Status.OK).entity(item).build();
    }


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
