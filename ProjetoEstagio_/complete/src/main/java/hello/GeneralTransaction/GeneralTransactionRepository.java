package hello.GeneralTransaction;

import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface GeneralTransactionRepository extends JpaRepository<GeneralTransaction, Long>, JpaSpecificationExecutor {

    GeneralTransaction findById(long id);
    Page<GeneralTransaction> findAll(Pageable pageable);


    Page<GeneralTransaction> findAllByGenreAndExecutedAndActived(Pageable pageable, Genre genre, boolean executed, boolean actived);
    List<GeneralTransaction> findDistinctByGenreAndActived(Genre genre, boolean actived);

}
