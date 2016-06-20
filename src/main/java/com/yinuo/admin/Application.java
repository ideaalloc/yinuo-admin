package com.yinuo.admin;

import com.yinuo.admin.service.StaffService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.TimeZone;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2016-05-10
 */
@SpringBootApplication
@EnableScheduling
public class Application {
    @Value("${admin.username}")
    String adminUsername;

    @Value("${admin.password}")
    String adminPassword;

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(final StaffService staffService) {

        return arg -> {
            if (!staffService.exists(adminUsername)) {
                staffService.addStaff(adminUsername, adminPassword, "ROLE_ADMIN", 1L, "admin", "13888888888", "admin@yinuo-tech.com");
            }
        };

    }
}
