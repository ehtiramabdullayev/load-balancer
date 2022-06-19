package org.example;

import org.example.strategy.ProviderStrategy;
import org.example.strategy.impl.RandomStrategy;
import org.example.strategy.impl.RoundRobinStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        List<Provider> providers = new ArrayList<>();
        providers.add(new Provider("1"));
        providers.add(new Provider("2"));
        providers.add(new Provider("3"));
//        providers.add(new Provider("4"));

        LoadBalancer loadBalancer = new LoadBalancer(providers, new RoundRobinStrategy());
        Provider provider1 = loadBalancer.getProvider();
        Provider provider2 = loadBalancer.getProvider();
        Provider provider3 = loadBalancer.getProvider();
        Provider provider4 = loadBalancer.getProvider();
        Provider provider5 = loadBalancer.getProvider();
        System.out.println(provider1);
        System.out.println(provider2);
        System.out.println(provider3);
        System.out.println(provider4);
        System.out.println(provider5);
    }
}
