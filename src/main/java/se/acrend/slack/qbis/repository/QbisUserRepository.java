package se.acrend.slack.qbis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.acrend.slack.qbis.entity.QbisUser;

/**
 *
 */
@Repository
public interface QbisUserRepository extends CrudRepository<QbisUser, Long> {

    QbisUser findBySlackHandle(String slackHandle);

}
