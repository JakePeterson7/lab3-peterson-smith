import static java.util.Arrays.copyOfRange;

class OFBMode {
    private static int[] text =new int[35];

    static int[] cipher(int[] key, String text, int crypt, int[] IV) {
        int[] plainBin;
        if(crypt == 0) {
            plainBin = BlockCipher.stringToBinaryArray(text);
        }else{
            plainBin = BlockCipher.binaryStringToBinaryArray(text);
        }
        int[] cipherText = new int[plainBin.length];
        encrypt(key, plainBin, IV, cipherText);
        return cipherText;
    }

    private static void encrypt(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp; //Holds encrypted block
        int[] tempBin; //Holds plaintext to be encrypted
        int l = plainBin.length; //Readability
        initialEncryption(keyBin, plainBin, IV, cipherText); //Initial run of encryption
        for (int i = 1; i < l / 35; i++) { //For each block after the first
            temp = BlockCipher.Encrypt(text, keyBin); //Encrypt result of last encryption with key
            System.arraycopy(temp, 0, text, 0, 35); //Overwrite text with result for next block
            tempBin = copyOfRange(plainBin, (i * 35), ((i * 35) + 35)); //Copy plaintext block into tempBin
            //XOR plaintext block and encryption result, then copy result to resulting ciphertext
            System.arraycopy((BlockCipher.addBinaryArrays(tempBin,temp)), 0, cipherText, (i * 35), 35);
        }
//        if (l % 35 != 0) { //Handles overflow
//            tempBin = copyOfRange(plainBin, (l - l % 35), l);
//            int[] newInput = BlockCipher.addBinaryArrays(tempBin, text);
//            temp = BlockCipher.Encrypt(newInput, keyBin);
//            System.arraycopy(temp, 0, cipherText, (l - l % 35), temp.length);
//        }
    }

