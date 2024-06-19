package com.playground.api.sample.controller;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.playground.api.sample.model.RequestMappingExcelDownResponse;
import com.playground.api.sample.model.RequestMappingResponse;
import com.playground.utils.ExcelDownUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Profile("local")
@Tag(name = "request-mapping", description = "RequestMapping 샘플 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground/public/sample/request-mapping")
public class RequestMappingController {
  private final RequestMappingHandlerMapping requestMappingHandlerMapping;

  /**
   * requestMapping 정보 조회
   */
  @Operation(summary = "requestMapping 정보 조회",
      description = "RequestMappingHandlerMapping를 활용해서 request의 url, http method, class명, method, parameter, return type등 정보를 조회")
  @GetMapping
  public Map<String, RequestMappingResponse> getRequestMappings() { // NOSONAR
    ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
    Map<String, RequestMappingResponse> resultMap = new HashMap<>();

    for (Map.Entry<RequestMappingInfo, HandlerMethod> enty : map.entrySet()) {
      RequestMappingInfo key = enty.getKey();
      HandlerMethod handlerMethod = enty.getValue();
      Method method = handlerMethod.getMethod();

      RequestMappingResponse requestMappingResponse = new RequestMappingResponse();

      PatternsRequestCondition patternsRequestCondition = key.getPatternsCondition();

      if (patternsRequestCondition != null) {
        requestMappingResponse.setUrls(patternsRequestCondition.getPatterns());
      }

      RequestMethodsRequestCondition requestMethodsRequestCondition = key.getMethodsCondition();

      requestMappingResponse.setHttpMethods(requestMethodsRequestCondition.getMethods());
      requestMappingResponse.setClassName(handlerMethod.getBeanType().getName());
      requestMappingResponse.setClassNameSimple(handlerMethod.getBeanType().getSimpleName());
      requestMappingResponse.setMethodName(method.getName());

      // 파라메터 정보 - start
      Class<?>[] parameterTypes = method.getParameterTypes();
      String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

      if (parameterNames != null && parameterNames.length > 0) {
        List<String> params = new ArrayList<>();
        List<String> paramsSimple = new ArrayList<>();

        for (int i = 0; i < parameterNames.length; i++) {
          params.add(parameterTypes[i].getName() + " " + parameterNames[i]);
          paramsSimple.add(parameterTypes[i].getSimpleName() + " " + parameterNames[i]);
        }

        requestMappingResponse.setParams(params);
        requestMappingResponse.setParamsSimple(paramsSimple);
      }
      // 파라메터 정보 - end

      // 메소드 return 정보 (generic 정보까지 조회) - start
      Type returnType = method.getGenericReturnType();

      if (returnType instanceof ParameterizedType parameterizedType) {
        requestMappingResponse.setReturnType(getName(parameterizedType, false));
        requestMappingResponse.setReturnTypeSimple(getName(parameterizedType, true));
      } else {
        requestMappingResponse.setReturnType(returnType.getTypeName());
      }
      // 메소드 return 정보 (generic 정보까지 조회) - end

      // @Operation 정보로 메소드 상세 내역 조회 - start
      Operation apiOperation = method.getAnnotation(Operation.class);

      if (apiOperation != null) {
        requestMappingResponse.setMethodDescription(apiOperation.summary());

        if (StringUtils.isNotBlank(apiOperation.description())) {
          requestMappingResponse.setMethodDescriptionDetail(apiOperation.description());
        }
      }
      // @Operation 정보로 메소드 상세 내역 조회 - end

      // request url이 여러개인 경우 분리를 하기 위함
      for (String path : requestMappingResponse.getUrls()) {
        resultMap.put(path, requestMappingResponse);
      }
    }

    return new TreeMap<>(resultMap);
  }

