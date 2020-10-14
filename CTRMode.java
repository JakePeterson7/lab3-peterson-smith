import java.util.Arrays;

import static java.util.Arrays.copyOfRange;

public class CTRMode {

    static int[] cipher(String key, String text, int[] iv) {
        int[] keyBin = BlockCipher.stringToBinaryArray(key);
        int[] plainBin = BlockCipher.stringToBinaryArray(text);
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
        System.out.println(plainBin.length);
            for (int i = 0; i < l / 35; i++) { //For each block after the first
                System.out.println(counter);
                combo = concatArrays(IV, stringToBinaryArrayWithoutPadding(Integer.toBinaryString(counter)));
//                System.out.println("Combo: ");
//                BlockCipher.printArray(combo);
                temp = BlockCipher.Encrypt(combo, keyBin);
                System.out.println("temp: ");
                BlockCipher.printArray(temp);
                tempBin = copyOfRange(plainBin, (i * 35), (i * 35) + 35);
                System.out.println("tempBin: ");
                BlockCipher.printArray(tempBin);
                int [] added = BlockCipher.addBinaryArrays(temp, tempBin);
                System.out.println("Added: ");
                BlockCipher.printArray(added);
                System.arraycopy(added, 0, cipherText, (i * 35), 35);
                counter++;
            }
    }

    static int[] stringToBinaryArrayWithoutPadding(String string) {
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

    static int[] concatArrays(int[] arr1, int[] arr2){
        int sum = (arr1.length + arr2.length);
        int[] result = new int[sum];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    public static void main(String[] args) { //Test encrypt and decrypt
        String text = "1234512345";
        String key = "0000000000";
        int[] IV = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        String result = BlockCipher.BinaryArrayToString(CTRMode.cipher(key, text, IV));
        System.out.println("Result: " + result);
        String result2 = BlockCipher.BinaryArrayToString(CTRMode.cipher(key, result, IV));
        System.out.println("Result: " + result2);
    }
}
