package Opgave1;

import java.util.Scanner;

public class Lytter extends Thread{
    private Scanner scanner;

    public Lytter() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            MyString.setString(scanner.nextLine());
        }
    }
}
