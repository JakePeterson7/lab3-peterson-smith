import java.util.Scanner;

class Run {
    private static int[] result;
    private static String text;
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Encrypt or decrypt?(0 or 1): ");
        int crypt = Integer.parseInt(myObj.nextLine());

//        System.out.println("Please input the IV");
//        String IV = myObj.nextLine();

        System.out.println("Please input the plaintext");
        text = myObj.nextLine();

        System.out.println("Please input the key");
        String key = myObj.nextLine();

        for(int i = 0; i < 3; i ++){
            System.out.println("Please select the mode: 0 ECB, 1 CBC, 2 CFB,");
            int mode = Integer.parseInt(myObj.nextLine());
            System.out.println("Processing. . .");
            modeSelect(mode,key,text,crypt);
            text = BlockCipher.BinaryArrayToString(result);
            System.out.println(text);
        }
        System.out.println("End ciphertext:" + text);
        myObj.close();
    }
    private static void modeSelect(int mode, String key, String text, int crypt){
        if(mode == 0){
            result = ECBMode.ECB(key, text, crypt);
        }
        if(mode == 1){
            result = CFBMode.CFB(key, text, crypt);
        }
        if(mode == 2){

        }
        if(mode == 3){

        }
    }
}
