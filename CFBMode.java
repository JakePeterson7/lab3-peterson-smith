
import java.util.Random;

class CFBMode { //not yet working.

    //public static void main(String[] args) {
    public static int[] CFB(String key, String plain, int crypt) {
        // initialization vector
        int[] IV = generateIV();
        BlockCipher.printArray(IV);


        int[] keyBin = BlockCipher.stringToBinaryArray(key);
        int[] plainBin = BlockCipher.stringToBinaryArray(plain);
        System.out.println("plainBin: ");
        BlockCipher.printArray(plainBin);

        int[] cipherText = new int[plain.length() * 7];// this needs to be fixed
        int cipherTextCounter = 0;// hold the index to add the next bit into cipherText[]

        if(crypt == 0) {
            Encrypt(IV, keyBin, plainBin, cipherText, cipherTextCounter);
        }else{
            Decrypt(IV, plain, keyBin, cipherText);
        }
        return cipherText;
    }// end main

    private static void Decrypt(int[] IV, String plainText, int[] keyBin, int[] cipherText) {
        int[] plainBin;
        plainBin = new int[plainText.length() * 7];
        int plainTextCounter = 0; // this holds the place in the plainText array to add the next bit to.

        int[] plainTextToAdd;
        int [] currentCipherTextBlock;
        int[] previousCipherTextBlock = new int[35];
        for(int i=0; i<plainBin.length / 35; i++){// walk through the array by each block

            currentCipherTextBlock =  getBlockOfCipherText(i, cipherText);
            //BlockCipher.printArray(currentCipherTextBlock);
            if(i == 0) {
                plainTextToAdd = initialDecryptionRun(IV, keyBin, currentCipherTextBlock);
                plainTextCounter = addToPlainBin(plainTextToAdd, plainBin, plainTextCounter);
            } else {
                plainTextToAdd = cfbDecryptionRun(previousCipherTextBlock, keyBin, currentCipherTextBlock);
                plainTextCounter = addToPlainBin(plainTextToAdd, plainBin, plainTextCounter);
            }
            previousCipherTextBlock = currentCipherTextBlock;
        }

        System.out.println("Decrypted Plain Text: This doesn't work...LOL ");
        BlockCipher.printArray(plainBin);
    }

    private static void Encrypt(int[] IV, int[] keyBin, int[] plainBin, int[] cipherText, int cipherTextCounter) {
        int[] cipherTextToAdd;
        int[] previousCipherText = new int[35];
        for (int i = 0; i < plainBin.length / 35; i++) {// walk through the array by block

            if (i == 0) {
                cipherTextToAdd = initialEncryptionRun(IV, keyBin, plainBin);
                cipherTextCounter = addToCipherText(cipherTextToAdd, cipherText, cipherTextCounter);
            } else {
                cipherTextToAdd = cfbEncryptionRun(previousCipherText, keyBin, plainBin);
                cipherTextCounter = addToCipherText(cipherTextToAdd, cipherText, cipherTextCounter);
            }
            previousCipherText = cipherTextToAdd;
        }
        System.out.println("Completed Cipher Text: ");

        BlockCipher.printArray(cipherText);
    }


    private static int[] initialDecryptionRun(int[] IV, int[] keyBin, int[] currentCipherTextBlock) {
        int[] plainText;
        int[] blockDecryption = BlockCipher.Decrypt(IV, keyBin);

        plainText = BlockCipher.addBinaryArrays(currentCipherTextBlock, blockDecryption);

        return plainText;
    }
    private static int[] cfbDecryptionRun(int[] previousCipherText, int[] keyBin, int[] currentCipherTextBlock) {
        int[] plainText;
        plainText = BlockCipher.addBinaryArrays(currentCipherTextBlock, BlockCipher.Decrypt(previousCipherText, keyBin));

        return plainText;
    }

    private static int[] getBlockOfCipherText(int i, int[] cipherText) {
        int[] result = new int[35];
        //k=i*35: k is being set to the begining of the block of cipherText we need to get.
        // k<(35 * (i+1)): I want this to run until k gets to the end of the block.
        // i is the block I want to get ex) if i=0, then 35 * (i+1) = 35 will put us at the end of the first block.
        //                              ex) if i=1, then 35 * (i+1) = 70 (the end of the second block).
        int counter = 0;
        for(int k=i*35; k<(35 * (i+1)); k++){
            result[counter] = cipherText[k];
            counter++;
        }

        return result;
    }

    private static int addToCipherText(int[] cipherTextToAdd, int[] cipherText, int cipherTextCounter) {
        // find index to start adding to cipherText
        for (int bit : cipherTextToAdd) {
            cipherText[cipherTextCounter] = bit;
            cipherTextCounter++;
        }
        return cipherTextCounter;
    }
    private static int addToPlainBin(int[] plainTextToAdd, int[] plainBin, int plainTextCounter) {
        for(int bit : plainTextToAdd){
            plainBin[plainTextCounter] = bit;
            plainTextCounter++;
        }
        return plainTextCounter;
    }


    // generate a random IV of size 35
    private static int[] generateIV() {
        int[] result = new int[35];
        Random RNG = new Random();

        for (int i = 0; i < 35; i++) {
            result[i] = RNG.nextInt(2);
        }

        return result;
    }
    //I've tested this by hand and it gives the correct encryption. I think...
    private static int[] initialEncryptionRun(int[] IV, int[] keyBin, int[] plainBin) {

        int[] cipherText;
        int[] blockEncryption = BlockCipher.Encrypt(IV, keyBin);
        cipherText = BlockCipher.addBinaryArrays(plainBin, blockEncryption);

        return cipherText;
    }

    private static int[] cfbEncryptionRun(int[] keyBin, int[] previousCipherText, int[] thisPlainText) {
        int[] cipherText;
        cipherText = BlockCipher.addBinaryArrays(thisPlainText, BlockCipher.Encrypt(previousCipherText, keyBin));

        return cipherText;
    }

}