package id.kawahEdukasi.controller;

import com.opencsv.exceptions.CsvValidationException;
import id.kawahEdukasi.DTO.FileFormDTO;
import id.kawahEdukasi.model.Item;
import id.kawahEdukasi.service.ImportService;
import id.kawahEdukasi.service.exportService;
import id.kawahEdukasi.service.ItemService;
import net.sf.jasperreports.engine.JRException;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

    @Inject
    ItemService itemService;

    @Inject
    exportService exportService;

    @Inject
    ImportService importService;


    @GET
    public List<Item> AllItems() {

        return itemService.getAll();
    }


    @GET
    @Path("/{id}")
    public Item findById(@PathParam("id") Long id) {

        return itemService.DetailById(id);
    }


    @GET
    @Path("/export/pdf")
    @Produces("application/pdf")
    public Response exportPdf() throws JRException {

        return exportService.exportPdfItem();
    }


    @GET
    @Path("/export/excel")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response exportExcel() throws IOException {

        return exportService.exportExcelItem();
    }


    @GET
    @Path("/export/csv")
    @Produces("text/csv")
    public Response exportCsv() throws  IOException {

        return exportService.exportCSVItem();
    }


    @POST
    @Path("/import/excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importExcell(@MultipartForm FileFormDTO request) {

        try{
            return importService.importExcell(request);
        }
        catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @POST
    @Path("/import/csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importCSV(@MultipartForm FileFormDTO request) {

        try {
            return importService.importCSV(request);
        }
        catch (IOException | CsvValidationException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

        }
    }


    @POST
    public Response post(Map<String, Object> request) {

        return itemService.create(request);
    }


    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") Long id, Map<String, Object> request) {

        return itemService.update(id, request);
    }



    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {

        return itemService.delete(id);
    }


}
