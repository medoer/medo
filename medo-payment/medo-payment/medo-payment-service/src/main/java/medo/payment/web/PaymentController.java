package medo.payment.web;

import medo.payment.domain.PaymentRepository;
import medo.payment.domain.PaymentService;
import medo.payment.properties.PaymentProperties;
import medo.payment.request.GenerateQrRequest;
import medo.payment.request.MicroPayRequest;
import medo.payment.request.PreCreateRequest;
import medo.payment.request.RefundRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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

    /**
     * static qr / static qr with default amount / dynamic qr
     */
    @GetMapping("/scan")
    public ResponseEntity<?> scan(
            @RequestParam String token, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        GenerateQrRequest generateQrRequest = GenerateQrRequest.verifyToken(token, paymentProperties.getPaymentSignToken());
        if (generateQrRequest.isFixedStaticQR()) {
            // redirect to channel app's qr code
            String qrCode = paymentService.preCreate(PreCreateRequest.create(request, generateQrRequest));
            response.sendRedirect(qrCode);
            return ResponseEntity.ok(qrCode);
        }
        if (generateQrRequest.isStaticQR()) {
            // redirect to payment service cashier page
        }
        if (generateQrRequest.isDynamicQR()) {
            // redirect to channel app's qr code
        }
        throw new RuntimeException("Not Supported QR type!");
    }

    @PostMapping("/pre/create")
    public ResponseEntity<String> preCreate(@RequestParam String token, HttpServletRequest request) {
        // invoke channel to create a pre payment order
        // return a token to invoke user's app to pay
        GenerateQrRequest generateQrRequest = GenerateQrRequest.verifyToken(token, paymentProperties.getPaymentSignToken());
        String qrCode = paymentService.preCreate(PreCreateRequest.create(request, generateQrRequest));
        return ResponseEntity.ok(qrCode);
    }

    /**
     * User confirm to pay by Cashier Page
     *
     * @param token
     * @param request
     * @return
     */
    @PostMapping("/submit")
    public ResponseEntity<String> submit(@RequestParam String token, HttpServletRequest request) {
        // invoke channel to create a pre payment order
        // return a token to invoke user's app to pay
        GenerateQrRequest generateQrRequest = GenerateQrRequest.verifyToken(token, paymentProperties.getPaymentSignToken());
        String qrCode = paymentService.preCreate(PreCreateRequest.create(request, generateQrRequest));
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
