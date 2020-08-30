package medo.framework.saga.orchestration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.extern.slf4j.Slf4j;
import medo.common.core.id.IdGenerator;

@Slf4j
public class SagaInstanceRepositoryJdbc implements SagaInstanceRepository {

    private JdbcTemplate jdbcTemplate;
    
    private IdGenerator idGenerator;

    private String insertIntoSagaInstanceSql;
    private String insertIntoSagaInstanceParticipantsSql;

    private String selectFromSagaInstanceSql;
    private String selectFromSagaInstanceParticipantsSql;

    private String updateSagaInstanceSql;

    public SagaInstanceRepositoryJdbc(JdbcTemplate jdbcTemplate,
            IdGenerator idGenerator) {
        this.jdbcTemplate = jdbcTemplate;
        this.idGenerator = idGenerator;

        String sagaInstanceTable = "saga_instance"; //eventuateSchema.qualifyTable("saga_instance");
        String sagaInstanceParticipantsTable = "saga_instance_participants"; //eventuateSchema.qualifyTable("saga_instance_participants");

        insertIntoSagaInstanceSql = String.format(
                "INSERT INTO %s(saga_type, saga_id, state_name, last_request_id, saga_data_type, saga_data_json, end_state, compensating) VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                sagaInstanceTable);
        insertIntoSagaInstanceParticipantsSql = String.format(
                "INSERT INTO %s(saga_type, saga_id, destination, resource) values(?,?,?,?)",
                sagaInstanceParticipantsTable);

        selectFromSagaInstanceSql = String.format("SELECT * FROM %s WHERE saga_type = ? AND saga_id = ?",
                sagaInstanceTable);
        selectFromSagaInstanceParticipantsSql = String.format(
                "SELECT destination, resource FROM %s WHERE saga_type = ? AND saga_id = ?",
                sagaInstanceParticipantsTable);

        updateSagaInstanceSql = String.format(
                "UPDATE %s SET state_name = ?, last_request_id = ?, saga_data_type = ?, saga_data_json = ?, end_state = ?, compensating = ? where saga_type = ? AND saga_id = ?",
                sagaInstanceTable);
    }

    public String getInsertIntoSagaInstanceSql() {
        return insertIntoSagaInstanceSql;
    }

    public String getInsertIntoSagaInstanceParticipantsSql() {
        return insertIntoSagaInstanceParticipantsSql;
    }

    public String getSelectFromSagaInstanceSql() {
        return selectFromSagaInstanceSql;
    }

    public String getSelectFromSagaInstanceParticipantsSql() {
        return selectFromSagaInstanceParticipantsSql;
    }

    public String getUpdateSagaInstanceSql() {
        return updateSagaInstanceSql;
    }

    @Override
    public void save(SagaInstance sagaInstance) {
        sagaInstance.setId(idGenerator.generateId().asString());
        log.info("Saving {} {}", sagaInstance.getSagaType(), sagaInstance.getId());
        jdbcTemplate.update(insertIntoSagaInstanceSql, sagaInstance.getSagaType(),
                sagaInstance.getId(), sagaInstance.getStateName(), sagaInstance.getLastRequestId(),
                sagaInstance.getSerializedSagaData().getSagaDataType(),
                sagaInstance.getSerializedSagaData().getSagaDataJSON(), sagaInstance.isEndState(),
                sagaInstance.isCompensating());

        saveDestinationsAndResources(sagaInstance);
    }

    private void saveDestinationsAndResources(SagaInstance sagaInstance) {
        for (DestinationAndResource dr : sagaInstance.getDestinationsAndResources()) {
            try {
                jdbcTemplate.update(insertIntoSagaInstanceParticipantsSql, sagaInstance.getSagaType(),
                        sagaInstance.getId(), dr.getDestination(), dr.getResource());
            } catch (DuplicateKeyException e) {
                log.info("key duplicate: sagaType = {}, sagaId = {}, destination = {}, resource = {}",
                        sagaInstance.getSagaType(), sagaInstance.getId(), dr.getDestination(), dr.getResource());
            }
        }
    }

    @Override
    public SagaInstance find(String sagaType, String sagaId) {
        log.info("finding {} {}", sagaType, sagaId);

        Set<DestinationAndResource> destinationsAndResources = new HashSet<>(jdbcTemplate.query(
                selectFromSagaInstanceParticipantsSql,
                (rs, rownum) -> new DestinationAndResource(rs.getString("destination"), rs.getString("resource")),
                sagaType, sagaId));

        return jdbcTemplate
                .query(selectFromSagaInstanceSql,
                        (rs, rownum) -> new SagaInstance(sagaType, sagaId, rs.getString("state_name"),
                                rs.getString("last_request_id"),
                                new SerializedSagaData(rs.getString("saga_data_type"), rs.getString("saga_data_json")),
                                destinationsAndResources),
                        sagaType, sagaId)
                .stream().findFirst().orElseThrow(
                        () -> new RuntimeException(String.format("Cannot find saga instance %s %s", sagaType, sagaId)));
    }

    @Override
    public void update(SagaInstance sagaInstance) {
        log.info("Updating {} {}", sagaInstance.getSagaType(), sagaInstance.getId());
        int count = jdbcTemplate.update(updateSagaInstanceSql, sagaInstance.getStateName(),
                sagaInstance.getLastRequestId(), sagaInstance.getSerializedSagaData().getSagaDataType(),
                sagaInstance.getSerializedSagaData().getSagaDataJSON(), sagaInstance.isEndState(),
                sagaInstance.isCompensating(), sagaInstance.getSagaType(), sagaInstance.getId());

        if (count != 1) {
            throw new RuntimeException("Should be 1 : " + count);
        }

        saveDestinationsAndResources(sagaInstance);
    }

}
