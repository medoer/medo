package medo.common.log;

import org.springframework.stereotype.Component;

@Component
public class AuditLog {

    @medo.common.log.annotation.AuditLog(operation = "test")
    public void test() {}
}
