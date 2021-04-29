package igushkin.homeworks.lesson10.task2;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class MyNoNullArrayList<T> extends ArrayList<T> { //todo: Почитать, нужно ли типизировать родительский класс

    private T field;

    @Override
    public boolean add(T t) {
        if (Objects.isNull(t)) {
            log.warn("add() - Passed null to adding. Operation terminated.");
            return false;
        }
        return super.add(t);
    }

    public Optional<T> getNonNullable(int index) {
        return Optional.of(super.get(index));
    }
}
