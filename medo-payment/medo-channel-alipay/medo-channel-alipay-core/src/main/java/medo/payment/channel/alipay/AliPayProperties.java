package medo.payment.channel.alipay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "channel.alipay.config")
public class AliPayProperties {
    /** 通信协议，通常填写https */
    public String protocol = "https";

    /** 网关域名 线上为：openapi.alipay.com 沙箱为：openapi.alipaydev.com */
    public String gatewayHost = "openapi.alipay.com";

    /** AppId */
    public String appId;

    /** 签名类型，Alipay Easy SDK只推荐使用RSA2，估此处固定填写RSA2 */
    public String signType = "RSA2";

    /** 支付宝公钥 */
    public String alipayPublicKey;

    /** 应用私钥 */
    public String merchantPrivateKey;

    /** 应用公钥证书文件路径 */
    public String merchantCertPath;

    /** 支付宝公钥证书文件路径 */
    public String alipayCertPath;

    /** 支付宝根证书文件路径 */
    public String alipayRootCertPath;

    /** 异步通知回调地址（可选） */
    public String notifyUrl;

    /** AES密钥（可选） */
    public String encryptKey;

    /** 签名提供方的名称(可选)，例：Aliyun KMS签名，signProvider = "AliyunKMS" */
    public String signProvider;
}
