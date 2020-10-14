import java.util.Scanner;

class Run {
    private static int[] result;

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Encrypt or decrypt?(0 or 1): ");
        int crypt = Integer.parseInt(myObj.nextLine());

        System.out.println("Please input the IV in binary");
        int[] IV = BlockCipher.readBinaryString(myObj.nextLine());
        String text;
        if(crypt == 0) {//Encryption
            System.out.println("Please input the text in string format");
            text = myObj.nextLine();
            text = continueForward(myObj, crypt, IV, text);
            int[] result = BlockCipher.stringToBinaryArray(text);
            BlockCipher.printArray(result);
        }else{//Decryption
            System.out.println("Please input the text in binary");
            text = myObj.nextLine();
            continueForward(myObj, crypt, IV, text);
        }
            myObj.close();
    }

    private static String continueForward(Scanner myObj, int crypt, int[] IV, String text) {
        System.out.println("Please input the key in binary");
        int[] key = BlockCipher.readBinaryString(myObj.nextLine());

        System.out.println("How many modes would you like to go through?(int)");
        int times = Integer.parseInt(myObj.nextLine());

        for (int i = 0; i < times; i++) {
            System.out.println("Please select the mode: 0 ECB, 1 CBC, 2 CFB, 3 OFB, 4 CTR");
            int mode = Integer.parseInt(myObj.nextLine());
            System.out.println("Processing. . .");
            modeSelect1(mode, key, text, crypt, IV);
            text = BlockCipher.BinaryArrayToString(result);
            System.out.println(text);
        }
        return text;
    }

    private static void modeSelect1(int mode, int[] key, String text, int crypt, int[] IV) {
        if (mode == 0) {
            result = ECBMode.ECB(key, text, crypt);
        }
        if (mode == 1) {
            CBCMode CBC = new CBCMode();
            result = CBC.cipher(key, text, crypt, IV);
        }
        if (mode == 2) {
            result = CFBMode.CFB(key, text, crypt, IV);
        }
        if (mode == 3) {
            result = OFBMode.cipher(key, text, IV);
        }
        if (mode == 4) {
            result = CTRMode.cipher(key, text, crypt, IV);
        }else{
            System.out.println("Error: Please input a number from 0 to 4 for mode.");
        }
    }
}
