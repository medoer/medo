package tech.medo.demo.seata.account.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.medo.demo.seata.account.service.AccountService;

@RestController
public class AccountController {
    @Resource
    private AccountService accountService;

    /**
     * 账号扣钱.
     */
    @PostMapping(value = "/account/reduce")
    public Boolean reduce(String userId, Integer money) {
        accountService.reduce(userId, money);
        return true;
    }
}
