package medo.common.log;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LogTest {
    
    @Autowired
    private AuditLog auditLog;

    @Test
    public void testLog() {
        auditLog.test();
    }

}
