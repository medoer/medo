package medo.payment.web;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import medo.payment.domain.PaymentRepository;
import medo.payment.domain.PaymentService;
import medo.payment.properties.PaymentProperties;
import medo.payment.request.GenerateQrRequest;
import medo.payment.request.MicroPayRequest;
import medo.payment.request.PreCreateRequest;
import medo.payment.request.RefundRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {

    @Resource private PaymentRepository paymentRepository;

    @Resource private PaymentService paymentService;

    @Resource private PaymentProperties paymentProperties;

    @GetMapping("/generate/qr")
    public String generateQR(@Valid GenerateQrRequest generateQrRequest) {
        generateQrRequest.setSignToken(paymentProperties.getPaymentSignToken());
        String token = generateQrRequest.generateToken();
        return paymentProperties.getHostName() + "/payment/scan?token=" + token;
    }

    /** static qr / static qr with default amount / dynamic qr */
    @GetMapping("/scan")
    public ResponseEntity<?> scan(
            @RequestParam String token, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        GenerateQrRequest generateQrRequest =
                GenerateQrRequest.verifyToken(token, paymentProperties.getPaymentSignToken());
        // check
        generateQrRequest.checkParam();
        if (generateQrRequest.isFixedStaticQR()) {
            // redirect to channel app's qr code
            String qrCode =
                    paymentService.preCreate(PreCreateRequest.create(request, generateQrRequest));
            response.sendRedirect(qrCode);
            return ResponseEntity.ok(qrCode);
        }
        if (generateQrRequest.isStaticQR()) {
            // redirect to payment service cashier page
            response.sendRedirect(paymentProperties.getCashierHostName() + "/h5/payment?token=" + token);
            return ResponseEntity.ok("");
        }
        if (generateQrRequest.isDynamicQR()) {
            // TODO check qr code if overdue
            // redirect to channel app's qr code
            String qrCode =
                    paymentService.preCreate(PreCreateRequest.create(request, generateQrRequest));
            response.sendRedirect(qrCode);
            return ResponseEntity.ok(qrCode);
        }
        // TODO redirect to error page
        throw new RuntimeException("Unsupported QR type!");
    }

    /**
     * User confirm to pay by Cashier Page
     *
     * @param request
     * @return
     */
    @PostMapping("/submit")
    public ResponseEntity<String> submit(
            @RequestBody PreCreateRequest preCreateRequest, HttpServletRequest request) {
        // invoke channel to create a pre payment order
        // return a token to invoke user's app to pay
        preCreateRequest.setChannelId(request);
        String qrCode = paymentService.preCreate(preCreateRequest);
        return ResponseEntity.ok(qrCode);
    }

    /**
     * channel auth
     *
     * @param request
     * @return
     */
    @RequestMapping(
            method = {RequestMethod.GET, RequestMethod.POST},
            path = "/auth/callback")
    public ResponseEntity<?> authCallback(HttpServletRequest request) {
        return null;
    }

    @RequestMapping(
            method = {RequestMethod.GET, RequestMethod.POST},
            path = "/notify")
    public ResponseEntity<?> paymentNotify(HttpServletRequest request) {
        // use HttpServletRequest to catch all channel's notification
        return null;
    }

    @RequestMapping(
            method = {RequestMethod.GET, RequestMethod.POST},
            path = "/refund/notify")
    public ResponseEntity<?> refundNotify(HttpServletRequest request) {
        return null;
    }

    @PostMapping("/micro")
    public ResponseEntity<?> microPay(@RequestBody @Valid MicroPayRequest microPayRequest) {
        paymentService.microPay(microPayRequest);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/refund")
    public ResponseEntity<?> refund(@RequestBody @Valid RefundRequest refundRequest) {
        paymentService.refund(refundRequest);
        return ResponseEntity.ok(null);
    }
}
