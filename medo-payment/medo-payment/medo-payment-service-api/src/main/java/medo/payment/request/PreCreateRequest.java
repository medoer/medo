package medo.payment.request;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import medo.common.spring.request.RequestContextHelper;
import medo.payment.channel.common.ChannelId;
import medo.payment.channel.request.ChannelPreCreateRequest;
import medo.payment.common.domain.Money;
import medo.payment.common.enums.QRType;
import medo.payment.domain.Terminal;

@Data
public class PreCreateRequest {

    public PreCreateRequest(HttpServletRequest request) {
        Long channelId = ChannelId.getChannelId(request);
        // set channel id in request attribute
        RequestContextHelper.setAttribute(ChannelId.HEADER_NAME, channelId);
        this.channelId = channelId;
    }

    public PreCreateRequest() {}

    public static PreCreateRequest create(
            HttpServletRequest request, GenerateQrRequest generateQrRequest) {
        PreCreateRequest preCreateRequest = new PreCreateRequest(request);
        preCreateRequest.setDesc(generateQrRequest.getDesc());
        preCreateRequest.setAmount(generateQrRequest.getAmount());
        preCreateRequest.setQrType(generateQrRequest.getQrType());
        return preCreateRequest;
    }

    public ChannelPreCreateRequest buildChannelPreCreateRequest(String id) {
        return ChannelPreCreateRequest.builder()
                .paymentId(id)
                .money(new Money(amount))
                .subject(desc)
                .build();
    }

    @NotEmpty private String amount;

    private Long channelId;

    private Terminal terminal;

    private String desc;

    private QRType qrType;

    public void setChannelId(HttpServletRequest request) {
        Long channelId = ChannelId.getChannelId(request);
        // set channel id in request attribute
        RequestContextHelper.setAttribute(ChannelId.HEADER_NAME, channelId);
        this.channelId = channelId;
    }
}
