package medo.payment.web;

import lombok.Data;
import medo.common.spring.request.RequestContextHelper;
import medo.payment.common.domain.Money;
import medo.payment.domain.Terminal;
import medo.payment.common.ChannelId;

import javax.validation.constraints.NotEmpty;

@Data
public class MicroPayRequest {

    public MicroPayRequest() {
        Long channelId = getChannelId();
        // set channel id in header
        RequestContextHelper.setAttribute(ChannelId.HEADER_NAME, channelId);
        this.channelId = channelId;
    }

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
