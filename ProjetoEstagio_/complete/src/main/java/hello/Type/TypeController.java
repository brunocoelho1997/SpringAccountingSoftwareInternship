package hello.Type;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequestMapping(path="/project")
public class TypeController implements WebMvcConfigurer {
}
