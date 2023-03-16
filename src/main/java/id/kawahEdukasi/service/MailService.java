package id.kawahEdukasi.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class MailService {
    @Inject
    Mailer mailer;

    @Inject
    exportService exportService;

    public void sendEmail(String email){

        mailer.send(
                Mail.withHtml(email, "Test from CRUD API QUARKUS",
                        "<h1>hello,</h1> this is Quarkus Item-Service"));

    }


    public void sendExcelToEmail(String email) throws IOException {

        mailer.send(
                Mail.withHtml(email,
                                "Excel Peserta Batch 6",
                                "<h1>hello,</h1> this is your excel file")
                .addAttachment("list-Item.xlsx",
                        exportService.excelItem().toByteArray(),
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }

}
