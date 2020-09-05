package org.eg.confessionwall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("org.eg.confessionwall.dao")
@EnableSwagger2
@EnableTransactionManagement(proxyTargetClass = true)
public class ConfessionWallApplication{
    public static void main(String[] args) {
        SpringApplication.run(ConfessionWallApplication.class, args);
    }

}
