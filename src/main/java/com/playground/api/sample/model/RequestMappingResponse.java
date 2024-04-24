package com.playground.api.sample.model;

import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.playground.annotation.ExcelDown;
import com.playground.model.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "RequestMappingResponse", description = "RequestMapping 정보 응답 데이터")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RequestMappingResponse extends BaseDto {
  private static final long serialVersionUID = 8841795425671398474L;

  @Schema(description = "request url과 동일한 메소드에 mapping된 url 목록", example = "[\"/playground/public/sample/request-mapping\"]")
  @ExcelDown(headerName = "request url", order = 1)
  private Set<String> urls;

  @Schema(description = "http method 목록", example = "[\"GET\", \"POST\"]")
  @ExcelDown(headerName = "http methods", order = 2)
  private Set<RequestMethod> httpMethods;

  @Schema(description = "Class명 상세", example = "com.playground.api.sample.RequestMappingController")
  @ExcelDown(headerName = "Class명 상세", order = 9)
  private String className;

  @Schema(description = "Class명", example = "RequestMappingController")
  @ExcelDown(headerName = "Class명", order = 5)
  private String classNameSimple;

  @Schema(description = "Method명", example = "getRequestMappings")
  @ExcelDown(headerName = "Method명", order = 6)
  private String methodName;

  @Schema(description = "Method 설명", example = "requestMapping 정보 조회")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ExcelDown(headerName = "Method 설명", order = 7)
  private String methodDescription;

  @Schema(description = "Method 설명 상세",
      example = "RequestMappingHandlerMapping를 활용해서 request의 url, http method, class명, method, parameter, return type등 정보를 조회")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ExcelDown(headerName = "Method 설명 상세", order = 8)
  private String methodDescriptionDetail;

  @Schema(description = "parameter 상세", example = "[\"java.lang.String id\", \"java.lang.String pwd\"]")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ExcelDown(headerName = "parameter 상세", order = 10)
  private List<String> params;

  @Schema(description = "parameter", example = "[\"String id\", \"String pwd\"]")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ExcelDown(headerName = "parameter", order = 3)
  private List<String> paramsSimple;

  @Schema(description = "return 상세",
      example = "org.springframework.http.ResponseEntity<com.playground.model.BaseResponse<java.util.Map<java.lang.String, com.playground.api.sample.model.RequestMappingInfoResponse>>>")
  @ExcelDown(headerName = "return 상세", order = 11)
  private String returnType;

  @Schema(description = "return", example = "ResponseEntity<BaseResponse<Map<String, RequestMappingInfoResponse>>>")
  @ExcelDown(headerName = "return", order = 4)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String returnTypeSimple;
}
