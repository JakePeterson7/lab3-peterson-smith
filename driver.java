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
            System.out.println("The length of "+i+" is: "+bitArray.length);
            //printStringArray(bitArray);

            //need to add padding if bitArray.length is < 7
            if(bitArray.length<7){
                String[] temp = bitArray;
                bitArray = new String[7];

                for(int p=0; p<bitArray.length; p++){
                    if (p==0) {
                        bitArray[0] = "0";
                    } else {
                        bitArray[p] = temp[p-1];
                    }
                }
            }
            
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
