package org.example.demo.util;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.HttpAuthenticationScheme;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

//@Configuration

/**
 * 配置SwaggerConfig
 */
//@Configuration
public class SwaggerConfig {
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo())
//                .securitySchemes(Collections.singletonList(HttpAuthenticationScheme.JWT_BEARER_BUILDER
////                        显示用
//                        .name("JWT")
//                        .build()))
//                .securityContexts(Collections.singletonList(SecurityContext.builder()
//                        .securityReferences(Collections.singletonList(SecurityReference.builder()
//                                .scopes(new AuthorizationScope[0])
//                                .reference("JWT")
//                                .build()))
//                        // 声明作用域
//                        .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
//                        .build()))
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    /**
//     * 添加摘要信息
//     *
//     * @return 返回ApiInfo对象
//     */
//    private ApiInfo apiInfo() {
//        // 用ApiInfoBuilder进行定制
//        return new ApiInfoBuilder()
//                // 设置标题
//                .title("金融科技比赛后端接口文档")
//                // 服务条款
//                .termsOfServiceUrl("NO terms of service")
//                // 描述
//                .description("这是SWAGGER_3生成的接口文档")
//                // 版本
//                .version("版本号:V1.0")
//                //协议
//                .license("The Apache License")
//                // 协议url
//                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
//                .build();
//    }
}
