import static java.util.Arrays.copyOfRange;

class CBCMode {
    private static int[] XORtext =new int[35];
    private static int cryptNum;

    public int[] cipher(String key, String text, int crypt, int[] IV) {
        int[] keyBin = BlockCipher.stringToBinaryArray(key);
        int[] plainBin = BlockCipher.stringToBinaryArray(text);
        int[] cipherText = new int[plainBin.length];
        cryptNum = crypt;
//        System.out.println("IV: " + BlockCipher.BinaryArrayToString(IV));
        if (cryptNum == 1) {
            decrypt(keyBin, plainBin, IV, cipherText);
        } else {
            encrypt(keyBin, plainBin, IV, cipherText);
        }
        return cipherText;
    }

    public static void encrypt(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp; //Holds encrypted block
        int[] tempBin; //Holds plaintext to be encrypted
        int l = plainBin.length; //Readability
        initialEncryption(keyBin, plainBin, IV, cipherText); //Initial run of encryption
            for (int i = 1; i < l / 35; i++) { //For each block after the first
                tempBin = copyOfRange(plainBin, (i * 35), ((i * 35) + 35)); //Copy plaintext block into tempBin
                int[] newInput = BlockCipher.addBinaryArrays(tempBin, XORtext); //XOR plaintext and saved XORtext
                temp = BlockCipher.Encrypt(newInput, keyBin); //Encrypt result of XOR with key
                System.arraycopy(temp, 0, cipherText, (i * 35), 35); //Copy result to resulting ciphertext
                System.arraycopy(temp, 0, XORtext, 0, 35); //Overwrite XORtext with result for further encryption
            }
//            if (l % 35 != 0) { //Handles overflow
//                tempBin = copyOfRange(plainBin, (l - l % 35), l);
//                int[] newInput = BlockCipher.addBinaryArrays(tempBin, XORtext);
//                temp = BlockCipher.Encrypt(newInput, keyBin);
//                System.arraycopy(temp, 0, cipherText, (l - l % 35), temp.length);
//            }
    }

    public static void initialEncryption(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp; //Holds encrypted block
        int[] tempBin; //Holds plaintext to be encrypted
        tempBin = copyOfRange(plainBin, 0,  35); //Copy plaintext block into tempBin
        int[] newInput = BlockCipher.addBinaryArrays(tempBin, IV); //XOR plaintext and IV
        temp = BlockCipher.Encrypt(newInput, keyBin); //Encrypt result of XOR with key
        System.arraycopy(temp, 0, XORtext, 0, 35); //Copy result of encrypt for future XORs
        System.arraycopy(temp, 0, cipherText, 0, 35); //Copy result to resulting ciphertext
    }

    public static void decrypt(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp; //Holds encrypted block
        int[] tempBin; //Holds plaintext to be encrypted
        int l = plainBin.length; //Readability
        initialDecryption(keyBin, plainBin, IV, cipherText); //Initial run of decryption
            for (int i = 1; i < l / 35; i++) { //For each block after the first
                tempBin = copyOfRange(plainBin, (i * 35), ((i * 35) + 35)); // Copy ciphertext block into tempBin
                temp = BlockCipher.Decrypt(tempBin, keyBin); //Decrypt ciphertext with key
                int[] newInput = BlockCipher.addBinaryArrays(temp, XORtext); //XOR result of decrption with last block ciphertext
                System.arraycopy(newInput, 0, cipherText, (i * 35), 35); //Copy result of decrypt to resulting plaintext
                System.arraycopy(tempBin, 0, XORtext, 0, 35); //Copy ciphertext from this block for next block
            }
//            if (l % 35 != 0) {
//                tempBin = copyOfRange(plainBin, (l - l % 35), l);
//                temp = BlockCipher.Decrypt(tempBin, keyBin);
//                int[] newInput = BlockCipher.addBinaryArrays(temp, XORtext);
//                System.arraycopy(newInput, 0, cipherText, (l - l % 35), temp.length);
//            }
    }

    public static void initialDecryption(int[] keyBin, int[] plainBin, int[] IV, int[] cipherText) {
        int[] temp; //Holds encrypted block
        int[] tempBin; //Holds plaintext to be encrypted
        System.arraycopy(plainBin, 0, XORtext, 0, 35); //Copy ciphertext block for next block
        tempBin = copyOfRange(plainBin, 0,  35); //Copy ciphertext block into tempBin
        int[] newInput = BlockCipher.Decrypt(tempBin, keyBin); //Decrypt result of XOR with key
        temp = BlockCipher.addBinaryArrays(newInput, IV); //XOR ciphertext with IV
        System.arraycopy(temp, 0, cipherText, 0, 35); //Copy result of decrypt to resulting plaintext
    }

    public static void main(String[] args) { //Test encrypt and decrypt
        String text = "yikesyikesyikes";
        String key = "hellohellohello";
        int[] IV = new int[]{};
        CBCMode CBC = new CBCMode();
        String result = BlockCipher.BinaryArrayToString(CBC.cipher(key, text, 0, IV));
        System.out.println("Result: " + result);
        String result2 = BlockCipher.BinaryArrayToString(CBC.cipher(key, result, 1, IV));
        System.out.println("Result: " + result2);
    }
}
