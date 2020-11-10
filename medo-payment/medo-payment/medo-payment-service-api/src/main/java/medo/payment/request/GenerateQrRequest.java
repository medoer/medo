package medo.payment.request;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medo.payment.common.Sign;
import medo.payment.common.enums.QRType;
import org.apache.commons.lang3.StringUtils;

/** @Author: yangcj @Date: 2020/11/7 23:11 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenerateQrRequest {

    private static final String CLAIM_AMOUNT = "amount";
    private static final String DESC = "desc";
    private static final String QR_TYPE = "qrType";

    private String signToken;
    private String amount;
    private String desc;
    private QRType qrType = QRType.STATIC;

    public String generateToken() {
        // TODO define the duration to config
        int duration = isDynamicQR() ? 2 : 200;
        String token =
                JWT.create()
                        .withClaim(CLAIM_AMOUNT, amount)
                        .withClaim(DESC, desc)
                        .withClaim(QR_TYPE, qrType.name())
                        .withExpiresAt(
                                new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(duration)))
                        .sign(Sign.getAlgorithm(signToken));
        return token;
    }

    public boolean isStaticQR() {
        return QRType.STATIC.equals(qrType);
    }

    /**
     *
     * @return is dynamic qr or not
     */
    public boolean isDynamicQR() {
        return QRType.DYNAMIC.equals(qrType);
    }

    public boolean isFixedStaticQR() {
        return QRType.FIXED_STATIC.equals(qrType);
    }

    public void checkParam() {
        if (isFixedStaticQR() && StringUtils.isEmpty(amount)) {
            throw new IllegalArgumentException("");
        }
        if (isDynamicQR() && StringUtils.isEmpty(amount)) {
            throw new IllegalArgumentException("");
        }
    }

    public static GenerateQrRequest verifyToken(String token, String signToken) {
        DecodedJWT decodedJWT = Sign.verifyToken(token, signToken);
        String amount = decodedJWT.getClaim(CLAIM_AMOUNT).asString();
        String desc = decodedJWT.getClaim(DESC).asString();
        String qrType = decodedJWT.getClaim(QR_TYPE).asString();
        GenerateQrRequest generateQrRequest = new GenerateQrRequest();
        generateQrRequest.setAmount(amount);
        generateQrRequest.setDesc(desc);
        generateQrRequest.setQrType(QRType.valueOf(qrType));
        return generateQrRequest;
    }
}
