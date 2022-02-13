package wg.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveServiceApplication {
    public static void main(String[] args) {
        //BlockHound.install();
        //It looks like you're running on JDK 13+.
        //You need to add '-XX:+AllowRedefinitionToAddDeleteMethods' JVM flag.

        SpringApplication.run(ReactiveServiceApplication.class, args);
    }
}
