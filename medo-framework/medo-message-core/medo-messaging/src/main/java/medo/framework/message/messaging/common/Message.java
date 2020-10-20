package medo.framework.message.messaging.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author: bryce
 * @date: 2020-08-11
 */
public class Message {

    private String payload;
    private Map<String, String> headers;

    public Message() {}

    public Message(String payload, Map<String, String> headers) {
        this.payload = payload;
        this.headers = headers;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getPayload() {
        return payload;
    }

    public Optional<String> getHeader(String name) {
        return Optional.ofNullable(headers.get(name));
    }

    public String getRequiredHeader(String name) {
        String s = headers.get(name);
        if (s == null)
            throw new RuntimeException("No such header: " + name + " in this message " + this);
        else return s;
    }

    public boolean hasHeader(String name) {
        return headers.containsKey(name);
    }

    public String getId() {
        return getRequiredHeader(MessageHeader.ID);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setHeader(String name, String value) {
        if (headers == null) headers = new HashMap<>();
        headers.put(name, value);
    }

    public void removeHeader(String key) {
        headers.remove(key);
    }
}