    private static void initialEncryption(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp; //Holds encrypted block
        int[] tempBin; //Holds plaintext block
        temp = BlockCipher.Encrypt(IV,keyBin); //Store result of encryption
        System.arraycopy(temp,0, text,0,35); //Copy result for next block
        tempBin = copyOfRange(plainBin, 0, 35); //Copy plaintext block
        //XOR plaintext block and encryption result, then copy result to resulting ciphertext
        System.arraycopy((BlockCipher.addBinaryArrays(tempBin,temp)),0,cipherText,0,35);
    }
    //Current test doesn't work because I think the group that I got ciphertext from didn't have a proper OFB mode. Otherwise works fine for manual encrypt then decrypt.
    public static void main(String[] args) { //Test encrypt and decrypt
        String text = "110010111101000001101101111011000100010001101100001100010010010001101001000100001001000010100110100111000101110100000110100011001100100010110000100101101100100110110010000000000010100010110011001011110100111011111000101010010100001010110001110100100100011001000100011001001100010100010111101011011001110001111111111110100111010101011000111001001000000011011011101001110001011000011001001011101010001010110000110101111101000110000110111011101000100001011110001101001001000110111100110001111011101111111001011011101011010011000100010111101000010001000100110100111100010101100110001001101010001010010010110111000100111100000100010111101101111010100011111111101011010101110001011110110011000000110011011101101101000110110111100000011101000001000000000111100001011100000011100101100111100011100001110100101110101011110100011111010010101001100000110000111101011110111000010000001010101001101001000010110010000101110011100011100110101011011110110000000100110011000010000111011010111100010010010101111101000100010100111110100000010100100111101101000111001001100101000000000001011111000001001001010111010011001000101110011100101001000010111110010110111001001100101110110100110110110100110111010101100100111101001110100101000011100000010111111111110101100010101011110011101010010111100101001100001000001110100111001010100001010010001011100010111010010010011100000000101011000000000000000110010101011101000000101001101001011101101100101110011011111011000110101011101101100100110110110011001101000000100101001010011000001011110000101100111010010011011111110001101110101000101101001101101101010101101111101011001111001110010110111100011011000101000011111100100010110011101100101110110101100000000101000100100011101110010000010000101111011100001101000101101000001110000111110100001100000010100011110110001010101111001110110110010000000000110110100000010001010111000100010111011101010000110001100101111100011111000101000010010101111000011111100100011001011100110001100010100100100001000010111100001011000010000010100011001111111110100001100001101111001001101101001111110001110111111010110110000110101010000101000010010011100111010011011101000110011111110111111101000100101111011000011100001011101111000101101010010101000100011000111100110000100100101110000010100010011101011111111000000000010010100011111000101111111000110111100100011011110100010110010110001000100101011111111111110101101110100001011000000001111110011110010010011010000010010001010001100001100101011011101111010100111000010000110110010111110001100010101000011010011100010001110101001111110101101011011111100001100000000010101001001001100100100111010111100011001101010011110100010100101101110011110001101000011110111010100001100000100111110100111001100110110000101110110100000000001101011011011110110101101011001010111011111011010100100100111010111100100101111110111011110110000111101010101110001001111110000101011101100001010100010100011101011100010000100110111011011000100011100101011011011011101111000100001010000010011001001100011100000110100000011100110111101110010101100010011000011010111100101001011001001000001111000100111010111110100000000011111010111110011111110100011100100100110110110011011011000111110111011000010111000000001000100011011111111110111100001000010001100111010101010001110000000110101000110010111100010000111110101101101111010111111000000100000010001101001011000001111010001011011010111000011111001011110110101110101001001001110011100111110100011111110110110111010000010001000110001101010001100101001001101110010010110101001001010000000000000011101111010000010010000110111111000101010110001011010010101101100101111010110111101001111111101010111000111110101100011101111110000001101110010001001010110101100111010000001011001000011100011011110000110001000100001011000010101101010001000101011010010000101110100110000001010100101101110110110000000000000001100000010111100000001110011101011011011011111010110101111110111111000000111010011001101110100110010011111000010001011011000000000001111101011011001110100101111001111111101011111001000011110001001010010000010110001110001001001011101001011001010001111111010011110111000000101100011001001011100110001010010000101101010110110101101110001010111110110101010010111101000100011000000110101000100010010000101110110111110111101010010101011110000101101110010001110101001101100000110000100101100111011010010110011110101011111100010010101010001100011010011010101000011000110111110100110111100110001001100000111111100010101100001110101101110101001010101000000001110111011001010010111010011000001100000110000101100001100110100101100011010101010101010000000011010111001111111100011101110101010001100100010000101000011111000000000010100001110100010010101011011011010001000011100000100000101101111110101000101011111000111000101011000000011110100100011011010011001111010111110101101100011100010111001011101001001011110100010110000101000110100110101110100100100011100010010011110000111100011010100111101110000101010100111100000001000110110110111001000111000111111110000000100110100100000001011000000100001000000011100010100000010110000101000000110010010011110010011001011110001001101101100101110010110011011000110100010000101110101110111110101011101110000100101000010001001001110111001011100101011111111110001110111101110101000101111010101111001001011111101101100101011001111101111000100011011001010010101111110100111110001011100101110011001100000001111110111011011010110011100111100111110011110111011001100100000110110000110110000101011100001001011111111010110100000000001111101110100011000011011011100110110011100000101110111011110011001011011110101111011111111111011111010011001101001100000101001000001111010010001110110110010111101010111110010001111000001111011101011011100110010101100100010101101100101111001000001101110010111011001001111100110100110010110010101110011010011100000010101000110100111110010010100010101001111011000011110011010101110001110101010011111101100001010101111100001001011101110000010000010100111111010101010100001100000110010010000101111011101100101101010001001011100100010011110111110111001011001001101100110110010011011011100000000111100011101111001010111011110000111010010100001110111001011001110100010101101101011011011100000111110001111000001000110011111011111100101001000010100100001010101010101001010001000110011001001111000100111000110001010101110110011011100101100011110110110111110011011101101111010111101000000111011111110101011110111001000001000100100000000001101011001001100011101101011001011101000010111110100100110100011010100100000110000011100010010010111010011010010110001100011001010001110000000100000011100011101000101011010100011110010010000101011010000001111010100011101011100000011001100001001000100011011100111111001100101111111011111000110100111001110011011100100001101101011001011101011100100011011101010010000100110111111110101011000001101111101010000001110110111010100110001101111000000011011001100010010000001111011010110111000011011010011101101010110010010100001010011111100100010110010010000111001111101011110001111000001000111001011001101010010011110111101100000111111";
        int[] key = new int[]{1,1,0,0,1,1,0,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,0,0,1,0,0,1,1,1,0,0,1,1};
        int[] IV = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//        String result = BlockCipher.binaryArrayConvertToASCII(OFBMode.cipher(key, text, 0, IV));
//        System.out.println("Result: " + result);
//        int[] test = BlockCipher.stringToBinaryArray(result);
//        String txt = BlockCipher.binaryArraytoBinaryString(test);
        String result2 = BlockCipher.binaryArrayConvertToASCII(OFBMode.cipher(key, text, 1, IV));
        System.out.println("Result: " + result2);
    }
}
