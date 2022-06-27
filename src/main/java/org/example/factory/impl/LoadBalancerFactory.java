package org.example.factory.impl;

import org.example.exception.MaxNumberOfProvidersException;
import org.example.provider.Provider;
import org.example.strategy.ProviderStrategy;

import java.util.List;

public class LoadBalancerFactory {

    private LoadBalancerFactory() {
    }

    public static class LoadBalancerBuilder {
        private int maxAcceptedProviders;

        private ProviderStrategy strategy;

        private List<Provider> providers;

        public LoadBalancerBuilder strategy(ProviderStrategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public LoadBalancerBuilder maxAcceptedProviders(int maxAcceptedProviders) {
            this.maxAcceptedProviders = maxAcceptedProviders;
            return this;
        }

        public LoadBalancerBuilder providers(List<Provider> providers) {
            this.providers = providers;
            return this;
        }

        public LoadBalancerImpl build() throws MaxNumberOfProvidersException {
            LoadBalancerImpl loadBalancer = new LoadBalancerImpl(providers, this.strategy);
            loadBalancer.setMaxAcceptedProviders(maxAcceptedProviders);
            return loadBalancer;
        }
    }

    public static LoadBalancerBuilder builder() {
        return new LoadBalancerBuilder();
    }
}