package custom.capstone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableWebSecurity
@EnableJpaAuditing
@SpringBootApplication
public class MuseApplication {
    public static void main(String[] args) {
        SpringApplication.run(MuseApplication.class, args);
    }
}
