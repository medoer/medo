package medo.payment.web;

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

    @GetMapping("/scan")
    public ResponseEntity scan() {
        return ResponseEntity.ok(1);
    }

    public ResponseEntity paymentNotify() {
        return null;
    }

    public ResponseEntity refundNotify() {
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
