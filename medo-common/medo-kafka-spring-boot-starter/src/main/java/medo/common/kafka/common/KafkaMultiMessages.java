package medo.common.kafka.common;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class KafkaMultiMessages {

    private List<KafkaMultiMessagesHeader> headers;
    private List<KafkaMultiMessage> messages;

    public KafkaMultiMessages(List<KafkaMultiMessage> messages) {
        this(Collections.emptyList(), messages);
    }

    public KafkaMultiMessages(List<KafkaMultiMessagesHeader> headers, List<KafkaMultiMessage> messages) {
        this.headers = headers;
        this.messages = messages;
    }

    public List<KafkaMultiMessagesHeader> getHeaders() {
        return headers;
    }

    public List<KafkaMultiMessage> getMessages() {
        return messages;
    }

    public int estimateSize() {
        return KeyValue.estimateSize(headers) + KeyValue.estimateSize(messages);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers, messages);
    }

}
