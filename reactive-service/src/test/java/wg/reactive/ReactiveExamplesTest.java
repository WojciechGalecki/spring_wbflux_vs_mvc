package wg.reactive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import wg.reactive.exception.CustomException;

public class ReactiveExamplesTest {

    @Test
    public void onNextMono() {
        Mono<Integer> publisher = Mono.just(1)
            .log(); // log to console

        StepVerifier
            .create(publisher)
            .expectNext(1)
            .verifyComplete();
    }

    @Test
    public void onNextFlux() {
        Flux<String> publisher = Flux.range(1, 5)
            .map(Object::toString)
            .doOnSubscribe(subscription -> System.out.println("onSubscribe: subscribed"))
            .doOnError(error -> System.err.println("onError: " + error)) // sout - IO, blocking call!!!
            .doOnComplete(() -> System.out.println("onComplete: completed"))
            .log();

        StepVerifier
            .create(publisher)
            .expectNext("1", "2", "3", "4", "5")
            .verifyComplete();
    }

    @Test
    public void onError() {
        Flux<String> publisher = Flux.fromIterable(List.of("a", "b", "c"))
            .concatWith(Flux.error(new RuntimeException("Flux error")))
            .log();

        StepVerifier
            .create(publisher)
            .expectNext("a", "b", "c")
            .expectErrorMessage("Flux error")
            .verify();
    }

    @Test
    public void onErrorReturn() {
        Flux<String> publisher = Flux.fromIterable(List.of("a", "b", "c"))
            .doOnNext(i -> {
                if (i.equals("b")) {
                    throw new RuntimeException("Flux error");
                }
            })
            .onErrorReturn("default")
            .log();

        StepVerifier
            .create(publisher)
            .expectNext("a", "default")
            .verifyComplete();
    }

    @Test
    public void onErrorContinue() {
        Flux<String> publisher = Flux.fromIterable(List.of("a", "b", "c"))
            .doOnNext(i -> {
                if (i.equals("b")) {
                    throw new RuntimeException("Flux error");
                }
            })
            .onErrorContinue((throwable, o) -> System.err.println("onErrorContinue"))
            .log();

        StepVerifier
            .create(publisher)
            .expectNext("a", "c")
            .verifyComplete();
    }

    @Test
    public void onErrorMap() {
        Mono<Object> publisher = Mono.error(new RuntimeException("Mono error"))
            .onErrorMap(RuntimeException.class, CustomException::new)
            .log();

        StepVerifier
            .create(publisher)
            .expectError(CustomException.class)
            .verify();
    }

    @Test
    public void onFlatMap() {
        String[] strings = new String[100];
        Arrays.fill(strings, "test");

        Flux<String> publisher = Flux.fromArray(strings)
            .flatMap(s -> Flux.just(s.split("")))
            .log();

        StepVerifier
            .create(publisher)
            .expectNextCount(400) // 't' 'e' 's' 't' (4) * 100
            .verifyComplete();
    }

    @Test
    public void onBackPressure() {
        Flux<Integer> publisher = Flux.range(1, 100).log();

        List<Integer> list = new ArrayList<>();

        publisher
            //.onBackpressureDrop(i -> System.out.println("Dropped: " + i))
            //.onBackpressureBuffer(10, i -> System.out.println("BUFFERED: " + i))
            //.onBackpressureDrop(list::add)
            .onBackpressureBuffer(1, list::add)
            .subscribe(new BaseSubscriber<>() {
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("next " + value);
            }
        });

        Flux.fromIterable(list)
            .log()
            .subscribeOn(Schedulers.newSingle("NEW-SINGLE"))
            .subscribe();
    }
}
