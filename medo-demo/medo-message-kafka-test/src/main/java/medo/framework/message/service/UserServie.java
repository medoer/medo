package medo.framework.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import medo.framework.message.messaging.producer.MessageBuilder;
import medo.framework.message.messaging.producer.MessageProducer;

@Service
public class UserServie {

    @Autowired
    private MessageProducer messageProducer;
    
    @Transactional
    public void createService() {
        // to save the user to user table

        // send message
        User user = new User();
        user.setName("test");
        String jsonString = JSONObject.toJSONString(user);

        messageProducer.send("Destination", MessageBuilder.withPayload(jsonString).build());
    }

}

@Data
class User {
    private String name;
}
