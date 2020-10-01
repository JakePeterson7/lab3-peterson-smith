public class driver {

    public static void main(String[] args) {
        String keyAlpha = "abcde";
        String plainText = "Hello";

        int[] keyBin = new int[35];
        int[] plainBin = new int[35];

        stringToBinaryArray(keyAlpha, keyBin);
        stringToBinaryArray(plainText, plainBin);

        printIntArray(keyBin);
        printIntArray(plainBin);

    }
    /** This method takes a String and an int[].
     *  It turns the string into a binary string that is added into the int[] */
    private static void stringToBinaryArray(String string, int[] key) {

        int counter=0; //used to keep track of position in the key array.
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);            // get a char from the String.
            String binString = Integer.toBinaryString(c); // bin holds the binary value of the first char as a string.
            String[] bitArray = binString.split("");      // split binString into a string array.
            //printStringArray(bitArray);
            
            // add values to the array.
            for(int k=0; k<bitArray.length; k++){
                key[counter] = Integer.parseInt(bitArray[k]);
                counter++;
            }
        }
    }

    private static void printIntArray(int[] ar) {
        for (int b : ar) {
            System.out.print(Integer.toBinaryString(b) + "");
        }
        System.out.println();
    }

    private static void printStringArray(String[] key) {
        for (String b : key) {
            System.out.print(b + " ");
        }
        System.out.println();
    }
}
