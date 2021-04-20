package igushkin.lesson2.exceptions;

public class NegativeIndexException extends RuntimeException{

    public NegativeIndexException(String s) {
        super(s);
    }
}
