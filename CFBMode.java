import java.util.Random;

public class CFBMode {

    /**
     * CFB mode encryption:
     * 
     * @param args
     */
    public static void main(String[] args) {

        // intitialization vector
        int[] IV = generateIV();

        String keyAlpha = "a5Z#\t";
        String plainText = "HelloHello";

        int[] keyBin = BlockCipher.stringToBinaryArray(keyAlpha);
        int[] plainBin = extendedStringToBinaryArray(plainText);
        System.out.println("plainBin: ");
        BlockCipher.printArray(plainBin);

        int[] cipherText = new int[plainText.length() * 7];
        int cipherTextCounter = 0;// hold the index to add the next bit into cipherText[]

        int[] cipherTextToAdd = new int[35];
        int[] previousCipherText = new int[35];
        for (int i = 0; i < plainBin.length / 35; i++) {

            if (i == 0) {

                cipherTextToAdd = initialRun(IV, keyBin, plainBin);
                cipherTextCounter = addToCipherText(cipherTextToAdd, cipherText, cipherTextCounter);
            } else {

                cipherTextToAdd = cfbRun(previousCipherText, keyBin, plainBin);
                cipherTextCounter = addToCipherText(cipherTextToAdd, cipherText, cipherTextCounter);
            }
            previousCipherText = cipherTextToAdd;
        }
        System.out.println("Completed Cipher Text: ");

        BlockCipher.printArray(cipherText);

    }// end main

    private static int addToCipherText(int[] cipherTextToAdd, int[] cipherText, int cipherTextCounter) {
        // find index to start adding to cipherText
        for (int bit : cipherTextToAdd) {
            cipherText[cipherTextCounter] = bit;
            cipherTextCounter++;
        }
        return cipherTextCounter;
    }

    // generate a random IV of size 35
    public static int[] generateIV() {
        int[] result = new int[35];
        Random RNG = new Random();

        for (int i = 0; i < 35; i++) {
            result[i] = RNG.nextInt(2);
        }

        return result;
    }

    private static int[] initialRun(int[] IV, int[] keyBin, int[] plainBin) {
        int[] cipherText = new int[35];
        cipherText = BlockCipher.addBinaryArrays(plainBin, BlockCipher.Encrypt(IV, keyBin));

        return cipherText;
    }

    private static int[] cfbRun(int[] keyBin, int[] previousCipherText, int[] thisPlainText) {
        int[] cipherText = new int[35];
        cipherText = BlockCipher.addBinaryArrays(thisPlainText, BlockCipher.Encrypt(previousCipherText, keyBin));

        return cipherText;
    }

    public static int[] extendedStringToBinaryArray(String string) {

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

}