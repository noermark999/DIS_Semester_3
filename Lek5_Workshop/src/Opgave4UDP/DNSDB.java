package Opgave4UDP;

import java.util.HashMap;

public class DNSDB {
    private static HashMap<String, String> adresser = new HashMap<>();

    public static void addAddress(String key, String value) {
        adresser.put(key, value);
    }

    public static HashMap<String, String> getAdresser() {
        return adresser;
    }
}
