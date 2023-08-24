package Opgave1;

public class Printer extends Thread{
    public Printer() {

    }

    @Override
    public void run() {
        while (true) {
            if (MyString.getString() != null) {
                System.out.println(MyString.getString());
            }
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
