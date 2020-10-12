class ECBMode {
    public static int[] ECB(String key, String text, int crypt){
        int[] keyBin = BlockCipher.stringToBinaryArray(key);
        int[] plainBin = BlockCipher.stringToBinaryArray(text);
        int[] cipherText;
        if(crypt == 0) {
            cipherText = BlockCipher.Encrypt(plainBin, keyBin);
        }else{
            cipherText = BlockCipher.Decrypt(plainBin, keyBin);
        }
        return cipherText;
    }
    public static void main(String[] args){ //Test encrypt and decrypt
        String key = "hello";
        String text = "yikes";
        String result = BlockCipher.BinaryArrayToString(ECB(key,text,0));
        System.out.println(result);
        String result2 = BlockCipher.BinaryArrayToString(ECB(key,result,1));
        System.out.println(result2);
    }
}
