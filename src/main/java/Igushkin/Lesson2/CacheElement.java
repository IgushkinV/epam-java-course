package igushkin.lesson2;

import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
public class CacheElement<T> {
    @Getter @Setter private T element;
    @Getter @Setter private int index;
}
