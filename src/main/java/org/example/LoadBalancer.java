package org.example;

import org.example.strategy.ProviderStrategy;

import java.util.Collections;
import java.util.List;

public class LoadBalancer {
    private final List<Provider> providers;
    private final ProviderStrategy providerStrategy;

    public LoadBalancer(List<Provider> providers, ProviderStrategy providerStrategy) {
        this.providers = Collections.unmodifiableList(providers);
        this.providerStrategy = providerStrategy;
    }

    public Provider getProvider() {
        return providers.get(providerStrategy.pick(providers.size()));
    }

}
