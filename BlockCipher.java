import java.util.Arrays;

public class BlockCipher {

    public static void main(String[] args) {
        String keyAlpha = "a5Z#\t";
        String plainText = "Hello";

        int[] keyBin = new int[35];
        int[] plainBin = new int[35];

        stringToBinaryArray(keyAlpha, keyBin);
        stringToBinaryArray(plainText, plainBin);

        // perform circular shift of three bits on the plaintext (plainBin).

        // test rotation
        // int[] test = new int[] { 1, 2, 3, 4, 5, 6, 7 };
        // printArray(test);
        // circleRotateThree(test);
        // printArray(test);

        // printIntArray(plainBin);
        circleRotateThree(plainBin);
        // add keyBin to plainBin (bitwise)
        int[] result = addKey(plainBin, keyBin);
        printIntArray(result);

    }// end main

    private static void circleRotateThree(int[] ar) {
        for (int i = 0; i < 3; i++) {
            int last = ar[ar.length - 1];

            for (int k = ar.length - 1; k > 0; k--) {
                ar[k] = ar[k - 1];
            }

            ar[0] = last;
        }
    }

    /**
     * This method takes a String and an int[]. It turns the string into a binary
     * string that is added into the int[]
     */
    private static void stringToBinaryArray(String string, int[] key) {

        int counter = 0; // used to keep track of position in the key array.
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i); // get a char from the String.
            String binString = Integer.toBinaryString(c); // bin holds the binary value of the first char as a string.
            String[] bitArray = binString.split(""); // split binString into a string array.
            System.out.println("The length of " + i + " is: " + bitArray.length);
            // printStringArray(bitArray);

            // need to add padding if bitArray.length is < 7
            if (bitArray.length < 7) {
                String[] temp = bitArray; // temp holds original values
                bitArray = new String[7]; // bitArray is made to be bigger (from 6 to 7)
                for (int j = 0; j < (bitArray.length - temp.length);j++){
                    bitArray[j] = "0";
                }
                int n = 0;
                for (int p = (bitArray.length - temp.length); p < bitArray.length; p++) {
                    bitArray[p] = temp[n];
                    n++;
                }
            }

            // add values to the array.
            for (int k = 0; k < bitArray.length; k++) {
                key[counter] = Integer.parseInt(bitArray[k]);
                counter++;
            }
        }
    }

    private static void printIntArray(int[] ar) {
        int counter = 1;
        for (int b : ar) {
            System.out.print(Integer.toBinaryString(b));
            if (counter % 7 == 0) {
                System.out.print(" ");
            }
            counter++;
        }
        System.out.println();
    }

    public static int[] addKey(int[] plainBin, int[] keyBin){
        int[] result = new int[35];
        for(int i = 0; i<35; i++){
            result[i] = addBits(plainBin[i], keyBin[i]);
        }
        return result;
    }

    public static int addBits(int a, int b){
        if(a == b){
            return 0;
        }else
            return 1;
    }

    public static void printArray(int[] key) {
        for (int b : key) {
            System.out.print(b + " ");
        }
        System.out.println();
    }
}
