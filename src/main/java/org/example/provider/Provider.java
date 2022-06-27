package org.example.provider;

import java.util.concurrent.atomic.AtomicInteger;

import static org.example.util.Constants.MAX_NUMBER_OF_CONCURRENT_CALLS;

public class Provider {
    private final String id;

    private ProviderStatus status;

    private boolean healthy;

    private final AtomicInteger healthCheckCount = new AtomicInteger(0);


    private final AtomicInteger countOfCurrentCalls = new AtomicInteger(0);


    public Provider(String id) {
        this.id = id;
        this.status = ProviderStatus.ACTIVE;
        this.healthy = true;
    }

    public String getId() {
        return id;
    }

    public ProviderStatus getStatus() {
        return status;
    }

    public void setStatus(ProviderStatus status) {
        this.status = status;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public int getHealthCheckCount() {
        return healthCheckCount.get();
    }

    public void incrementHealthCheckCount() {
        healthCheckCount.incrementAndGet();
    }

    public void resetHeathCheckCount() {
        healthCheckCount.set(0);
    }

    public boolean canHandle() {
        if (countOfCurrentCalls.get() < MAX_NUMBER_OF_CONCURRENT_CALLS) {
            countOfCurrentCalls.addAndGet(1);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Provider{" + "id='" + id + '\'' + ", status=" + status + '}';
    }
}
