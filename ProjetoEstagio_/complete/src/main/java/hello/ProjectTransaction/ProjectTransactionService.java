package hello.ProjectTransaction;

import hello.Contact.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTransactionService {

    @Autowired
    ProjectTransactionRepository repository;


    public List<ProjectTransaction> getProjectsTransactionsByGenre(Genre genre){
        List<ProjectTransaction> projectTransactions = repository.findByGenre(genre);
        return projectTransactions;
    }

}
