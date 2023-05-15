package auxiliaryClasses;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCreator {

    private String _algorhitm;

    public HashCreator(HashingAlgorhitms algorhitm) {
        _algorhitm = algorhitm.getValue();
    }

    public String createHash(String... args){
        StringBuilder sb = new StringBuilder();
        for(String arg : args) {
            sb.append(arg);
        }
        String strongHash = "No hash was produced. NoSuchAlgorithmException was thrown";
        try {
            strongHash = createHashInternal(sb.toString());

        } catch (NoSuchAlgorithmException ex) {

        }
        return strongHash;
    }

    private String createHashInternal(final String input) throws NoSuchAlgorithmException {

        String hashtext = null;
        MessageDigest md = MessageDigest.getInstance(_algorhitm);

        // Compute message digest of the input
        byte[] messageDigest = md.digest(input.getBytes());

        hashtext = convertToHex(messageDigest);

        return hashtext;
    }

    private String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }


}
