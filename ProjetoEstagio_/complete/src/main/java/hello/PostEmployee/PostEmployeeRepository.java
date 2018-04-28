package hello.PostEmployee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEmployeeRepository extends JpaRepository<PostEmployee, Long> {

    PostEmployee findById(long id);
}