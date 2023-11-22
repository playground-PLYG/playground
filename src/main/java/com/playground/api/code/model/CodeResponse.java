package com.playground.api.code.model;

import com.playground.model.BaseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(name = "SignResponse", description = "응답 데이터")
@Getter
@Setter
public class CodeResponse extends BaseDto {

	 @Schema(description = "코드ID")
	 private String cdId;
	 
	 @Schema(description = "코드명")
	 private String cdNm;
	 
	 @Schema(description = "상위코드ID")
	 private String upCdId;
	 
	 @Schema(description = "그룹코드여부")
	 private String groupCdYn;
	 
	 @Schema(description = "정렬순번")
	 private String sortSn;
	 
	 @Schema(description = "등록자ID")
	 private String regMbrNo;
	 
	 @Schema(description = "등록일시")
	 private String regDt;
	 
	 @Schema(description = "수정자ID")
	 private String mdfcnMbrNo;	 
	 
	 @Schema(description = "수정일시")
	 private String dfcnDt;
	 

	 

}
