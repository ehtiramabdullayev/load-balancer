package org.example.strategy.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.strategy.ProviderStrategy;

import java.util.Random;

public class RandomStrategy implements ProviderStrategy {
    private static final Logger logger = LogManager.getLogger(RoundRobinStrategy.class);

    @Override
    public Integer pick(Integer size) {
        logger.warn("RandomStrategy invoked {}", size);
        return new Random().nextInt(size);
    }
}
