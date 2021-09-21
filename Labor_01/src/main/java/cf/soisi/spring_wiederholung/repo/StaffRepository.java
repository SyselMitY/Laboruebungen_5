package cf.soisi.spring_wiederholung.repo;

import cf.soisi.spring_wiederholung.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff,String> {

}
