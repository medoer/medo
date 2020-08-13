package medo.common.kafka.common;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import io.eventuate.messaging.kafka.common.sbe.MultiMessageEncoder;

public class KafkaMultiMessage extends KeyValue {

    private List<KafkaMultiMessageHeader> headers;

    public KafkaMultiMessage(String key, String value) {
        this(key, value, Collections.emptyList());
    }

    public KafkaMultiMessage(String key, String value, List<KafkaMultiMessageHeader> headers) {
        super(key, value);
        this.headers = headers;
    }

    public List<KafkaMultiMessageHeader> getHeaders() {
        return headers;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public int estimateSize() {
        int headerSize = MultiMessageEncoder.MessagesEncoder.HeadersEncoder.HEADER_SIZE;
        int messagesSize = KeyValue.estimateSize(headers);
        return super.estimateSize() + headerSize + messagesSize;
    }

}
