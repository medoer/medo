package medo.framework.saga.common;

public class SagaLockManagerImplDefaultSchemaTest extends SagaLockManagerImplSchemaTest {

    @Override
    protected SagaLockManagerImpl getSagaLockManager() {
        return new SagaLockManagerImpl(null);
    }

    @Override
    protected String getExpectedInsertIntoSagaLockTable() {
        return String.format("INSERT INTO saga_lock_table(target, saga_type, saga_id) VALUES(?, ?,?)");
    }

    @Override
    protected String getExpectedInsertIntoSagaStashTable() {
        return String.format(
                "INSERT INTO saga_stash_table(message_id, target, saga_type, saga_id, message_headers, message_payload) VALUES(?, ?,?, ?, ?, ?)");
    }

    @Override
    protected String getExpectedSelectFromSagaLockTable() {
        return String.format("select saga_id from saga_lock_table WHERE target = ? FOR UPDATE");
    }

    @Override
    protected String getExpectedSelectFromSagaStashTable() {
        return String.format(
                "select message_id, target, saga_type, saga_id, message_headers, message_payload from saga_stash_table WHERE target = ? ORDER BY message_id LIMIT 1");
    }

    @Override
    protected String getExpectedUpdateSagaLockTable() {
        return String.format("update saga_lock_table set saga_type = ?, saga_id = ? where target = ?");
    }

    @Override
    protected String getExpectedDeleteFromSagaLockTable() {
        return String.format("delete from saga_lock_table where target = ?");
    }

    @Override
    protected String getExpectedDeleteFromSagaStashTable() {
        return String.format("delete from saga_stash_table where message_id = ?");
    }
}
