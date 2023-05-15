package auxiliaryClasses;

public enum HashingAlgorhitms {
    MD5("MD5"),
    SHA256("SHA-256"),
    SHA512("SHA-512");

    private String _value;


    HashingAlgorhitms(String s) {
        _value = s;
    }

    public String getValue() {
        return _value;
    }
}
