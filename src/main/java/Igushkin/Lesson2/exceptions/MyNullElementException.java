package igushkin.lesson2.exceptions;

public class MyNullElementException extends NullPointerException{

    public MyNullElementException (String s) {
        super(s);
    }
}
