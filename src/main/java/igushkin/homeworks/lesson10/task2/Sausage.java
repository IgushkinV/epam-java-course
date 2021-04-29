package igushkin.homeworks.lesson10.task2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sausage implements Serializable {

    private String type = null;

    private int weight;

    private long cost;

    public Optional<String> getType() {
        return Optional.ofNullable(type);
    }

}
