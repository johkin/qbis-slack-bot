package se.acrend.slack.qbis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.acrend.slack.qbis.entity.User;

/**
 *
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findBySlackHandle(String slackHandle);

}
