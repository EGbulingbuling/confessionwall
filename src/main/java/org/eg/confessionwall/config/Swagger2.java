package org.eg.confessionwall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Project Name:confessionwall <br/>
 * Package org.eg.confessionwall.config <br/>
 * Date:2020/3/24 17:02 <br/>
 * <b>Description:</b> TODO: 描述该类的作用 <br/>
 *
 * @author <a href="turodog@foxmail.com">nasus</a><br/>
 * Copyright Notice =========================================================
 * This file contains proprietary information of Eastcom Technologies Co. Ltd.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2019 =======================================================
 */
@Configuration
// 启用 Swagger2
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 文档信息对象
                .apiInfo(apiInfo())
                .select()
                // 被注解的包路径
                .apis(RequestHandlerSelectors.basePackage("org.eg.confessionwall.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title("API 文档")
                // Api 文档描述
                .description("confessionwall")

                .termsOfServiceUrl("https://blog.csdn.net/q2051190945")
                // 文档作者信息
                .contact(new Contact("eg", "https://github.com/EGbulingbuling", "2051190945@qq.com"))
                // 文档版本
                .version("1.0")
                .build();
    }
}