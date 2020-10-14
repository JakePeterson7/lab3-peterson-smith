import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

class OFBMode {
    private static int[] text =new int[35];

    public static int[] cipher(int[] key, String text, int[] IV) {
        int[] keyBin = key;
        int[] plainBin = BlockCipher.stringToBinaryArray(text);
        int[] cipherText = new int[plainBin.length];
//      System.out.println("IV: " + BlockCipher.BinaryArrayToString(IV));
        encrypt(keyBin, plainBin, IV, cipherText);
        return cipherText;
    }

    public static void encrypt(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp; //Holds encrypted block
        int[] tempBin; //Holds plaintext to be encrypted
        int l = plainBin.length; //Readability
        initialEncryption(keyBin, plainBin, IV, cipherText); //Initial run of encryption
        for (int i = 1; i < l / 35; i++) { //For each block after the first
            temp = BlockCipher.Encrypt(text, keyBin); //Encrypt result of last encryption with key
            System.arraycopy(temp, 0, text, 0, 35); //Overwrite text with result for next block
            tempBin = copyOfRange(plainBin, (i * 35), ((i * 35) + 35)); //Copy plaintext block into tempBin
            //XOR plaintext block and encryption result, then copy result to resulting ciphertext
            System.arraycopy((BlockCipher.addBinaryArrays(tempBin,temp)), 0, cipherText, (i * 35), 35);
        }
//        if (l % 35 != 0) { //Handles overflow
//            tempBin = copyOfRange(plainBin, (l - l % 35), l);
//            int[] newInput = BlockCipher.addBinaryArrays(tempBin, text);
//            temp = BlockCipher.Encrypt(newInput, keyBin);
//            System.arraycopy(temp, 0, cipherText, (l - l % 35), temp.length);
//        }
    }

    public static void initialEncryption(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp; //Holds encrypted block
        int[] tempBin; //Holds plaintext block
        temp = BlockCipher.Encrypt(IV,keyBin); //Store result of encryption
        System.arraycopy(temp,0, text,0,35); //Copy result for next block
        tempBin = copyOfRange(plainBin, 0, 35); //Copy plaintext block
        //XOR plaintext block and encryption result, then copy result to resulting ciphertext
        System.arraycopy((BlockCipher.addBinaryArrays(tempBin,temp)),0,cipherText,0,35);
    }

    public static void main(String[] args) { //Test encrypt and decrypt
        String text = "yikesyikesyikes";
        int[] key = BlockCipher.stringToBinaryArray("hellohellohello");
        int[] IV = new int[]{};
        String result = BlockCipher.BinaryArrayToString(OFBMode.cipher(key, text, IV));
        System.out.println("Result: " + result);
        String result2 = BlockCipher.BinaryArrayToString(OFBMode.cipher(key, result, IV));
        System.out.println("Result: " + result2);
    }
}
