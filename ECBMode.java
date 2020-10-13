import static java.util.Arrays.copyOfRange;

class ECBMode {
    public static int[] ECB(String key, String text, int crypt) {
        int[] keyBin = BlockCipher.stringToBinaryArray(key);
        int[] plainBin = BlockCipher.stringToBinaryArray(text);
        int[] cipherText = new int[plainBin.length];
        if (crypt == 0) {
            encrypt(keyBin, plainBin, cipherText);
        } else {
            decrypt(keyBin, plainBin, cipherText);
        }
        return cipherText;
    }

    private static void decrypt(int[] keyBin, int[] plainBin, int[] cipherText) {
        //This method processes and decrypts each block of text
        int[] temp;
        int[] tempBin;
        int l = plainBin.length;
        for (int i = 0; i < l / 35; i++) {
            tempBin = copyOfRange(plainBin, (i * 35), ((i * 35) + 35));
            temp = BlockCipher.Decrypt(tempBin, keyBin);
            System.arraycopy(temp, 0, cipherText, (i * 35), temp.length);
        }
        if (l % 35 != 0) { //Handle overflow
            tempBin = copyOfRange(plainBin, (l - l % 35), l);
            temp = BlockCipher.Decrypt(tempBin, keyBin);
            System.arraycopy(temp, 0, cipherText, (l - l % 35), temp.length);
        }
    }

    private static void encrypt(int[] keyBin, int[] plainBin, int[] cipherText) {
        //This method processes and encrypts each block of text
        int[] temp;
        int[] tempBin;
        int l = plainBin.length;
        for (int i = 0; i < l / 35; i++) {
            tempBin = copyOfRange(plainBin, (i * 35), ((i * 35) + 35));
            temp = BlockCipher.Encrypt(tempBin, keyBin);
            System.arraycopy(temp, 0, cipherText, (i * 35), temp.length);
        }
        if (l % 35 != 0) { //Handle overflow
            tempBin = copyOfRange(plainBin, (l - l % 35), l);
            temp = BlockCipher.Encrypt(tempBin, keyBin);
            System.arraycopy(temp, 0, cipherText, (l - l % 35), temp.length);
        }
    }

    public static void main(String[] args) { //Test encrypt and decrypt
        String text = "hellohello";
        String key = "yikesyikes";
        String result = BlockCipher.BinaryArrayToString(ECB(key, text, 0));
        System.out.println(result);
        String result2 = BlockCipher.BinaryArrayToString(ECB(key, result, 1));
        System.out.println(result2);
    }
}
