package hello.ProjectTransaction;

import hello.Contact.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectTransactionRepository extends JpaRepository<ProjectTransaction, Long> {

    List<ProjectTransaction> findByGenre(Genre genre);

}
