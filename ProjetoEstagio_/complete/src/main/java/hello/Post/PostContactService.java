package hello.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostContactService {

    @Autowired
    private PostContactRepository repository;

    public List<PostContact> getAll()
    {
        return repository.findAll();
    }
    public void addPostContact(PostContact postContact){
        repository.save(postContact);
    }
}
