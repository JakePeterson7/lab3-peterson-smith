import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

public class CTRMode {

    static int[] cipher(int[] key, String text, int crypt, int[] iv) {
        int[] keyBin = key;
        int[] plainBin;
        if(crypt == 0) {
            plainBin = BlockCipher.stringToBinaryArray(text);
        }else{
            plainBin = BlockCipher.readBinaryString(text);
        }
        int[] cipherText = new int[plainBin.length];
        int[] IV = new int[19];
        System.arraycopy(iv, 0, IV, 0, 19);
        encrypt(keyBin, plainBin, IV, cipherText);
        return cipherText;
    }

    private static void encrypt(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp; //Holds encrypted block
        int[] tempBin; //Holds plaintext to be encrypted
        int[] combo; //Holds concatenated IV and counter
        int l = plainBin.length; //Readability
        int counter = 0;
            for (int i = 0; i < l / 35; i++) { //For each block after the first
                combo = concatArrays(IV, stringToBinaryArrayWithoutPadding(Integer.toBinaryString(counter)));
                temp = BlockCipher.Encrypt(combo, keyBin);
                tempBin = copyOfRange(plainBin, (i * 35), (i * 35) + 35);
                int [] added = BlockCipher.addBinaryArrays(temp, tempBin);
                System.arraycopy(added, 0, cipherText, (i * 35), 35);
                counter++;
            }
    }

    private static int[] stringToBinaryArrayWithoutPadding(String string) {
        int[] result = new int[string.length()];
        int num = 0;
        for(int i = 0; i < string.length(); i++){
            result[i] = Integer.parseInt(Character.toString(string.charAt(i)));
        }
        if(result.length < 16){
            int[] newarr = new int[16];
            for(int i = 0; i < (16 - result.length);i++){
                newarr[i] = 0;
                num++;
            }
            if (num >= 0) System.arraycopy(result, 0, newarr, 16 - result.length, result.length);
            return newarr;
        }
        return result;
    }

