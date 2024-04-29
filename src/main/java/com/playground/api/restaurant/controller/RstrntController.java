package com.playground.api.restaurant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playground.api.restaurant.model.RstrntSrchRequest;
import com.playground.api.restaurant.model.RstrntSrchResponse;
import com.playground.api.restaurant.service.RstrntService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "restaurant", description = "식당 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/playground")
public class RstrntController {
	
	private final RstrntService rstrntService;
	
	  /**
	   * 식당리스트 조회
	   */

	@Operation(summary = "식당리스트 조회", description = "식당리스트 조회")
	@PostMapping("public/restaurant/getRstrntList")
	public List<RstrntSrchResponse> getRstrntList(@RequestBody @Valid RstrntSrchRequest req) {
		
		log.debug(" 식당리스트 조회 :::: {}", req);
		
		return rstrntService.getRstrntList(req);
	}
	
	
	  /**
	   * 식당 등록
	   */
	  @Operation(summary = "식당 등록", description = "식당 등록")
	  @PostMapping("/public/restaurant/addRstrnt")
	  public RstrntSrchResponse addRstrnt(@RequestBody @Valid RstrntSrchRequest req) {
		  
			log.debug(" 식당 등록 :::: {}", req);
		  
	    return rstrntService.addRstrnt(req);
	  }
	  
	  
	  
	  /**
	   * 식당 삭제
	   */
	  @Operation(summary = "식당 삭제", description = "식당 삭제")
	  @PostMapping("/public/restaurant/removeRstrnt")
	  public void removeRstrnt(@RequestBody @Valid RstrntSrchRequest req) {
		  
			log.debug(" 식당 삭제 :::: {}", req);
		  
	    rstrntService.removeRstrnt(req);
	  }
}
