package com.sixsixsix516;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.LocalDate;

/**
 * @author sunlin
 */
@EnableScheduling
@SpringBootApplication
public class SunBackupApplication implements CommandLineRunner {

    private static String username = "test";
    private static String password = "test";
    private static String db = "test";
    private static String path = "/home/test/backup/file/";

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SunBackupApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String bashFile = generateBash();
        execute(bashFile);
    }

    /**
     * 生成自动备份命令
     */
    @Scheduled(cron = "1 0 0 * * *")
    public String generateBash() {
        String bash = "mysqldump  --column-statistics=0  -u" + username + " --port=12631  -p'" + password + "'  " + db + ">" + path + db + "-" + LocalDate.now() + ".sql";
        String fullPath = path + db + ".sh";
        try (FileWriter fileWriter = new FileWriter(new File(fullPath))) {
            fileWriter.append("#!/bin/sh");
            fileWriter.append("\n");
            fileWriter.append(bash);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullPath;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.mail")
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

    @Autowired
    private JavaMailSender javaMailSender;

    public void execute(String bashFile) throws IOException {
        Process per = Runtime.getRuntime().exec("chmod +x " + bashFile);

        Process process = Runtime.getRuntime().exec(bashFile);
        //取得命令结果的输出流
        InputStream fis = process.getInputStream();
        //用一个读输出流类去读
        InputStreamReader isr = new InputStreamReader(fis);
        //用缓冲器读行
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        //直到读完为止
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        String[] fileArray = {path + db + "-" + LocalDate.now() + ".sql"};

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("sixsixsix517@163.com");
            helper.setTo("sixsixsix517@163.com");
            helper.setSubject("业务名" + LocalDate.now() + "备份");
            helper.setText("业务名备份");
            //验证文件数据是否为空
            FileSystemResource file = null;
            for (String s : fileArray) {
                //添加附件
                file = new FileSystemResource(s);
                String fileName = s.substring(s.lastIndexOf(File.separator));
                helper.addAttachment(fileName, file);
            }
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
