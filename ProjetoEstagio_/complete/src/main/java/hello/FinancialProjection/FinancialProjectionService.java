package hello.FinancialProjection;

import hello.Enums.Genre;
import hello.Transaction.Transaction;
import hello.Transaction.TransactionRepository;
import hello.Transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FinancialProjectionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Page<Transaction> findAllPageableByGenre(PageRequest pageable, Genre genre) {

        return transactionRepository.findAllByGenreAndExecuted(pageable, genre, false);
    }
}
