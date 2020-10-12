import static java.util.Arrays.copyOfRange;

class CBCMode {
    public int[] cipher(String key, String text, int crypt, String iv) {
        int[] keyBin = BlockCipher.stringToBinaryArray(key);
        int[] plainBin = BlockCipher.stringToBinaryArray(text);
        int[] cipherText = new int[plainBin.length];
        int[] IV = BlockCipher.stringToBinaryArray(iv);
        System.out.println("IV: " + BlockCipher.BinaryArrayToString(IV));
        if (crypt == 0) {
            encrypt(keyBin, plainBin, IV, cipherText);
        } else {
            decrypt(keyBin, plainBin, IV, cipherText);
        }
        return cipherText;
    }

    public static void encrypt(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp;
        int[] tempBin;
        int l = plainBin.length;
        for (int i = 0; i < l / 35; i++) {
            tempBin = copyOfRange(plainBin, (i * 35), ((i * 35) + 35));
            int[] newInput = BlockCipher.addBinaryArrays(tempBin, IV);
            temp = BlockCipher.Encrypt(newInput, keyBin);
            System.arraycopy(temp, 0, cipherText, (i * 35), temp.length);
        }
        if (l % 35 != 0) {
            tempBin = copyOfRange(plainBin, (l - l % 35), l);
            int[] newInput = BlockCipher.addBinaryArrays(tempBin, IV);
            temp = BlockCipher.Encrypt(newInput, keyBin);
            System.arraycopy(temp, 0, cipherText, (l - l % 35), temp.length);
        }
    }

    public static void decrypt(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp;
        int[] tempBin;
        int l = plainBin.length;
        for (int i = 0; i < l / 35; i++) {
            tempBin = copyOfRange(plainBin, (i * 35), ((i * 35) + 35));
            temp = BlockCipher.Decrypt(tempBin, keyBin);
            int[] newInput = BlockCipher.addBinaryArrays(temp, IV);
            System.arraycopy(newInput, 0, cipherText, (i * 35), temp.length);
        }
        if (l % 35 != 0) {
            tempBin = copyOfRange(plainBin, (l - l % 35), l);
            temp = BlockCipher.Decrypt(tempBin, keyBin);
            int[] newInput = BlockCipher.addBinaryArrays(temp, IV);
            System.arraycopy(newInput, 0, cipherText, (l - l % 35), temp.length);
        }
    }

    public static void main(String[] args) { //Test encrypt and decrypt
        String text = "yikesyikesyik";
        String key = "hellohellohel";
        String IV = "arere";
        CBCMode CBC = new CBCMode();
        String result = BlockCipher.BinaryArrayToString(CBC.cipher(key, text, 0, IV));
        System.out.println("Result: " + result);
        String result2 = BlockCipher.BinaryArrayToString(CBC.cipher(key, result, 1, IV));
        System.out.println("Result: " + result2);
    }
}
