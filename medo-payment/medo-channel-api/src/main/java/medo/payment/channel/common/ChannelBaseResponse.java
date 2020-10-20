package medo.payment.channel.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class ChannelBaseResponse<T> {
    private T data;
    private ChannelState code;

    public static <T> ChannelBaseResponse<T> succeed(T data) {
        return (ChannelBaseResponse<T>)
                ChannelBaseResponse.builder().code(ChannelState.SUCCESS).data(data).build();
    }

    public static <T> ChannelBaseResponse<T> failed(T data) {
        return (ChannelBaseResponse<T>)
                ChannelBaseResponse.builder().code(ChannelState.FAIL).data(data).build();
    }

    public static <T> ChannelBaseResponse<T> error(T data) {
        return (ChannelBaseResponse<T>)
                ChannelBaseResponse.builder().code(ChannelState.ERROR).data(data).build();
    }

    public boolean isSuccess() {
        return code.equals(ChannelState.SUCCESS);
    }

    public boolean isError() {
        return code.equals(ChannelState.ERROR);
    }

    public boolean isFail() {
        return code.equals(ChannelState.FAIL);
    }

    /** common channel response state */
    private static enum ChannelState {
        SUCCESS,
        // response error code
        FAIL,
        // invoke error
        ERROR,
    }
}
