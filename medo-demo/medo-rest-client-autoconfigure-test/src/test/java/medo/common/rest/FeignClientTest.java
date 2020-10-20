package medo.common.rest;

import medo.common.rest.client.TestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FeignClientTest {

    @Autowired private TestClient testClient;

    @Test(expected = Exception.class)
    public void test() {
        testClient.test("zh-CN");
    }
}
