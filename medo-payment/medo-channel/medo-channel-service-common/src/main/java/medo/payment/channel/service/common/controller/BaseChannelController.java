package medo.payment.channel.service.common.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import medo.payment.channel.ChannelClient;
import medo.payment.channel.common.ChannelBaseResponse;
import medo.payment.channel.common.ChannelServiceURIConstant;
import medo.payment.channel.request.ChannelMicroPayRequest;
import medo.payment.channel.response.ChannelMicroPayResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** TODO 请求参数添加校验 */
@RestController
public class BaseChannelController {

    @Resource private ChannelClient channelClient;

    @PostMapping(ChannelServiceURIConstant.MICRO_PAY_URI)
    public ResponseEntity microPay(
            @RequestBody @Valid ChannelMicroPayRequest channelMicroPayRequest) {
        ChannelBaseResponse<ChannelMicroPayResponse> channelMicroPayResponseChannelBaseResponse =
                channelClient.microPay(channelMicroPayRequest);
        return ResponseEntity.ok(channelMicroPayResponseChannelBaseResponse);
    }
}
