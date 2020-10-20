package medo.framework.message.messaging.common;

/**
 * message consumer channel.
 *
 * @author: bryce
 * @date: 2020-08-11
 */
public interface ChannelMapping {

    String transform(String channel);
}
