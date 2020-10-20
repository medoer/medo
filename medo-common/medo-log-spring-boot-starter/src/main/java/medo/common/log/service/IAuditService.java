package medo.common.log.service;

import medo.common.log.model.Audit;

/**
 * Audit Service.
 *
 * @author: bryce
 * @date: 2020-08-05
 */
public interface IAuditService {

    void save(Audit audit);
}
