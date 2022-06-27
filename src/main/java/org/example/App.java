package org.example;

import org.example.exception.MaxNumberOfProvidersException;
import org.example.exception.ProviderNotFoundException;
import org.example.factory.impl.LoadBalancerImpl;
import org.example.factory.impl.LoadBalancerFactory;
import org.example.provider.Provider;
import org.example.strategy.impl.RoundRobinStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws MaxNumberOfProvidersException, ProviderNotFoundException {
        System.out.println("Hello World!");
        List<Provider> providers = new ArrayList<>();
        providers.add(new Provider("1"));
        providers.add(new Provider("2"));
        providers.add(new Provider("3"));

        LoadBalancerImpl loadBalancer = LoadBalancerFactory
                .builder()
                .maxAcceptedProviders(10)
                .providers(providers)
                .strategy(new RoundRobinStrategy())
                .build();

        try {
            Provider provider1 = loadBalancer.getProvider().get();
            Provider provider2 = loadBalancer.getProvider().get();
            Provider provider3 = loadBalancer.getProvider().get();
            Provider provider4 = loadBalancer.getProvider().get();
            Provider provider5 = loadBalancer.getProvider().get();

            loadBalancer.excludeProvider("1");

            loadBalancer.checkHeartBeat();


            System.out.println(provider1);
            System.out.println(provider2);
            System.out.println(provider3);
            System.out.println(provider4);
            System.out.println(provider5);

            loadBalancer.checkHeartBeat();


            System.out.println(loadBalancer.getAllActiveProviders());

        } catch (Exception e) {

        }

    }
}
