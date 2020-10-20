package medo.payment.channel.alipay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import medo.common.core.json.JSONMapper;

public class AliPayFactory {

    public static AliPayProperties aliPayProperties;

    private AliPayFactory(AliPayProperties aliPayProperties) {
        this.aliPayProperties = aliPayProperties;
    }

    public static AliPayFactory create(AliPayProperties aliPayProperties) {
        Factory.setOptions(getOptions(aliPayProperties));
        return new AliPayFactory(aliPayProperties);
    }

    private static Config getOptions(AliPayProperties aliPayProperties) {
        String jsonStr = JSONMapper.toJSON(aliPayProperties);
        return JSONMapper.fromJSON(jsonStr, Config.class);
    }
}
