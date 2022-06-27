package org.example.factory.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exception.MaxNumberOfProvidersException;
import org.example.exception.ProviderNotFoundException;
import org.example.factory.LoadBalancer;
import org.example.provider.Provider;
import org.example.provider.ProviderStatus;
import org.example.strategy.ProviderStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.example.util.Constants.*;

public class LoadBalancerImpl implements LoadBalancer {
    private static final Logger logger = LogManager.getLogger(LoadBalancerImpl.class);
    private List<Provider> providers;
    private int maxAcceptedProviders = MAX_NUMBER_ACCEPTED_PROVIDERS;

    private final ProviderStrategy providerStrategy;

    LoadBalancerImpl(List<Provider> providers, ProviderStrategy providerStrategy) throws MaxNumberOfProvidersException {
        registerProviders(providers);
        this.providerStrategy = providerStrategy;
    }

    @Override
    public Optional<Provider> getProvider() {
        List<Provider> activeProviders = getAllActiveProviders();

        Provider provider = activeProviders.get(providerStrategy.pick(activeProviders.size()));
        if (provider.canHandle()) {
            return Optional.of(provider);
        } else {
            logger.warn(PROVIDER_NOT_AVAILABLE_TEXT);
            return Optional.empty();
        }
    }

    @Override
    public List<Provider> getAllActiveProviders() {
        return providers
                .stream()
                .filter(provider -> provider.getStatus() == ProviderStatus.ACTIVE)
                .collect(Collectors.toList());
    }

    @Override
    public Provider excludeProvider(String id) throws ProviderNotFoundException {
        Provider provider = providers
                .stream()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new ProviderNotFoundException(PROVIDER_NOT_FOUND_TEXT));
        provider.setStatus(ProviderStatus.PASSIVE);
        return provider;
    }

    @Override
    public void checkHeartBeat() {
        logger.info(HEARTBEAT_CHECKED);
        for (Provider provider : providers) {
            provider.incrementHealthCheckCount();
            if (provider.isHealthy()) {
                if (!getAllActiveProviders().contains(provider)) {
                    provider.setHealthy(false);
                    logger.info(PROVIDER_CHECKED_X_TIME_TEXT, provider.getId(), provider.getHealthCheckCount());

                }
            } else {
                if (provider.getHealthCheckCount() > MAX_HEALTH_CHECK_COUNT) {
                    provider.setHealthy(true);
                    provider.setStatus(ProviderStatus.ACTIVE);
                    provider.resetHeathCheckCount();
                    logger.info(PROVIDER_NUMBER_X_REACTIVATED_TEXT, provider.getId());
                }
            }
        }

    }

    @Override
    public void registerProviders(List<Provider> providers) throws MaxNumberOfProvidersException {
        checkProvidersSize(providers);
        this.providers = Collections.unmodifiableList(providers);

    }


    public void checkProvidersSize(List<Provider> providers) throws MaxNumberOfProvidersException {
        if (providers.size() > maxAcceptedProviders) {
            throw new MaxNumberOfProvidersException(MAX_NUMBER_OF_PROVIDERS_EXCEPTION_TEXT);
        }
    }

    public void setMaxAcceptedProviders(int maxAcceptedProviders) {
        this.maxAcceptedProviders = maxAcceptedProviders;
    }

}
