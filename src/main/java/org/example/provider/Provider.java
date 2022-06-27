package org.example.provider;

import java.util.concurrent.atomic.AtomicInteger;

public class Provider {
    private final String id;

    private ProviderStatus status;

    private int maxNumOfConcurrentCalls = 2;
    private final AtomicInteger countOfCurrentCalls = new AtomicInteger(0);


    public Provider(String id) {
        this.id = id;
        this.status = ProviderStatus.ACTIVE;
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

    public boolean canHandleRequest() {
        if (countOfCurrentCalls.get() < maxNumOfConcurrentCalls) {
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
