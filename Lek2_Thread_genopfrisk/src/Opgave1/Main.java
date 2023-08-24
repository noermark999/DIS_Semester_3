package Opgave1;

public class Main {
    public static void main(String[] args) {
        Lytter lytter = new Lytter();
        Printer printer = new Printer();

        lytter.start();
        printer.start();
    }
}
