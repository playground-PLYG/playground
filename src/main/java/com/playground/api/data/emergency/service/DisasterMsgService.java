package com.playground.api.data.emergency.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.playground.api.code.model.CodeSrchRequest;
import com.playground.api.code.model.CodeSrchResponse;
import com.playground.api.code.service.CodeService;
import com.playground.api.data.emergency.client.DisasterMsgHttpClient;
import com.playground.api.data.emergency.model.DisasterMsgBodyHttpClientResponse;
import com.playground.api.data.emergency.model.DisasterMsgBodyResponse;
import com.playground.api.data.emergency.model.DisasterMsgHeaderResponse;
import com.playground.api.data.emergency.model.DisasterMsgHttpClientResponse;
import com.playground.api.data.emergency.model.DisasterMsgRequest;
import com.playground.api.data.emergency.model.DisasterMsgResponse;
import com.playground.api.data.emergency.model.DisasterMsgResponse.DisasterMsgResponseBuilder;
import com.playground.constants.CacheType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DisasterMsgService {
  private final CodeService codeService;
  private final DisasterMsgHttpClient disasterMsgHttpClient;

  @Cacheable(cacheManager = CacheType.ONE_MINUTES, cacheNames = "disasterMsg",
      key = "#reqData.crtDt + '_' + #reqData.rgnNm + '_' + #reqData.numOfRows + '_' + #reqData.pageNo", unless = "#result == null")
  @Transactional(readOnly = true)
  public DisasterMsgResponse getDisasterMsgList(DisasterMsgRequest reqData) {
    CodeSrchResponse code = codeService.getCode(CodeSrchRequest.builder().upperCode("API_SERVICE_KEY").code("EMERGENCY_DISASTER_MSG").build());

    DisasterMsgHttpClientResponse clientRes =
        disasterMsgHttpClient.getList(code.getCodeValue(), reqData.getCrtDt(), reqData.getRgnNm(), reqData.getNumOfRows(), reqData.getPageNo());

    DisasterMsgResponseBuilder resBuilder = DisasterMsgResponse.builder();

    if (clientRes != null) {
      resBuilder.numOfRows(clientRes.getNumOfRows()).pageNo(clientRes.getPageNo()).totalCount(clientRes.getTotalCount());

      if (clientRes.getHeader() != null) {
        DisasterMsgHeaderResponse clientHeader = clientRes.getHeader();

        DisasterMsgHeaderResponse header = DisasterMsgHeaderResponse.builder().resultCode(clientHeader.getResultCode())
            .resultMsg(clientHeader.getResultMsg()).errorMsg(clientHeader.getErrorMsg()).build();

        resBuilder.header(header);
      }

      if (clientRes.getBody() != null && !clientRes.getBody().isEmpty()) {
        List<DisasterMsgBodyResponse> body = new ArrayList<>();
        List<DisasterMsgBodyHttpClientResponse> clientBody = clientRes.getBody();

        clientBody.stream().forEach(row -> {
          body.add(DisasterMsgBodyResponse.builder().sn(row.getSn()).crtDt(row.getCrtDt()).msgCn(row.getMsgCn()).rcptnRgnNm(row.getRcptnRgnNm())
              .emrgStepNm(row.getEmrgStepNm()).dstSeNm(row.getDstSeNm()).regYmd(row.getRegYmd()).mdfcnYmd(row.getMdfcnYmd()).build());
        });

        resBuilder.body(body);
      }
    }

    return resBuilder.build();
  }
}
