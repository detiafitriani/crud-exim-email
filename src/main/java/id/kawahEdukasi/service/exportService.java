package id.kawahEdukasi.service;


import com.opencsv.CSVWriter;
import id.kawahEdukasi.model.Item;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class exportService {
    @Inject
    ItemService itemService;

    public Response exportPdfItem() throws JRException{

            Connection conn = null;
            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/week6", "postgres", "12345");

            } catch (SQLException ex) {

            } catch (ClassNotFoundException ex) {

            } finally {

            }

        File file = new File("src/main/resources/itemReport.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource jrbn = new JRBeanCollectionDataSource(Item.listAll());

        Map<String, Object> param = new HashMap<>();
       param.put("DATASOURCE", jrbn);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,new HashMap<>(), conn);

        byte[] jasperResult = JasperExportManager.exportReportToPdf(jasperPrint);

        return Response.ok().type("application/pdf").entity(jasperResult).build();
    }
    public Response exportExcelItem() throws IOException {
        ByteArrayOutputStream outputStream = excelItem();

        return Response.ok()
                .type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-Disposition", "attachment: filename\"item_list_excel.xlsx\"")
                .entity(outputStream.toByteArray()).build();
    }

    public ByteArrayOutputStream excelItem() throws IOException {
        List<Item> itemList = Item.listAll();
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet Sh = wb.createSheet("data ");

        int rownum = 8;
        Row row = Sh.createRow(rownum++);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("name");
        row.createCell(2).setCellValue("price");
        row.createCell(3).setCellValue("type");
        row.createCell(4).setCellValue("count");
        row.createCell(5).setCellValue("description");
        row.createCell(6).setCellValue("createddAt");
        row.createCell(7).setCellValue("updatedAt");

        for (Item item : itemList){
            row = Sh.createRow(rownum++);
            row.createCell(0).setCellValue(item.id);
            row.createCell(1).setCellValue(item.name);
            row.createCell(2).setCellValue(item.price);
            row.createCell(3).setCellValue(item.type);
            row.createCell(4).setCellValue(item.count);
            row.createCell(5).setCellValue(item.description);
            row.createCell(6).setCellValue(item.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            row.createCell(7).setCellValue(item.updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        return outputStream;
    }


    public Response exportCSVItem() throws IOException {

        List<Item> itemList = Item.listAll();

        File file = File.createTempFile("temp", "");

        FileWriter outputfile = new FileWriter(file);

        CSVWriter writer = new CSVWriter(outputfile);

        String[] headers = {"id", "name", "price", "type", "count", "description", "created At", "updated at"};
        writer.writeNext(headers);
        for(Item item : itemList) {
            String[] data = {
                    item.id.toString(),
                    item.name,
                    item.price.toString(),
                    item.type,
                    item.count.toString(),
                    item.description,
                    item.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    item.updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            };
            writer.writeNext(data);
            }
        return Response.ok()
                .type("text/csv")
                .header("Content-Disposition", "attachment: filename\"item_list_excel.csv\"")
                .entity(file).build();
        }


    }
