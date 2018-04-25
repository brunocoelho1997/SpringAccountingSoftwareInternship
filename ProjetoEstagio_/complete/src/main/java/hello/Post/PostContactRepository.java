package hello.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostContactRepository extends JpaRepository<PostContact, Long> {
}