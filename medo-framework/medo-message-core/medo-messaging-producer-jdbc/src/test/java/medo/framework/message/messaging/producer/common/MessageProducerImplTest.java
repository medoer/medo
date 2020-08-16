package medo.framework.message.messaging.producer.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.stubbing.Answer;

import medo.framework.message.messaging.common.ChannelMapping;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.common.MessageHeader;
import medo.framework.message.messaging.common.MessageInterceptor;
import medo.framework.message.messaging.producer.MessageBuilder;

public class MessageProducerImplTest {

    @Test
    public void shouldSendMessage() {

        ChannelMapping channelMapping = mock(ChannelMapping.class);

        PersistentMessage implementation = mock(PersistentMessage.class);

        MessageProducerImpl mp = new MessageProducerImpl(new MessageInterceptor[0], channelMapping, implementation);

        String transformedDestination = "TransformedDestination";
        String messageID = "1";

        doAnswer((Answer<Void>) invocation -> {
            ((Runnable) invocation.getArgument(0)).run();
            return null;
        }).when(implementation).withContext(any(Runnable.class));

        when(channelMapping.transform("Destination")).thenReturn(transformedDestination);

        when(implementation.generateMessageId()).thenReturn(messageID);

        mp.send("Destination", MessageBuilder.withPayload("x").build());

        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(implementation).save(messageArgumentCaptor.capture());
        Message sendMessage = messageArgumentCaptor.getValue();

        assertEquals(messageID, sendMessage.getRequiredHeader(MessageHeader.ID));
        assertEquals(transformedDestination, sendMessage.getRequiredHeader(MessageHeader.DESTINATION));
        assertNotNull(sendMessage.getRequiredHeader(MessageHeader.DATE));
    }
}