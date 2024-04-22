package com.playground.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import com.playground.annotation.ExcelDown;
import com.playground.constants.MessageCode;
import com.playground.exception.BizException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class ExcelDownUtil<T> {
  private static final int MAX_ROWS = SpreadsheetVersion.EXCEL2007.getMaxRows();
  private static final int FLUSH_ROWS = 10000;

  private static final ModelMapper modelMapper = new ModelMapper();

  private String fileName;
  private Class<T> clazz;
  private List<ColumnInfo> columnList;
  private List<T> dataList;
  private SXSSFWorkbook workbook;
  private SXSSFSheet sheet;
  private CellStyle headerStyle;
  private CellStyle bodyStyle;

  /**
   * @param fileName - 다운로드 될 파일 명
   * @param sheetName - 시트명
   * @param clazz - 다운로드하는 DTO/VO Class
   * @param dataList - 다운로드할 data 목록
   */
  public ExcelDownUtil(String fileName, String sheetName, Class<T> clazz, List<T> dataList) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String timestamp = LocalDateTime.now().format(formatter);

    this.fileName = fileName + "_" + timestamp;
    this.clazz = clazz;
    this.dataList = dataList;

    if (CollectionUtils.isNotEmpty(dataList) && dataList.size() > MAX_ROWS) {
      throw new BizException(MessageCode.FAIL_EXCEL_DOWNLOAD);
    }

    workbook = new SXSSFWorkbook(FLUSH_ROWS);
    workbook.setCompressTempFiles(true);

    if (StringUtils.isBlank(sheetName)) {
      sheet = workbook.createSheet(fileName);
    } else {
      sheet = workbook.createSheet(sheetName);
    }


    getColumnInfo();
  }

  /**
   * @param fileName - 다운로드 될 파일/sheet 명
   * @param clazz - 다운로드하는 DTO/VO Class
   * @param dataList - 다운로드할 data 목록
   */
  public ExcelDownUtil(String fileName, Class<T> clazz, List<T> dataList) {
    this(fileName, fileName, clazz, dataList);
  }

  public SXSSFWorkbook getWorkbook() {
    return workbook;
  }

  public Font getFont() {
    return workbook.createFont();
  }

  public CellStyle getEmptyStyle() {
    return workbook.createCellStyle();
  }

  public void setHeaderStyle(CellStyle headerStyle) {
    this.headerStyle = headerStyle;
  }

  public void setBodyStyle(CellStyle bodyStyle) {
    this.bodyStyle = bodyStyle;
  }

  private void getColumnInfo() {
    List<Field> fieldList = FieldUtils.getFieldsListWithAnnotation(clazz, ExcelDown.class);

    if (CollectionUtils.isEmpty(fieldList)) {
      throw new BizException(MessageCode.FAIL_EXCEL_DOWNLOAD);
    }

    columnList = fieldList.stream().map(field -> {
      Annotation annotation = field.getAnnotation(ExcelDown.class);
      Map<String, Object> dtoInfoMap = AnnotationUtils.getAnnotationAttributes(annotation);
      dtoInfoMap.put("key", field.getName());

      return modelMapper.map(dtoInfoMap, ColumnInfo.class);
    }).sorted(Comparator.comparing(ColumnInfo::getOrder)).collect(Collectors.toList());

    if (CollectionUtils.isEmpty(columnList)) {
      throw new BizException(MessageCode.FAIL_EXCEL_DOWNLOAD);
    }
  }

  private void makeExcel() {
    setHeader();

    if (CollectionUtils.isNotEmpty(dataList)) {
      setBody();

      for (int i = 0; i < columnList.size(); i++) {
        ColumnInfo columnInfo = columnList.get(i);

        if (columnInfo.getWidth() == -1) {
          sheet.trackColumnForAutoSizing(i);
          sheet.autoSizeColumn(i);
        }
      }
    }
  }

  private void setHeader() {
    Row row = sheet.createRow(0);

    for (int i = 0; i < columnList.size(); i++) {
      Cell cell = row.createCell(i);
      ColumnInfo columnInfo = columnList.get(i);

      cell.setCellValue(columnInfo.getHeaderName());

      if (headerStyle != null) {
        cell.setCellStyle(headerStyle);
      }

      if (columnInfo.getWidth() > 0) {
        sheet.untrackColumnForAutoSizing(i);
        sheet.setColumnWidth(i, (columnInfo.getWidth() * 256) / 7);
      } else {
        sheet.trackColumnForAutoSizing(i);
      }
    }
  }

  private void setBody() { // NOSONAR
    for (int i = 0; i < dataList.size(); i++) {
      Row row = sheet.createRow(i + 1);
      T dataObj = dataList.get(i);

      for (int j = 0; j < columnList.size(); j++) {
        Cell cell = row.createCell(j);
        ColumnInfo columnInfo = columnList.get(j);

        String value;

        try {
          value = StringUtils.defaultString(Objects.toString(FieldUtils.readField(dataObj, columnInfo.getKey(), true)));
        } catch (IllegalAccessException e) {
          Method getMethod = ReflectionUtils.findMethod(clazz, "get" + StringUtils.capitalize(columnInfo.getKey()));

          if (getMethod != null) {
            value = StringUtils.defaultString(Objects.toString(ReflectionUtils.invokeMethod(getMethod, dataObj)));
          } else {
            throw new BizException(MessageCode.FAIL_EXCEL_DOWNLOAD);
          }
        }

        if (StringUtils.isNoneBlank(value) && !StringUtils.equals("null", value)) {
          if (StringUtils.length(value) > 32767) {
            value = StringUtils.substring(value, 0, 32767);
          }

          cell.setCellValue(value);
        }

        if (columnInfo.getCellStyle() != null) {
          cell.setCellStyle(columnInfo.getCellStyle());
        } else if (StringUtils.isNotBlank(columnInfo.getDataFormat()) || bodyStyle != null) {
          CellStyle cellStyle = workbook.createCellStyle();

          if (bodyStyle != null) {
            BeanUtils.copyProperties(bodyStyle, cellStyle);
          }

          if (StringUtils.isNotBlank(columnInfo.getDataFormat())) {
            DataFormat dataFormat = workbook.createDataFormat();

            cellStyle.setDataFormat(dataFormat.getFormat(columnInfo.getDataFormat()));
          }

          if (cellStyle != null) {
            cell.setCellStyle(cellStyle);

            columnInfo.setCellStyle(cellStyle);
            columnList.set(i, columnInfo);
          }
        }
      }
    }
  }

  public ResponseEntity<byte[]> download() {
    makeExcel();

    byte[] bytes;

    try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
      workbook.write(bos);
      bytes = bos.toByteArray();
      workbook.dispose();
    } catch (IOException e) {
      throw new BizException(MessageCode.FAIL_EXCEL_DOWNLOAD);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + ".xlsx;");

    return ResponseEntity.ok().cacheControl(CacheControl.noCache()).headers(headers).body(bytes);
  }

  @ToString
  @Getter
  @Setter
  public static class ColumnInfo {
    private String key;
    private String headerName;
    private int width;
    private int order;
    private String dataFormat;
    private CellStyle cellStyle;
  }
}
