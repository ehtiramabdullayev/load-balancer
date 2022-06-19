package org.example;

public class Provider {
    private final String ip;

    public Provider(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "ip='" + ip + '\'' +
                '}';
    }
}
