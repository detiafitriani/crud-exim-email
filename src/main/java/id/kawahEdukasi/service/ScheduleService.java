package id.kawahEdukasi.service;

import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.time.LocalDateTime;

@ApplicationScoped
public class ScheduleService {

  @Inject
  MailService mailService;


  Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    @Scheduled(every = "30m")
    public void generateKawahEdukasi(){

      logger.info("kawahedukasi {}", LocalDateTime.now());

    }

    @Scheduled(cron = "0 0 7 1/10 * ? *")
    public void scheduleSendEmailListItem() throws IOException {

      mailService.sendExcelToEmail ("detiafitriani@gmail.com");

      logger.info("SEND EMAIL SUCCESS");
    }

}