package Igushkin.Lesson2;


/**
 * Unchecked exception. Выбрасывается при попытке использовать ссылку, содержащую null.
 */
public class MyNullElementException extends NullPointerException{

    public MyNullElementException (String s) {
        super(s);
    }
}
