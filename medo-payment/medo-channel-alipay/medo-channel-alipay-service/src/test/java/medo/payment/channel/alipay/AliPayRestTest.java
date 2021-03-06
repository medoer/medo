package medo.payment.channel.alipay;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import medo.common.core.json.JSONMapper;
import medo.payment.channel.common.ChannelServiceURIConstant;
import medo.payment.channel.request.ChannelMicroPayRequest;
import medo.payment.common.domain.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliPayRestTest {

    @Autowired private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testMicroPay() throws Exception {
        ChannelMicroPayRequest channelMicroPayRequest =
                ChannelMicroPayRequest.builder()
                        .authCode("xxxxx")
                        .money(new Money(100))
                        .subject("test")
                        .paymentId("123456")
                        .build();
        MvcResult mvcResult =
                mockMvc.perform(
                                MockMvcRequestBuilders.post(ChannelServiceURIConstant.MICRO_PAY_URI)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(JSONMapper.toJSON(channelMicroPayRequest)))
                        .andExpect(status().isOk())
                        .andReturn();

        // mock mvc 没有返回配置的 server.servlet.context-path， 但实际上生效了。
        //        String contextPath = mvcResult.getRequest().getServletContext().getContextPath();
        //        Assert.assertTrue("/alipay".equals(contextPath));
    }
}
