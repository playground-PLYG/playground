package com.playground.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import com.playground.utils.ProfileUtil;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class SwaggerConfig {
  @Autowired
  private ServerProperties serverProperties;

  @Autowired
  private ProfileUtil profileUtil;

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder().group("playground").pathsToMatch("/playground/**").build();
  }

  @Bean
  public OpenAPI openAPI() {

    Info info = new Info().version("v1.0.0").title("Playground API").description("playground 프로젝트 API 명세서");

    Server server = null;

    try {
      String hostname = InetAddress.getLocalHost().getHostName();

      if (StringUtils.isNotBlank(hostname)) {
        server = new Server();

        String port = String.valueOf(serverProperties.getPort());

        log.debug(">>> hostname : {}", hostname);
        log.debug(">>> getActiveProfile : {}", profileUtil.getActiveProfile());
        log.debug(">>> isLocal : {}", profileUtil.isLocal());
        log.debug(">>> GAE_INSTANCE : {}", System.getenv("GAE_INSTANCE"));
        log.debug(">>> HOSTNAME : {}", System.getenv("HOSTNAME"));

        if (profileUtil.isLocal()) {
          hostname = "http://localhost";

          if (StringUtils.isNotBlank(port)) {
            hostname += ":" + port;
          }
        } else {
          hostname = "https://" + hostname;
        }

        server.setUrl(hostname);
      }
    } catch (UnknownHostException e) {
      log.error("UnknownHost", e);
    }

    // SecurityScheme 명
    String jwtSchemeName = "jwtAuth";

    // API 요청헤더에 인증정보 포함
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

    // SecuritySchemes 등록
    Components components = new Components().addSecuritySchemes(jwtSchemeName, new SecurityScheme().name(jwtSchemeName).type(SecurityScheme.Type.HTTP) // HTTP 방식
        .scheme("bearer").bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

    OpenAPI openApi = new OpenAPI().info(info).addSecurityItem(securityRequirement).components(components);

    if (server != null) {
      openApi.servers(List.of(server));
    }

    return openApi;
  }

  // 전역 설정 필요 시 사용
  @Bean
  public OperationCustomizer operationCustomizer() {
    return (Operation operation, HandlerMethod handlerMethod) -> {
      Parameter param = new Parameter().in(ParameterIn.HEADER.toString()) // 전역 헤더 설정
          .schema(new StringSchema()._default("1234567").name("AppID")) // default 값 설정
          .name("AppID").description("TEST AppID").required(true);

      operation.addParametersItem(param);

      return operation;
    };
  }
}
