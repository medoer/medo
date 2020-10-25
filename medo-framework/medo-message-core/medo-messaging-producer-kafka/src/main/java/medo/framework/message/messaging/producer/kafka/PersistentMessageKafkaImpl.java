package medo.framework.message.messaging.producer.kafka;

import medo.common.core.id.IdGenerator;
import medo.common.core.json.JSONMapper;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.common.MessageHeader;
import medo.framework.message.messaging.producer.common.PersistentMessage;
import org.springframework.kafka.core.KafkaTemplate;

public class PersistentMessageKafkaImpl implements PersistentMessage {

    private IdGenerator idGenerator;

    private KafkaTemplate<Object, Object> kafkaTemplate;

    public PersistentMessageKafkaImpl(IdGenerator idGenerator, KafkaTemplate kafkaTemplate) {
        this.idGenerator = idGenerator;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String generateMessageId() {
        return idGenerator.generateId().asString();
    }

    @Override
    public void save(Message message) {
        // TODO 映射 message 和 kafka 关系
        kafkaTemplate.send(message.getRequiredHeader(MessageHeader.DESTINATION), JSONMapper.toJSON(message));
    }
}
