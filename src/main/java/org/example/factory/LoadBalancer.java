package org.example.factory;

import org.example.exception.ProviderNotFoundException;
import org.example.provider.Provider;
import org.example.exception.MaxNumberOfProvidersException;

import java.util.List;
import java.util.Optional;

public interface LoadBalancer {
    void registerProviders(List<Provider> providers) throws MaxNumberOfProvidersException;

    Optional<Provider> getProvider();

    List<Provider> getAllActiveProviders();

    Provider excludeProvider(String id) throws ProviderNotFoundException;

    void checkHeartBeat();
}
