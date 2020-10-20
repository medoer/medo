package tech.medo.demo.seata.business.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// @RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// @AutoConfigureMockMvc
public class BusinessControllerTest {

    @Autowired private MockMvc mockMvc;

    // @Test
    public void testPlaceOrder() throws Exception {
        // 下单场景测试-正常
        mockMvc.perform(post("/placeOrder").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    // @Test
    public void testPlaceOrderFallBack() throws Exception {
        // 下单场景测试-回滚
        mockMvc.perform(post("/placeOrderFallBack").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }
}
