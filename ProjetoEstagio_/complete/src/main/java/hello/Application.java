package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@SpringBootApplication
public class Application{


    public static final int INITIAL_PAGE_SIZE = 2;
    public static final int INITIAL_PAGE = 0;
    public static final int[] PAGE_SIZES = {2,10,20};
    public static final int BUTTONS_TO_SHOW = 5;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource msgSrc = new ReloadableResourceBundleMessageSource();
        msgSrc.setDefaultEncoding("UTF-8");
        msgSrc.setBasename("classpath:/messages");
        return msgSrc;
    }
}