  /**
   * requestMapping 정보 엑셀 다운 - type1
   */
  @Operation(summary = "requestMapping 정보 엑셀 다운 - type1",
      description = "RequestMappingHandlerMapping를 활용해서 request의 url, http method, class명, method, parameter, return type등 정보를 엑셀로 다운로드")
  @GetMapping("/download/type1")
  public ResponseEntity<byte[]> downloadExcelType1RequestMappings() { // NOSONAR
    ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
    List<RequestMappingResponse> resultList = new ArrayList<>();

    for (Map.Entry<RequestMappingInfo, HandlerMethod> enty : map.entrySet()) {
      RequestMappingInfo key = enty.getKey();
      HandlerMethod handlerMethod = enty.getValue();
      Method method = handlerMethod.getMethod();

      RequestMappingResponse requestMappingResponse = new RequestMappingResponse();

      PatternsRequestCondition patternsRequestCondition = key.getPatternsCondition();

      if (patternsRequestCondition != null) {
        requestMappingResponse.setUrls(patternsRequestCondition.getPatterns());
      }

      RequestMethodsRequestCondition requestMethodsRequestCondition = key.getMethodsCondition();

      requestMappingResponse.setHttpMethods(requestMethodsRequestCondition.getMethods());
      requestMappingResponse.setClassName(handlerMethod.getBeanType().getName());
      requestMappingResponse.setClassNameSimple(handlerMethod.getBeanType().getSimpleName());
      requestMappingResponse.setMethodName(method.getName());

      // 파라메터 정보 - start
      Class<?>[] parameterTypes = method.getParameterTypes();
      String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

      if (parameterNames != null && parameterNames.length > 0) {
        List<String> params = new ArrayList<>();
        List<String> paramsSimple = new ArrayList<>();

        for (int i = 0; i < parameterNames.length; i++) {
          params.add(parameterTypes[i].getName() + " " + parameterNames[i]);
          paramsSimple.add(parameterTypes[i].getSimpleName() + " " + parameterNames[i]);
        }

        requestMappingResponse.setParams(params);
        requestMappingResponse.setParamsSimple(paramsSimple);
      }
      // 파라메터 정보 - end

      // 메소드 return 정보 (generic 정보까지 조회) - start
      Type returnType = method.getGenericReturnType();

      if (returnType instanceof ParameterizedType parameterizedType) {
        requestMappingResponse.setReturnType(getName(parameterizedType, false));
        requestMappingResponse.setReturnTypeSimple(getName(parameterizedType, true));
      } else {
        requestMappingResponse.setReturnType(returnType.getTypeName());
      }
      // 메소드 return 정보 (generic 정보까지 조회) - end

      // @Operation 정보로 메소드 상세 내역 조회 - start
      Operation apiOperation = method.getAnnotation(Operation.class);

      if (apiOperation != null) {
        requestMappingResponse.setMethodDescription(apiOperation.summary());

        if (StringUtils.isNotBlank(apiOperation.description())) {
          requestMappingResponse.setMethodDescriptionDetail(apiOperation.description());
        }
      }
      // @Operation 정보로 메소드 상세 내역 조회 - end

      resultList.add(requestMappingResponse);
    }

    ExcelDownUtil<RequestMappingResponse> excel =
        new ExcelDownUtil<>("request-mappings type1", "endpoint info", RequestMappingResponse.class, resultList);

    CellStyle headerStyle = excel.getEmptyStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    headerStyle.setBorderTop(BorderStyle.THIN);
    headerStyle.setBorderRight(BorderStyle.THIN);
    headerStyle.setBorderBottom(BorderStyle.THIN);
    headerStyle.setBorderLeft(BorderStyle.THIN);
    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    Font headerFont = excel.getFont();
    headerFont.setBold(true);

    headerStyle.setFont(headerFont);

    excel.setHeaderStyle(headerStyle);

    CellStyle bodyStyle = excel.getEmptyStyle();
    bodyStyle.setBorderTop(BorderStyle.THIN);
    bodyStyle.setBorderRight(BorderStyle.THIN);
    bodyStyle.setBorderBottom(BorderStyle.THIN);
    bodyStyle.setBorderLeft(BorderStyle.THIN);

    excel.setBodyStyle(bodyStyle);

    return excel.download();
  }

