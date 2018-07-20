package hello.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void printAll(){
        System.out.println("\n\n\n\n " + transactionRepository.findAll());
    }
}