    private static int[] concatArrays(int[] arr1, int[] arr2){
        int sum = (arr1.length + arr2.length);
        int[] result = new int[sum];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    public static void main(String[] args) { //Test encrypt and decrypt
        String text = "01110010011101010001101010110110010101101000111011101101010000011111010111110001110111011110100001111110101000110011101111011011001010110001100001100101001100110110010101010101011011000000101000110100010011001011001000011100110001011001010111111111111110100101110111010110101101000011001101001011101000100100010010110110100101011110110001100000101110110101000110001101100010011111110110101001101001011000101100101011011010001010011001110011011010011111100100001100111011110110110001001011101101111101001011000010110111011001111100010010111010001100111000110011001111100000000100011010100101100000001100001011101000110101001011011001011000010111010010001101101111111001111001111011000010110010100101101011110101010111100001100101011001110100110011001110011011001010100110110010100001110100100011011101110100111010001001111011110110010011111000100110000001010001101101100101010000111100100011101000110010101101110100100101001011100000100000011110101101010011100110011111001010111001100111100100111110001110010100010011010010001011111011011100101011011111111101010010110101101001000101101111010010101011110111110010101111000001111001011111011011100101010000000101010000111111000111001010111101010101100010110101111110010101000000110101000011111100011100101010111010011110010111110100011010011111001100001100111011110110011011111110011100111001110111011010110001011000101110100111101111000111001010111100001110000101111101101110010101000010010101000011111100011100101010000101011110010011111000111010011111000011100000001111110111110010100100010001101001111011011111100101011101010111111010010111011101011010111101111100000101110100011010001101101111101000101001011110100101010010110110010101011010101010001011010111110001010010100101110111010110101111101101010000000011000100101100011010111110100000001111011101001000101001101111000000010100011011000101101000011010000001011011000110000010100101000111010010100101011000100110111111011010100101100001010000011110110011110001101011001100110111011111110110100010100110110001101111111010011001100111011110110110010111100100010110001101111010000100000111101011000111010101111010101000001111010101100000101111100110010101111110101111110001101011001100110111011111101110100010000110011001000110110101001100000101110101010110010100111001011101001111011011111100101011011101011111010010111011101011010110000010100000101110100011010001101110101011000101001011110100101010010000000110101011010100000001001000100000111100100101110100011011110001110101101111101010111100010100000111010100111100011010110011001101110110001101101000101001101100011011111101000100111001011101000110110001011101101101011011011010001100111000111011101111110101011110001010000011101001001001000011011010100101001011100001010100011011110110111001010110110111111100101100100011010010001000001001010000011011001100101011111011101110110011100110011111001010100000010010100000001101000110010101101010111111001010111000100101011111011110010110000110010001101000000111110101110100110101110111110010100011101100101000011101001000110011011001001110100010000111011001101110111001000111100101100100011011110001110010001111000000001000110100111010010001011110010011110100001000110110111010100100001100100011001011001110010001010001010011000100110111111101000110100000110110101101011000111000110110100000010100011010010001001001111111101001011001000101010011111100001000010101010001101000100100011001111100100111110111110010100010000100101000011111100011100101011110001101010000110110101101000000100011000101000001011100010011011101111111011111001011001000110111100011111011111111010010111011101011010110100110100000101110100100010000111100010001000001011101000110100001011000100000110000001010001101010010111001010000100101110100011011000101110000111111000000111001101100101001001010111110000110010001101101100111000101111110100101100111010110001101001100100101010111101111100101010010011111110011110110111111001010101001000010100001110100011011001001110110011111001110001000110110001011101101011111010010111010001010100100001011011000101100100011010110101100101011001101001011000010110111011110010011110010101110101101100011110011101001100111011110110110010101101111110101000010111011011100101010011100001011010010111101001010100100111000100010100101101000010101101101110101111000000101000110110001011101001110110000110010001100011001110101100011000111100100011010011101101110011010000011011010110100000010000011011111010010110010101010100100000011101000010111101100011000011100101000010010010111010110100000010001110110100000110110101101000001101101011100000100111101010010101111011010101001000011011000100110111011100110110011010010111011101010101101101100101000111011110110011011111001000101010011101101001000101001010000101110100010000110011011001010111001000001000101111110001010000011011000011100001110111100011100101011010101001010000111010001111011010000000011001100110011100110110010100001010001101000010011001101100101010111010111110010111110110111001010111000110001000101111110001010000011011001111000001011111001010101010011111000011111011011010001100011001101000001011000111100100011010011101010101101010000011011010110100000011110101111111010010110010101010100111101000101000010111101100011000011011110010010011000111000100110000011110011011111010010111101001010100111010011110100000001100110110010101110110000010001011111100010100000110111000111000011101111000111001010100011010111110100101110111010101001000011110011101001010011010101010110111010001010011100110011011001010101101101010100001111101111010000111011101101111000010011001100110111110111011100010011100110110001100101101110000011100101110100011000101101001101011010001110111011010100000110100101010010011101111011001010010101101111101000101111110101010000001110110001010000000111001100100000110100110101110010111010001100101100101100110111100111111110000110010101100001110101000011101001000110111010110010010100000110110101101000001101000001010100111001101111011110011010000100101001110011001101100101011111101100100011101010001101011100100110100010100001011110000110010100111111010010001001111101111100101010000111111101010101111000011001010100100111001100110011100110110010101111000000101101001011000010100000110101100101110010111010001100101100100100010011100101111101100010101101011101111010000011011011001100101010110110010110010001110011011001010111111101001000101110100011010000101010101110011101001011101000101100011111111100101010010100111001001000100000000111110100101101010011000111010100001110001101011100010101010011111101011010011010010001101001000100110011011010100101110111010110101011110001010000000011001100110110011111000110100000101110101001100010100010010101001000011101100010100001011111110101001110011001101100101000001110011010011110110111111001010100100001011000001011100010011110001101101101010001111011000100110000010001001100101010010111010001011000110001100001010100101000110011001101000001010100101001011000100110001110010111001110001011010001101100100101010110100000000011110111110100111001010100011101010111011101100101010011000111110000001111011111001010110011010001100001101101011110010101111010110101000011101011111100101011110110011110010011110101111001010001101100111010101011110000110010101010001100011001100111001101100101011110111101010000111010010001101110100001100101000100111101111011001001001100000011000000101000110110110011001101111110010001110100011001010101110011010010100101110000010000001001001000101001110011001111100101010000010111110010011111000111001010010000110001000101111101101110010101011101101110101001011010110100100011010000001001010101111011111001010100100001111100101111101101110010101111110100101000011111100011100101010001001110110001011010111111001010111111100010100001111110001110010101100011011111001011111010001101001110011011100110011101111011001101111100110111011100111011101101011000101011101001010011110111100011100101010010000011000010111110110111001010111000010010100001111110001110010101110000111111001001111100011100101000100000011010011110110111111001010100011101111110100101110111010110101001001111000001011101000110100011010111011010001010010111101001010100110100100101010110101010100010110101001010010100101001011101110101101010010111010100000000110001001011000101110111101000000011110111010010001100101011110000000101000110110001010111100110100000010110110001100000110010010001110100101001010110001001011001110110101001011000010100000110000000111100011010110011001101110100110101101000101001101100011011111000110110011001110111101101100101110101100101100011011110100001000001100011110001110101011110101010000011000111011000001011111001100101011110001111111100011010110011001101110100110011101000100001100110010001101011000011000001011101010101100101000101110111010011110110111111001010100000010111110100101110111010110101001110101000001011101000110100011010110110110001010010111101001010100110111001101010110101000000010010001101111111001001011101000110111100010110111011111010101111000101000001100010001111000110101100110011011100110001011010001010011011000110111101110101001110010111010001101100010010010000100011110100101111";
        String k = "11001111010011010010100001011011101";
        int[] key = BlockCipher.readBinaryString(k);
        int[] IV = new int[]{1,1,0,1,0,1,0,0,0,0,1,0,1,0,0,1,1,0,1};
//        String result = BlockCipher.BinaryArrayToString(CTRMode.cipher(key, text, IV));
//        System.out.println("Result: " + result);
        String result2 = BlockCipher.BinaryArrayToString(CTRMode.cipher(key, text, 1, IV));
        System.out.println("Result: " + result2);
    }
}
