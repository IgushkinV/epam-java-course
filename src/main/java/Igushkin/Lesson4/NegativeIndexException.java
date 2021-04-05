package Igushkin.Lesson4;

/**
 * Обрабатываемое исключение, выбрасывается, когда обращение к массиву происходит с отрицательным индексом.
 */
public class NegativeIndexException extends Exception{

    public NegativeIndexException(String s) {
        super(s);
    }
}
