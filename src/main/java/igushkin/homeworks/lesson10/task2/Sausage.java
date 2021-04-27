package igushkin.homeworks.lesson10.task2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Sausage implements Serializable {

    private String type;

    private int weight;

    private long cost;
}
