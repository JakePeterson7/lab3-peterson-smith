import java.util.Random;

public class CFBMode {

    /**
     * CFB mode encryption:
     * 
     * @param args
     */
    public static void main(String[] args){

    //intitialization vector
    int[] IV = generateIV();

    String keyAlpha = "a5Z#\t";
    String plainText = "Hello";

    int[] keyBin = BlockCipher.stringToBinaryArray(keyAlpha);
    int[] plainBin = BlockCipher.stringToBinaryArray(plainText);

    BlockCipher.printArray(IV);

    int[] cipherText = initialRun(IV, keyBin, plainBin);
    BlockCipher.printArray(cipherText);


    }// end main


    // generate a random IV of size 35
    public static int[] generateIV(){
        int[] result = new int[35];
        Random RNG = new Random();

        for(int i=0; i<35; i++){
            result[i] = RNG.nextInt(2);
        }

        return result;
    }
    
    private static int[] initialRun(int[] IV, int[] keyBin, int[] plainBin ){
        int[] cipherText = new int[35];
        cipherText = BlockCipher.addBinaryArrays(plainBin, BlockCipher.Encrypt(IV, keyBin)); 

        return cipherText;
    }

    private static int[] cfbRun(int[] keyBin, int[] previousCipherText, int[] thisPlainText){
        int[] cipherText = new int[35];
        cipherText = BlockCipher.addBinaryArrays(thisPlainText, BlockCipher.Encrypt(previousCipherText, keyBin));

        return cipherText;
    }

}