package medo.framework.saga.common;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;
import medo.common.core.json.JSONMapper;
import medo.framework.message.messaging.common.Message;
import medo.framework.message.messaging.common.MessageHeader;
import medo.framework.message.messaging.producer.MessageBuilder;

@Slf4j
public class SagaLockManagerImpl implements SagaLockManager {

    private JdbcTemplate jdbcTemplate;

    private String insertIntoSagaLockTableSql;
    private String insertIntoSagaStashTableSql;
    private String selectFromSagaLockTableSql;
    private String selectFromSagaStashTableSql;
    private String updateSagaLockTableSql;
    private String deleteFromSagaLockTableSql;
    private String deleteFromSagaStashTableSql;

    public SagaLockManagerImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;

        // TODO read properties
        String sagaLockTable = "saga_lock_table";// eventuateSchema.qualifyTable("saga_lock_table");
        String sagaStashTable = "saga_stash_table";// eventuateSchema.qualifyTable("saga_stash_table");

        insertIntoSagaLockTableSql = String.format("INSERT INTO %s(target, saga_type, saga_id) VALUES(?, ?,?)",
                sagaLockTable);
        insertIntoSagaStashTableSql = String.format(
                "INSERT INTO %s(message_id, target, saga_type, saga_id, message_headers, message_payload) VALUES(?, ?,?, ?, ?, ?)",
                sagaStashTable);
        selectFromSagaLockTableSql = String.format("select saga_id from %s WHERE target = ? FOR UPDATE", sagaLockTable);
        selectFromSagaStashTableSql = String.format(
                "select message_id, target, saga_type, saga_id, message_headers, message_payload from %s WHERE target = ? ORDER BY message_id LIMIT 1",
                sagaStashTable);
        updateSagaLockTableSql = String.format("update %s set saga_type = ?, saga_id = ? where target = ?",
                sagaLockTable);
        deleteFromSagaLockTableSql = String.format("delete from %s where target = ?", sagaLockTable);
        deleteFromSagaStashTableSql = String.format("delete from %s where message_id = ?", sagaStashTable);
    }

    public String getInsertIntoSagaLockTableSql() {
        return insertIntoSagaLockTableSql;
    }

    public String getInsertIntoSagaStashTableSql() {
        return insertIntoSagaStashTableSql;
    }

    public String getSelectFromSagaLockTableSql() {
        return selectFromSagaLockTableSql;
    }

    public String getSelectFromSagaStashTableSql() {
        return selectFromSagaStashTableSql;
    }

    public String getUpdateSagaLockTableSql() {
        return updateSagaLockTableSql;
    }

    public String getDeleteFromSagaLockTableSql() {
        return deleteFromSagaLockTableSql;
    }

    public String getDeleteFromSagaStashTableSql() {
        return deleteFromSagaStashTableSql;
    }

    @Override
    public boolean claimLock(String sagaType, String sagaId, String target) {
        while (true)
            try {
                jdbcTemplate.update(insertIntoSagaLockTableSql, target, sagaType, sagaId);
                log.debug("Saga {} {} has locked {}", sagaType, sagaId, target);
                return true;
            } catch (DuplicateKeyException e) {
                Optional<String> owningSagaId = selectForUpdate(target);
                if (owningSagaId.isPresent()) {
                    if (owningSagaId.get().equals(sagaId))
                        return true;
                    else {
                        log.debug("Saga {} {} is blocked by {} which has locked {}", sagaType, sagaId, owningSagaId,
                                target);
                        return false;
                    }
                }
                log.debug("{}  is repeating attempt to lock {}", sagaId, target);
            }
    }

    private Optional<String> selectForUpdate(String target) {
        return jdbcTemplate.query(selectFromSagaLockTableSql, (rs, rowNum) -> rs.getString("saga_id"), target).stream()
                .findFirst();
    }

    @Override
    public void stashMessage(String sagaType, String sagaId, String target, Message message) {

        log.debug("Stashing message from {} for {} : {}", sagaId, target, message);

        jdbcTemplate.update(insertIntoSagaStashTableSql, message.getRequiredHeader(MessageHeader.ID), target, sagaType,
                sagaId, JSONMapper.toJSON(message.getHeaders()), message.getPayload());
    }

    @Override
    public Optional<Message> unlock(String sagaId, String target) {
        Optional<String> owningSagaId = selectForUpdate(target);

        if (!owningSagaId.isPresent()) {
            throw new RuntimeException("owningSagaId is not present");
        }

        if (!owningSagaId.get().equals(sagaId)) {
            throw new RuntimeException(String.format("Expected owner to be %s but is %s", sagaId, owningSagaId.get()));
        }

        log.debug("Saga {} has unlocked {}", sagaId, target);

        List<StashedMessage> stashedMessages = jdbcTemplate.query(selectFromSagaStashTableSql, (rs, rowNum) -> {
            return new StashedMessage(rs.getString("saga_type"), rs.getString("saga_id"),
                    MessageBuilder.withPayload(rs.getString("message_payload"))
                            .withExtraHeaders("", JSONMapper.fromJSON(rs.getString("message_headers"), Map.class))
                            .build());
        }, target);

        if (stashedMessages.isEmpty()) {
            assertEqualToOne(jdbcTemplate.update(deleteFromSagaLockTableSql, target));
            return Optional.empty();
        }

        StashedMessage stashedMessage = stashedMessages.get(0);

        log.debug("unstashed from {}  for {} : {}", sagaId, target, stashedMessage.getMessage());

        assertEqualToOne(jdbcTemplate.update(updateSagaLockTableSql, stashedMessage.getSagaType(),
                stashedMessage.getSagaId(), target));
        assertEqualToOne(jdbcTemplate.update(deleteFromSagaStashTableSql, stashedMessage.getMessage().getId()));

        return Optional.of(stashedMessage.getMessage());
    }

    private void assertEqualToOne(int n) {
        if (n != 1)
            throw new RuntimeException("Expected to update one row but updated: " + n);
    }

}
