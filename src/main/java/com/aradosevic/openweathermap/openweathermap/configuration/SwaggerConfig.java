package com.aradosevic.openweathermap.openweathermap.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Defines Swagger Ui and configuration
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .paths(PathSelectors.regex("/api/.*"))
        .build().apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Rest API for Open Weather Map service")
        .description("API for Open Weather Map project")
        .version("0.0.1-SNAPSHOT")
        .licenseUrl("https://www.gnu.org/licenses/gpl-3.0.en.html")
        .build();
  }
}
