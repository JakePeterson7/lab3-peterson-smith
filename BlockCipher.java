public class BlockCipher {

    static int[] Encrypt(int[] text, int[] key) {
        circleRotateThree(text);// this has been checked to work

        return addBinaryArrays(text, key);

    }// end main

    // reverse the encryption
    static int[] Decrypt(int[] encryption, int[] keyBin) {

        int[] result = addBinaryArrays(encryption, keyBin);

        reverseCircleRotate(result);

        return result;// this gives correct result, I checked by hand.
    }

    private static void circleRotateThree(int[] ar) { //Encrypting helper method
        for (int i = 0; i < 3; i++) {
            int last = ar[ar.length - 1];

            System.arraycopy(ar, 0, ar, 1, ar.length - 1);

            ar[0] = last;
        }
    }

    private static void reverseCircleRotate(int[] ar) { //Decrypting helper method
        for (int i = 0; i < 3; i++) {
            int first = ar[0];

            System.arraycopy(ar, 1, ar, 0, ar.length - 1);
            ar[ar.length - 1] = first;
        }
    }

       public static void printIntArray(int[] ar) { //Not being used so commented out
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

    public static int[] addBinaryArrays(int[] plainBin, int[] keyBin) { //Method using XOR method and storing to var.
        int[] result = new int[35];
        for (int i = 0; i < 35; i++) {
            result[i] = addBits(plainBin[i], keyBin[i]);
        }
        return result;
    }

    private static int addBits(int a, int b) { //XOR helper method
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
        int[] keyBin = stringToBinaryArray(keyAlpha);
        int[] plainBin = stringToBinaryArray(plainText);

        System.out.println("plainBin: ");
        printArray(plainBin);

        System.out.println("keyBin: ");
        printArray(keyBin);

        System.out.println("add arrays: ");
        printArray(addBinaryArrays(plainBin, keyBin));

        int[] encryption = Encrypt(plainBin, keyBin);

        System.out.println("encryption: ");
        printArray(encryption);

        int[] decryption = Decrypt(encryption, keyBin);

        System.out.println("decryption: ");
        printArray(decryption);
    }

    /**
     * This method takes a String and an int[]. It turns the string into a binary
     * string that is added into the int[]. It also pads zeros.
     */
    static int[] stringToBinaryArray(String string) {

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
            for (String s : bitArray) {
                result[counter] = Integer.parseInt(s);
                counter++;
            }
        }
        return result;
    }

    public static String binaryArrayConvertToASCII(int[] arr) {
        String result = "";
        char nextChar;
//        System.out.println("Array length: " + arr.length);
        for(int j = 0; j < arr.length/7; j++) {
            String s = "";
            for (int i = 0; i < 7; i++) {
                s = s + arr[((j * 7) + i)];
            }
            nextChar = (char)Integer.parseInt(s.substring(0,7),2);
            result += nextChar;
//            System.out.println(result);
        }
        return result;
    }

    public static int[] binaryStringToBinaryArray(String str) {
        int[] result = new int[str.length()];
        for(int i = 0; i < str.length(); i++){
            result[i] = Integer.parseInt(Character.toString(str.charAt(i)));
        }
        return result;
    }

    public static String binaryArraytoBinaryString(int[] arr) {
        String result = "";
        for(int i = 0; i < arr.length; i++){
            result = result + arr[i];
        }
        return result;
    }
}
