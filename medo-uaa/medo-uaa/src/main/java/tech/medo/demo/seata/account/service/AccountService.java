package tech.medo.demo.seata.account.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import tech.medo.demo.seata.account.mapper.AccountMapper;
import tech.medo.demo.seata.account.model.Account;

@Service
public class AccountService {

    @Resource
    private AccountMapper accountMapper;

    /**
     * 减账号金额.
     */
    //@Transactional(rollbackFor = Exception.class)
    public void reduce(String userId, final int money) {
        if ("U002".equals(userId)) {
            throw new RuntimeException("this is a mock Exception");
        }

        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new Account().setUserId(userId));
        Account account = accountMapper.selectOne(wrapper);
        account.setMoney(account.getMoney() - money);
        accountMapper.updateById(account);
    }
}
