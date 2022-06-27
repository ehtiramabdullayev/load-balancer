package org.example.factory;

import org.example.exception.ProviderNotFoundException;
import org.example.provider.Provider;
import org.example.exception.MaxNumberOfProvidersException;
import org.example.exception.NoProviderRegisteredException;

import java.util.List;

public interface LoadBalancer {
    void registerProviders(List<Provider> providers) throws MaxNumberOfProvidersException;
    Provider getProvider() throws NoProviderRegisteredException;
    Provider excludeProvider(String id) throws ProviderNotFoundException;
}
