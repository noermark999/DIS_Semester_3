package Opgave1;

public class MyString {
    private static String string;

    public MyString() {
    }


    public static String getString() {
        return string;
    }

    public static void setString(String string) {
        MyString.string = string;
    }
}