  /**
   * requestMapping 정보 엑셀 다운 - type2
   */
  @Operation(summary = "requestMapping 정보 엑셀 다운 - type2",
      description = "RequestMappingHandlerMapping를 활용해서 request의 url, http method, class명, method, parameter, return type등 정보를 엑셀로 다운로드")
  @GetMapping("/download/type2")
  public ResponseEntity<byte[]> downloadExcelType2RequestMappings() { // NOSONAR
    ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
    List<RequestMappingExcelDownResponse> resultList = new ArrayList<>();

    for (Map.Entry<RequestMappingInfo, HandlerMethod> enty : map.entrySet()) {
      RequestMappingInfo key = enty.getKey();
      HandlerMethod handlerMethod = enty.getValue();
      Method method = handlerMethod.getMethod();

      RequestMappingExcelDownResponse requestMappingResponse = new RequestMappingExcelDownResponse();

      RequestMethodsRequestCondition requestMethodsRequestCondition = key.getMethodsCondition();

      requestMappingResponse.setHttpMethods(requestMethodsRequestCondition.getMethods());
      requestMappingResponse.setClassName(handlerMethod.getBeanType().getName());
      requestMappingResponse.setClassNameSimple(handlerMethod.getBeanType().getSimpleName());
      requestMappingResponse.setMethodName(method.getName());

      // 파라메터 정보 - start
      Class<?>[] parameterTypes = method.getParameterTypes();
      String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

      if (parameterNames != null && parameterNames.length > 0) {
        List<String> params = new ArrayList<>();
        List<String> paramsSimple = new ArrayList<>();

        for (int i = 0; i < parameterNames.length; i++) {
          params.add(parameterTypes[i].getName() + " " + parameterNames[i]);
          paramsSimple.add(parameterTypes[i].getSimpleName() + " " + parameterNames[i]);
        }

        requestMappingResponse.setParams(params);
        requestMappingResponse.setParamsSimple(paramsSimple);
      }
      // 파라메터 정보 - end

      // 메소드 return 정보 (generic 정보까지 조회) - start
      Type returnType = method.getGenericReturnType();

      if (returnType instanceof ParameterizedType parameterizedType) {
        requestMappingResponse.setReturnType(getName(parameterizedType, false));
        requestMappingResponse.setReturnTypeSimple(getName(parameterizedType, true));
      } else {
        requestMappingResponse.setReturnType(returnType.getTypeName());
      }
      // 메소드 return 정보 (generic 정보까지 조회) - end

      // @Operation 정보로 메소드 상세 내역 조회 - start
      Operation apiOperation = method.getAnnotation(Operation.class);

      if (apiOperation != null) {
        requestMappingResponse.setMethodDescription(apiOperation.summary());

        if (StringUtils.isNotBlank(apiOperation.description())) {
          requestMappingResponse.setMethodDescriptionDetail(apiOperation.description());
        }
      }

      // @Operation 정보로 메소드 상세 내역 조회 - end
      PatternsRequestCondition patternsRequestCondition = key.getPatternsCondition();

      if (patternsRequestCondition != null) {
        Set<String> urls = patternsRequestCondition.getPatterns();

        for (String url : urls) {
          RequestMappingExcelDownResponse result = new RequestMappingExcelDownResponse();

          BeanUtils.copyProperties(requestMappingResponse, result);

          result.setUrl(url);

          resultList.add(result);
        }
      }
    }

    ExcelDownUtil<RequestMappingExcelDownResponse> excel =
        new ExcelDownUtil<>("request-mappings type2", "endpoint info", RequestMappingExcelDownResponse.class, resultList);

    CellStyle headerStyle = excel.getEmptyStyle();
    headerStyle.setAlignment(HorizontalAlignment.CENTER);
    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    headerStyle.setBorderTop(BorderStyle.THIN);
    headerStyle.setBorderRight(BorderStyle.THIN);
    headerStyle.setBorderBottom(BorderStyle.THIN);
    headerStyle.setBorderLeft(BorderStyle.THIN);
    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    Font headerFont = excel.getFont();
    headerFont.setBold(true);

    headerStyle.setFont(headerFont);

    excel.setHeaderStyle(headerStyle);

    CellStyle bodyStyle = excel.getEmptyStyle();
    bodyStyle.setBorderTop(BorderStyle.THIN);
    bodyStyle.setBorderRight(BorderStyle.THIN);
    bodyStyle.setBorderBottom(BorderStyle.THIN);
    bodyStyle.setBorderLeft(BorderStyle.THIN);

    excel.setBodyStyle(bodyStyle);

    return excel.download();
  }

  private String getName(Type type, boolean isSimple) {
    if (type instanceof ParameterizedType parameterizedType) {
      Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
      Type rawType = parameterizedType.getRawType();
      StringBuilder stringBuilder = new StringBuilder();

      stringBuilder.append(getName(rawType, isSimple));
      stringBuilder.append("<");

      for (int i = 0; i < actualTypeArguments.length; i++) {
        if (i > 0) {
          stringBuilder.append(", ");
        }

        if (isSimple) {
          stringBuilder.append(getName(actualTypeArguments[i], isSimple));
        } else {
          stringBuilder.append(actualTypeArguments[i].getTypeName());
        }
      }

      stringBuilder.append(">");

      return stringBuilder.toString();
    } else if (type instanceof TypeVariable<?> typeVariable) {

      return typeVariable.getName();
    } else if (type instanceof Class<?> clzz) {

      return isSimple ? clzz.getSimpleName() : clzz.getName();
    } else {
      return type.getTypeName();
    }
  }
}
