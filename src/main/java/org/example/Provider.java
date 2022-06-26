package org.example;

public class Provider {
    private final String ip;
    private final String id;

    public Provider(String ip) {
        this.ip = ip;
        this.id = "id";
    }

    public String getIp() {
        return ip;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Provider{" +
                "ip='" + ip + '\'' +
                '}';
    }
}
