package id.kawahEdukasi.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import id.kawahEdukasi.DTO.FileFormDTO;
import id.kawahEdukasi.model.Item;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@ApplicationScoped
public class ImportService {
    @Transactional
    public Response importExcell(FileFormDTO request) throws IOException {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(request.file);

        XSSFWorkbook wb = new XSSFWorkbook(byteArrayInputStream);

        XSSFSheet sheet = wb.getSheetAt(0);

        sheet.removeRow(sheet.getRow(0));

        StringBuilder stringBuilder = new StringBuilder();

        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case NUMERIC:
                        stringBuilder.append(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        stringBuilder.append(cell.getStringCellValue() + "\t");
                        break;
                    default:
                        break;
                }
            }

            stringBuilder.append("\n");
        }

        wb.close();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();

    }

    @Transactional
    public Response importCSV(FileFormDTO request) throws IOException, CsvValidationException {

        File file = File.createTempFile("temp", "");
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(request.file);
        
        CSVReader reader = new CSVReader(new FileReader(file));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String[] nextLine;
        reader.skip(1);

        List<Item> toPersist = new ArrayList<>();

        while ((nextLine = reader.readNext()) != null) {

                Item item = new Item();
                item.id = Long.parseLong(nextLine[0].trim());
                item.name = nextLine[1].trim();
                item.price = Integer.parseInt(nextLine[2].trim());
                item.type = nextLine[3].trim();
                item.count = Integer.parseInt(nextLine[4].trim());
                item.description = nextLine[5].trim();
                item.createdAt = LocalDateTime.parse(nextLine[6].trim(), formatter);
                item.updatedAt = LocalDateTime.parse(nextLine[7].trim(), formatter);

                toPersist.add(item);
            }

        Item.persist(toPersist);

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();

        }

    }

