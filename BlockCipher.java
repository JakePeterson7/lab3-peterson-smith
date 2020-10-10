import java.util.Arrays;

public class BlockCipher {

    public static int[] Encrypt(int[] text, int[] key) {

        int[] keyBin = key;
        int[] plainBin = text;

        circleRotateThree(plainBin);// this has been checked to work

        int[] result = addBinaryArrays(plainBin, keyBin); // this has been checked to work
   
        return result;

    }// end main

    // reverse the encryption
    public static int[] Decrypt(int[] encryption, int[] keyBin) {

        int[] result = addBinaryArrays(encryption, keyBin);

        reverseCircleRotate(result);

        return result;// this gives correct result, I check by hand.
    }

    private static void circleRotateThree(int[] ar) {
        for (int i = 0; i < 3; i++) {
            int last = ar[ar.length - 1];

            for (int k = ar.length - 1; k > 0; k--) {
                ar[k] = ar[k - 1];
            }

            ar[0] = last;
        }
    }

    private static void reverseCircleRotate(int[] ar) {
        for (int i = 0; i < 3; i++) {
            int first = ar[0];

            for (int k = 0; k < ar.length - 1; k++) {
                ar[k] = ar[k + 1];
            }
            ar[ar.length - 1] = first;
        }
    }

    /**
     * This method takes a String and an int[]. It turns the string into a binary
     * string that is added into the int[]
     */
    public static int[] stringToBinaryArray(String string) {

        int[] result = new int[string.length() * 7];

        int counter = 0; // used to keep track of position in the key array.
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i); // get a char from the String.
            String binString = Integer.toBinaryString(c); // bin holds the binary value of the first char as a string.
            String[] bitArray = binString.split(""); // split binString into a string array.
            // System.out.println("The length of " + i + " is: " + bitArray.length);
            // printStringArray(bitArray);

            // need to add padding if bitArray.length is < 7
            if (bitArray.length < 7) {
                String[] temp = bitArray; // temp holds original values
                bitArray = new String[7]; // bitArray is made to be bigger (from 6 to 7)
                for (int j = 0; j < (bitArray.length - temp.length); j++) {
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
                result[counter] = Integer.parseInt(bitArray[k]);
                counter++;
            }
        }
        return result;
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

    public static int[] addBinaryArrays(int[] plainBin, int[] keyBin) {

        int[] result = new int[35];
        for (int i = 0; i < 35; i++) {
            result[i] = addBits(plainBin[i], keyBin[i]);
        }
        return result;
    }

    public static int addBits(int a, int b) {
        if (a == b) {
            return 0;
        } else
            return 1;
    }

    public static void printArray(int[] key) {
        for (int b : key) {
            System.out.print(b + " ");
        }
        System.out.println();
    }

    // Testing encryption and decryption.
    public static void main(String[] args) {
        String keyAlpha = "a5Z#xa5z#x";
        String plainText = "HelloHello";
        int[] keyBin = BlockCipher.stringToBinaryArray(keyAlpha);
        int[] plainBin = BlockCipher.stringToBinaryArray(plainText);

        System.out.println("plainBin: ");
        BlockCipher.printArray(plainBin);

        System.out.println("keyBin: ");
        BlockCipher.printArray(keyBin);

        System.out.println("add arrays: ");
        BlockCipher.printArray(addBinaryArrays(plainBin, keyBin));

        int[] encryption = Encrypt(plainBin, keyBin);

        System.out.println("encryption: ");
        BlockCipher.printArray(encryption);

        int[] decryption = Decrypt(encryption, keyBin);

        System.out.println("decryption: ");
        BlockCipher.printArray(decryption);
    }

}
