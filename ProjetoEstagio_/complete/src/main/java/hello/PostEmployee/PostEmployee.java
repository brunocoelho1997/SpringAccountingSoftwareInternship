package hello.PostEmployee;

import hello.Post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "post_employee")
public class PostEmployee extends Post{


}
