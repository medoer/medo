package medo.framework.message.event.publisher;

import java.util.Arrays;
import java.util.List;

import medo.framework.message.event.common.DomainEvent;

/**
 * TODO 框架未使用。
 * 
 * @author: bryce
 * @date: 2020-08-16
 * @param <T>
 */
public class ResultWithEvents<T> {

    public final T result;
    public final List<DomainEvent> events;

    public ResultWithEvents(T result, List<DomainEvent> events) {
        this.result = result;
        this.events = events;
    }

    public ResultWithEvents(T result, DomainEvent... events) {
        this.result = result;
        this.events = Arrays.asList(events);
    }

}
