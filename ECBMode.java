import static java.util.Arrays.copyOfRange;

class ECBMode {
    public static int[] ECB(int[] key, String text, int crypt) {
        int[] keyBin = key;
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
//        if (l % 35 != 0) { //Handle overflow
//            tempBin = copyOfRange(plainBin, (l - l % 35), l);
//            temp = BlockCipher.Decrypt(tempBin, keyBin);
//            System.arraycopy(temp, 0, cipherText, (l - l % 35), temp.length);
//        }
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
//        if (l % 35 != 0) { //Handle overflow
//            tempBin = copyOfRange(plainBin, (l - l % 35), l);
//            temp = BlockCipher.Encrypt(tempBin, keyBin);
//            System.arraycopy(temp, 0, cipherText, (l - l % 35), temp.length);
//        }
    }

    public static void main(String[] args) { //Test encrypt and decrypt
        String binary = "001110111011101010001000100001100001100011101111010110001101001000100111010010011110101100010010010011001010100010011101011011101000110100000011010100111110010011010001101001100110111001101001000100010010100000100101001111101001001001000110100011111011000011101010111001000101000011110011100111010101111110011000010100001110110000111000111101100000110010011101111000010001111001111100110010011100111010100100100011010000110101001011100011001101000000110011001000100111010110111010001010001100110110000111010100101010111110000001101011011111110001000111010000000011001000110100000010001000001000011110010111111111000100010000010000110101101111111110001111101111010011111111001101010111010010001101010001010010010111101101101100011010000111101010011100011001101000110100100111010010110111100011111011111000111100111011111011100010010110100001010100010011010010001000101111000111111001000111100110000110001101000010110100110111010101101110010000001001101001011101001001100000110100010011011000111101110110100001101011111110101011111111100011000011010000101100100101110101000000100110000111001010011011110110111000001101000111110110001111111100011000011010010010101001011011111011010000110100011111011100110001100001000001101001000110100101101111101101000011010011110101100011111111000011000000110011101000100111010110111011011111100111010100001110101100011010110010000011001110110101011011010000110100011111001011111111110001100001101001011110100101101111101101000011010010110101100011110111011010000000110011111011111110111110111000001101010010000110101110110100010000011010001011101100001110101011100100010100010011001111111111100010001001011000001110011110111010101000110000100011011001110101100101111111100010100010111010011011101010111001000101000100000111011101000100100110001000000100001110111010011011100100110000110011001001110100100010001011001000010000111011101010100011110110000011010001010001110101100001000101000001101010001011101010010101001000100101110010011110010100110110111110001110001110111111001000111101111110011101000100111010110111010001101100011011100011110011100001110010001001110111010001111011011110100100010011101011111111111110011011000011100101110010011110010100110110111110001001001110111110001001101100101100001111001111111111100000010001101010010000110101110110100010000011010001011101100001110101011100100010100010011001111111111100010001001011000001110011110111010101000110000100011011001110101100101101011100110000110011001110111111100010001010100000011010010000111101101101010010001001011100100111100101001101101111100011100011101111110010001111011111100100110100001111111000100010110010000001101001001100110001000111010000001101001000011110110110101001000100101010011101101110100000110000010000100001110111100111000110000110100010101011000111111000010001001100001111000111011101110100110110000111001001111110011101001001100000110100111001010010110100010000110001101000111010110001111101100100110000111001111101110011011100001000100100000001111011010111001010111000001101011001110100101101111000111110110110001000001110111110001011101101111100000011011000111111000010001010100000010010010001101000000100010011110011111101100011110111000011100101100010011001110110111000010001111110000001000111011111100100100110111111000000001110111011101000000100010000001001011010110100100010001001100001111110110000111010100111110000100000100101001111111010001000100101000111111011000111101110111000001101011001110100101101111101101000011010000100101001011011101000000100100000110000011101110110110010011011111000000110110001111110000100010101000000011100100001110101011100100010100011101001111111111100011111001001100111110111111111111000100010010010001111100010011010010001000100000100001101010110111111100010001011001000010010110001101001100110100011010101110100100011011100001000101111000000101011000011101010011001000100000101010100110111101101110000011010011111101100011101101000100000110100101101010010110111100010001011010000011010110001110100000100010110010000100101100001110101011111100001000111111010001111111000010001000101000100110011100111010100101010111110000110100100011111011000000101100110010101100100111010110111010001101000101010100101101101100011000011010011011101001001110101010001100001000110111001111011101010000111000011000111010110001101001000100010110010000001101110011011100001000110010010010111010001111011000010001001111000110000111011101110100000010010000011100101100001110101001110100001000111100011101110001110001111011000100100000101011001101100000010010000000110001110111011101000000100100010011101001111111101110000011000011100101010011101101110100100110111110011110100111001110101011100100001010011111001101111010110111011011111100001000111011010011000111110000010000010001110111110001001101000110100111101010101111111110110101001000100111110110111110101101101000011010010010101001011011110001000000010101001111010100111000110011010001101010010000110101101100100110100011010001010101100011111111000110000110100111101010101111111110110101001000100110010111111111111100111010000100011001101100001100110001000110010010010111010001111011000010001001111000110000111011101110100000010010000011100101100001110101001110100001000111100011101110001110001111011000100110100101011011001100100110110000011011101100001110101010001100001000110100011010110100010011010001101001111010101011111111101101010010001001011101101111101011011010000110100000011011000011101010111001000010000011000101000111010110011010000101001101101001011101001001101000110100011101010010110111110001001001001100111010110001101100100110110111111001110101011011101001000111101101010010011010000111111100010001100011000011000111011110101100011010010001001010101111011101011011101100000100011000011101111111110111101001000001111000111011101001100110110010001001011101000011111110001000111111000011111011000111011110110110001101001001110100101101111000111110111110001110001110111011101000000100100010011101010010111010010001001010100100100110100101110100100110100011010011000101010111101010001000100011000000011010010110110110110110001101011111110101011111011101110000011010000010100100101111011011011000110100000101010100111000110011010001101001111010100100111010100101010010001001001101000011111110001000111111000000111010000111111110001111000011000100000111011101110100011100011010001010101110011110011001101000110100111111010101111111100010001001001000101010001000111010101111110001000011101101010101110101100101100110000001101011000011101010000011011001100101110010011110010101101010000100011010101101011100100001000100110000000111010001011101010111001000101000101000111011101000100110110111100011110001110111011011000001101100100000110011101100100110001111000000000111110100101110100100110100011010100111001101011101101000100000110100011011010001110110110010011010100001100100111011110110100100110110000001010101010101110101011111100010000110111010101011101011011011011011000111010100110111010100100110110011001001101000011000110001000101101000001101001111110100110111000000001100110011101000111101100010100011010001001101100011010011011100000110100111111010100110111010000000001101001011110100101101111000010000011010010111101100011110111000000000110101000101001001011110110110110001101000011110101001110001100110100011010010011101001011011110001111101111100011110011101111101110001001011010000101010001001101001000100010111100011111100100011110011000011000110100001011010011011101010110111001000000100110100101110100100110000011010001001101100011110111011010000110101111111010101111111110001100001101000010110010010111010100000010011000011100101001101111011011100000110100011111011000111111110001100001101001001010100101101111101101000011010001111101110011000110000100000110100100011010010110111110110100001101001111010110001111111100001100000011001110100010011101011011101101111110011101010000111010110001101011001000001100111011010101101101000011010001111100101111111111000110000110100101111010010110111110110100001101001011110110001111011101101000011010100100001101011101101000100000110100010111011000011101010111001000101000100110011111111111000100010010110000011100111101110101010001100001000110110011101011001011111111000101000101110100110111010101110010001010001000001110111010001001001100010000001000011101110100110111001001100001100110010011101001000100010110010000100001110111010101000111101100000110100010100011101011000010001010000011010100010111010100101010010001001011100100111100101001101101111100011100011101111110010001111011111100111010001001110101101110100011011000110111000111100111000011100100010011101110100011110110111101001000100111010111111111111100110110000111001011100100111100101001101101111100010010011101111100010011011001011000011110011111111111000000100011010100100001101011101101000100000110100010111011000011101010111001000101000100110011111111111000100010010110000011100111101110101010001100001000110110011101011001011010111001100001100110011101111111000100010101000000110100100001111011011010100100010010111001001111001010011011011111000111000111011111100100011110111111001001101000011111110001000101100100001101010010011001101000100101101110";
        int[] arr2 = BlockCipher.readBinaryString(binary);
        String text = BlockCipher.BinaryArrayToString(arr2);
        int[] key = new int[]{1,1,0,0,1,1,1,1,0,1,0,0,1,1,0,1,0,0,1,0,1,0,0,0,0,1,0,1,1,0,1,1,1,0,1};
        //        String result = BlockCipher.BinaryArrayToString(ECB(key, text, 0));
        //        System.out.println(result);
        String result2 = BlockCipher.BinaryArrayToString(ECB(key, text, 1));
        System.out.println(result2);
    }
}
