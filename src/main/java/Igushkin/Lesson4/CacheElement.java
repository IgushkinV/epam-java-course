package Igushkin.Lesson4;

import java.util.Objects;

public class CacheElement<T> {
    private T element;
    private int index;

    public CacheElement(T element, int index) {
        this.element = element;
        this.index = index;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheElement<T> that = (CacheElement<T>) o;
        return index == that.index && element.equals(that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, index);
    }
}
