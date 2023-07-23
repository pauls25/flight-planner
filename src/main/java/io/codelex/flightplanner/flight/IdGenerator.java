package io.codelex.flightplanner.flight;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdGenerator {

    private volatile AtomicLong id;
    public IdGenerator() {
        this.id = new AtomicLong();
    }

    public long getCurrentId() {
        return id.incrementAndGet();
    }
}
