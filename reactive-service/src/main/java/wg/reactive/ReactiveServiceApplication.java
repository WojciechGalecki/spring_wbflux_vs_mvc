package wg.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveServiceApplication {

    public static void main(String[] args) throws InterruptedException {
        // DEBUGGING
        //Hooks.onOperatorDebug(); // DO NOT USE IN PRODUCTION
        //ReactorDebugAgent.init(); // production friendly

        // BLOCK HOUND
        //BlockHound.install();
        //It looks like you're running on JDK 13+.
        //You need to add '-XX:+AllowRedefinitionToAddDeleteMethods' JVM flag.

        SpringApplication.run(ReactiveServiceApplication.class, args);

        // BLOCKING CALL
        //        Mono.delay(Duration.ofSeconds(1))
        //            .doOnNext(it -> {
        //                try {
        //                    Thread.sleep(10);
        //                } catch (InterruptedException e) {
        //                    throw new RuntimeException(e);
        //                }
        //            })
        //            .block();

        // DEBUGGING
        //        Flux.just("a", "b")
        //            .take(1)
        //            //.single()
        //            //.checkpoint("after single")
        //            .log()
        //            .subscribe();
    }
}
