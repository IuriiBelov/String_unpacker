import unpacking.Unpacker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String packedString = scanner.nextLine();
        scanner.close();

        String unpackedString = Unpacker.unpackString(packedString);

        if (unpackedString != null) {
            System.out.println(unpackedString);
        } else {
            System.out.println("The input string is not valid");
            System.out.println("Maybe you should try again");
        }
    }
}
