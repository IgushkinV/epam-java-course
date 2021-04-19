package igushkin.lesson2;

/**
 * Обрабатываемое исключение, выбрасывается, когда обращение к массиву происходит с отрицательным индексом.
 */
public class NegativeIndexException extends RuntimeException{

    public NegativeIndexException(String s) {
        super(s);
    }
}
