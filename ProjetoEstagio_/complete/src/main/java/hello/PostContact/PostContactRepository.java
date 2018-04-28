package hello.PostContact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostContactRepository extends JpaRepository<PostContact, Long> {

    PostContact findById(long id);
}