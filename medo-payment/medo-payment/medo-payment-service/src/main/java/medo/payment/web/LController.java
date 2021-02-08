package medo.payment.web;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import medo.payment.domain.LhRepository;
import medo.payment.domain.Ll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/ll")
public class LController {

    @Resource private LhRepository lhRepository;

    @GetMapping
    public void generateQR(String type) {
        Ll ll = new Ll();
        ll.setType(type);
        lhRepository.insert(ll);
    }
}
