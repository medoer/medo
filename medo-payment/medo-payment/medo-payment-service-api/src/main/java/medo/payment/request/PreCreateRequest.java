package medo.payment.request;

import lombok.Data;
import medo.common.spring.request.RequestContextHelper;
import medo.payment.channel.common.ChannelId;
import medo.payment.channel.request.ChannelPreCreateRequest;
import medo.payment.common.domain.Money;
import medo.payment.domain.Terminal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;

@Data
public class PreCreateRequest {

    public PreCreateRequest(HttpServletRequest request) {
        Long channelId = ChannelId.getChannelId(request);
        // set channel id in request attribute
        RequestContextHelper.setAttribute(ChannelId.HEADER_NAME, channelId);
        this.channelId = channelId;
    }
    
    public static PreCreateRequest create(HttpServletRequest request, GenerateQrRequest generateQrRequest) {
        PreCreateRequest preCreateRequest = new PreCreateRequest(request);
        preCreateRequest.setDesc(generateQrRequest.getDesc());
        preCreateRequest.setMoney(new Money(generateQrRequest.getAmount()));
        return preCreateRequest;
    }

    public ChannelPreCreateRequest buildChannelPreCreateRequest(String id) {
        return ChannelPreCreateRequest.builder()
                .paymentId(id)
                .money(money)
                .subject(desc)
                .build();
    }

    private PreCreateRequest() {}

    @NotEmpty private Money money;

    private Long channelId;

    private Terminal terminal;

    private String desc;

}
