package org.example.factory.impl;

import org.example.Provider;
import org.example.exception.MaxNumberOfProvidersException;
import org.example.factory.LoadBalancer;
import org.example.strategy.ProviderStrategy;

import java.util.Collections;
import java.util.List;

public class LoadBalancerImpl implements LoadBalancer {
    private List<Provider> providers;
    //    private final Set<Provider> excludedProviders;
    private int maxAcceptedProviders = 10;

    private final ProviderStrategy providerStrategy;

    LoadBalancerImpl(List<Provider> providers, ProviderStrategy providerStrategy, int maxAcceptedProviders) throws MaxNumberOfProvidersException {
        registerProviders(providers);
        this.providerStrategy = providerStrategy;
        this.maxAcceptedProviders = maxAcceptedProviders;
    }

    @Override
    public Provider getProvider() {
        return providers.get(providerStrategy.pick(providers.size()));
    }

    @Override
    public void registerProviders(List<Provider> providers) throws MaxNumberOfProvidersException {
        checkProvidersSize(providers);
        this.providers = Collections.unmodifiableList(providers);

    }

    public void checkProvidersSize(List<Provider> providers) throws MaxNumberOfProvidersException {
        if (providers.size() > maxAcceptedProviders) {
            throw new MaxNumberOfProvidersException("Allowed capacity is overreached!");
        }

    }

//    public synchronized boolean excludeProvider(Provider<T> provider) {
//        return this.excludedProviders.add(provider);
//    }
}
