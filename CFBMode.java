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

    BlockCipher.printArray(IV);

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

}