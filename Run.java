import java.util.Scanner;

public class Run {
    public static void main(String[] args) {
        System.out.println("Please input the plaintext");
        Scanner myObj = new Scanner(System.in);
        String text = myObj.nextLine();

        System.out.println("Please input the key");
        String key = myObj.nextLine();

        BlockCipher.Encrypt(text, key);
        myObj.close();
    }
}