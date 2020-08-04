package medo.common.swagger2;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class SwaggerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        MvcResult andReturn = mockMvc.perform(get("/v2/api-docs"))
        .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), andReturn.getResponse().getStatus());
    }

}
