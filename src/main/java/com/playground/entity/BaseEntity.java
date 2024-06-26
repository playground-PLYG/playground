package com.playground.entity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ReflectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.playground.annotation.Secret;
import com.playground.constants.PlaygroundConstants;
import com.playground.utils.MaskingUtil;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {
  @CreatedBy
  @Column(name = "regist_usr_id", updatable = false)
  private String registUsrId;

  @CreationTimestamp
  @Column(name = "regist_dt", updatable = false)
  private LocalDateTime registDt;

  @LastModifiedBy
  @Column(name = "updt_usr_id")
  private String updtUsrId;

  @UpdateTimestamp
  @Column(name = "updt_dt")
  private LocalDateTime updtDt;

  @Override
  public String toString() { // NOSONAR
    Map<String, Object> map = new HashMap<>();
    Field[] fields = FieldUtils.getAllFields(getClass());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    if (ObjectUtils.isNotEmpty(fields)) {
      for (Field field : fields) {
        String fieldName = field.getName();
        String fieldValue = "";

        Annotation annotation = field.getAnnotation(Secret.class);

        try {
          ReflectionUtils.makeAccessible(field);
          fieldValue = StringUtils.defaultString(Objects.toString(field.get(this)));
        } catch (IllegalAccessException e) {
          // Do nothing
        }

        if (annotation != null && StringUtils.isNotBlank(fieldValue)) {
          if (fieldValue.matches(PlaygroundConstants.RegexPattern.RESIDENT_FOREIGNER_REGISTRATION_NUMBER)) {
            fieldValue = MaskingUtil.residentForeignerRegistrationNumber(fieldValue);
            // TODO 전화, 카드, 계좌 등등 마스킹 처리 //NOSONAR
          } else if (!StringUtils.isBlank(fieldValue)) {
            fieldValue = MaskingUtil.withoutFirstAndLast(fieldValue);
          }
        }

        map.put(fieldName, fieldValue);
      }
    }

    try {
      return objectMapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }
  }
}
