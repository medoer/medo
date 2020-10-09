package medo.payment.web;

import lombok.Data;
import medo.common.spring.request.RequestContextHelper;
import medo.payment.common.domain.Money;
import medo.payment.domain.Terminal;
import medo.payment.common.ChannelId;

import javax.validation.constraints.NotEmpty;

@Data
public class MicroPayRequest {

    public MicroPayRequest(String authCode) {
        this.authCode = authCode;
        Long channelId = getChannelId();
        // set channel id in request attribute
        RequestContextHelper.setAttribute(ChannelId.HEADER_NAME, channelId);
        this.channelId = channelId;
    }

    public MicroPayRequest() {}

    @NotEmpty
    private String authCode;

    @NotEmpty
    private Money money;

    private Long channelId;

    private Terminal terminal;

    private String desc;

    public Long getChannelId() {
        return ChannelId.getChannelId(authCode);
    }

}
