package medo.framework.saga.orchestration;

import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class EnlistedAggregatesDao {

    private JdbcTemplate jdbcTemplate;

    public EnlistedAggregatesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(String sagaId, Set<EnlistedAggregate> enlistedAggregates) {
        for (EnlistedAggregate ela : enlistedAggregates) {
            try {
                jdbcTemplate.update(
                        "INSERT INTO saga_enlisted_aggregates(saga_id, aggregate_type, aggregate_id) values(?,?,?)",
                        sagaId,
                        ela.getAggregateClass(),
                        ela.getAggregateId());
            } catch (DuplicateKeyException e) {
                log.info(
                        "Cannot save aggregate, key duplicate: sagaId = {}, aggregateClass = {}, aggregateId = {}",
                        sagaId,
                        ela.getAggregateClass(),
                        ela.getAggregateId());
                // ignore
            }
        }
    }

    public Set<EnlistedAggregate> findEnlistedAggregates(String sagaId) {
        return new HashSet<>(
                jdbcTemplate.query(
                        "Select aggregate_type, aggregate_id from saga_enlisted_aggregates where saga_id = ?",
                        (rs, rowNum) -> {
                            try {
                                return new EnlistedAggregate(
                                        ClassUtils.getClass(rs.getString("aggregate_type")),
                                        rs.getString("aggregate_id"));
                            } catch (ClassNotFoundException e) {
                                log.error("Class not found", e);
                                throw new RuntimeException("Class not found", e);
                            }
                        },
                        sagaId));
    }

    public Set<String> findSagas(Class aggregateType, String aggregateId) {
        return new HashSet<>(
                jdbcTemplate.query(
                        "Select saga_id from saga_enlisted_aggregates where aggregate_type = ? AND  aggregate_id = ?",
                        (rs, rowNum) -> rs.getString("aggregate_type"),
                        aggregateType,
                        aggregateId));
    }
}
