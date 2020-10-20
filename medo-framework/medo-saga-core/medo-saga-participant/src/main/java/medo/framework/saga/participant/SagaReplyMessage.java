package medo.framework.saga.participant;

import java.util.Map;
import java.util.Optional;
import medo.framework.message.messaging.common.Message;
import medo.framework.saga.common.LockTarget;

public class SagaReplyMessage extends Message {

    private Optional<LockTarget> lockTarget;

    public SagaReplyMessage(
            String body, Map<String, String> headers, Optional<LockTarget> lockTarget) {
        super(body, headers);
        this.lockTarget = lockTarget;
    }

    public Optional<LockTarget> getLockTarget() {
        return lockTarget;
    }

    public boolean hasLockTarget() {
        return lockTarget.isPresent();
    }
}
