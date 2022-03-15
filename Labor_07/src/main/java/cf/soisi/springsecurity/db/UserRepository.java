package cf.soisi.springsecurity.db;

import cf.soisi.springsecurity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
