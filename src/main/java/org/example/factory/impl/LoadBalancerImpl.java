package org.example.factory.impl;

import org.example.exception.ProviderNotFoundException;
import org.example.provider.Provider;
import org.example.exception.MaxNumberOfProvidersException;
import org.example.factory.LoadBalancer;
import org.example.provider.ProviderStatus;
import org.example.strategy.ProviderStrategy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LoadBalancerImpl implements LoadBalancer {
    private List<Provider> providers;
    private int maxAcceptedProviders = 10;

    private final ProviderStrategy providerStrategy;

    LoadBalancerImpl(List<Provider> providers,
                     ProviderStrategy providerStrategy,
                     int maxAcceptedProviders
    ) throws MaxNumberOfProvidersException {
        registerProviders(providers);
        this.providerStrategy = providerStrategy;
        this.maxAcceptedProviders = maxAcceptedProviders;
    }

    @Override
    public Provider getProvider() {
        List<Provider> activeProviders = providers.stream().filter(provider -> provider.getStatus() == ProviderStatus.ACTIVE).collect(Collectors.toList());
        Provider provider = activeProviders.get(providerStrategy.pick(activeProviders.size()));
        if (provider.canHandleRequest()) {
            return provider;
        } else /* add logging */ return null;

    }

    @Override
    public Provider excludeProvider(String id) throws ProviderNotFoundException {
        Provider provider = providers.stream().filter(p -> p.getId().equalsIgnoreCase(id)).findFirst().orElseThrow(() -> new ProviderNotFoundException("Provider doesn't exist!"));
        provider.setStatus(ProviderStatus.PASSIVE);
        return provider;
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
