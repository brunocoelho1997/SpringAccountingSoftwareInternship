package hello.PostEmployee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostEmployeeService {

    @Autowired
    PostEmployeeRepository repository;

    public List<PostEmployee> getAllPostsEmployees()
    {
        return repository.findAll();
    }
}
