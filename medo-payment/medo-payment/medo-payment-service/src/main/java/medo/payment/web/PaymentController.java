package medo.payment.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import medo.payment.domain.PaymentRepository;
import medo.payment.domain.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {

    @Autowired private PaymentRepository paymentRepository;

    @Autowired private PaymentService paymentService;

    @GetMapping("/generate/qr")
    public ResponseEntity generateQR(String token) {
        return ResponseEntity.ok("http://localhost:8080/payment/scan?token=" + token);
    }

    @GetMapping("/scan")
    public ResponseEntity scan(
            String token, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // parse token
        // static qr with money
        // redirect to pre create
        String encodeRedirectURL =
                response.encodeRedirectURL(
                        "http://localhost:8080/payment/pre/create?token=" + token);
        response.sendRedirect(encodeRedirectURL);
        return ResponseEntity.ok(1);
    }

    @PostMapping("/pre/create")
    public ResponseEntity preCreate(String token) {
        // invoke channel to create a pre payment order
        // return a token to invoke user's app to pay
        String preCreate = paymentService.preCreate();
        return ResponseEntity.ok(preCreate);
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
    public ResponseEntity authCallback(HttpServletRequest request) {
        return null;
    }

    @RequestMapping(
            method = {RequestMethod.GET, RequestMethod.POST},
            path = "/notify")
    public ResponseEntity paymentNotify(HttpServletRequest request) {
        // use HttpServletRequest to catch all channel's notification
        return null;
    }

    @RequestMapping(
            method = {RequestMethod.GET, RequestMethod.POST},
            path = "/refund/notify")
    public ResponseEntity refundNotify(HttpServletRequest request) {
        return null;
    }

    @PostMapping("/micro")
    public ResponseEntity microPay(@RequestBody @Valid MicroPayRequest microPayRequest) {
        paymentService.microPay(microPayRequest);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/refund")
    public ResponseEntity refund(@RequestBody @Valid RefundRequest refundRequest) {
        paymentService.refund(refundRequest);
        return ResponseEntity.ok(null);
    }
}
