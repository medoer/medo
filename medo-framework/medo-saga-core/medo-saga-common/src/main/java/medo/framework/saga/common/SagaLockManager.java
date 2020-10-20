package medo.framework.saga.common;

import java.util.Optional;
import medo.framework.message.messaging.common.Message;

public interface SagaLockManager {

    boolean claimLock(String sagaType, String sagaId, String target);

    void stashMessage(String sagaType, String sagaId, String target, Message message);

    Optional<Message> unlock(String sagaId, String target);
}
