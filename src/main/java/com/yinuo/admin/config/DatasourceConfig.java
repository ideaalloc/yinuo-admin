package com.yinuo.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-10-29
 */
@Configuration
@EnableConfigurationProperties
public class DatasourceConfig {
    static final Logger LOGGER = LoggerFactory.getLogger(DatasourceConfig.class);

    @Value("${spring.adminDs.url}")
    String adminUrl;

    @Value("${spring.adminDs.username}")
    String adminUsername;

    @Value("${spring.adminDs.password}")
    String adminPassword;

    @Value("${spring.adminDs.driver-class-name}")
    String adminDriverClassName;

    @Value("${spring.apiDs.url}")
    String apiUrl;

    @Value("${spring.apiDs.username}")
    String apiUsername;

    @Value("${spring.apiDs.password}")
    String apiPassword;

    @Value("${spring.apiDs.driver-class-name}")
    String apiDriverClassName;

    @Bean(name = "adminDs")
    @Primary
    public DataSource adminDs() {
        return createDataSource(adminUrl, adminUsername, adminPassword, adminDriverClassName);
    }

    @Bean(name = "apiDs")
    public DataSource apiDs() {
        return createDataSource(apiUrl, apiUsername, apiPassword, apiDriverClassName);
    }

    private DataSource createDataSource(String url, String username, String password, String driverClassName) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = getClass().getClassLoader().getResourceAsStream("druid.properties");
            prop.load(input);
        } catch (IOException ex) {
            LOGGER.error("load druid config file error");
            throw new RuntimeException("fatal error, caused by druid config error");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.error("close druid config file error");
                }
            }
        }

        dataSource.configFromPropety(prop);
        return dataSource;
    }
}
