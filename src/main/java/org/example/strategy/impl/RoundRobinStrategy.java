package org.example.strategy.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.strategy.ProviderStrategy;

import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinStrategy implements ProviderStrategy {
    private static final Logger logger = LogManager.getLogger(RoundRobinStrategy.class);
    private final AtomicInteger atomicCounter = new AtomicInteger(0);

    @Override
    public Integer pick(Integer size) {
        int nextIndex;
        int counter;
        do {
            counter = atomicCounter.get();
            nextIndex = (counter + 1) % size;
            logger.warn("RoundRobinStrategy invoked {}", nextIndex);
        } while (!atomicCounter.compareAndSet(counter, nextIndex));
        return nextIndex;
    }
}
