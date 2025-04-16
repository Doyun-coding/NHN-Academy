package nhn.academy.model;

import java.util.Locale;

public class Requester {
    private String ip;
    private Locale lang;
    public Requester(String ip, Locale lang) {
        this.ip = ip;
        this.lang = lang;
    }

    public String getIp() {
        return ip;
    }

    public Locale getLang() {
        return lang;
    }
}
