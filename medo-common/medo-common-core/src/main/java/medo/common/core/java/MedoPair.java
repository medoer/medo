package medo.common.core.java;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * KEY:VALUE Object
 *
 * @Author: Bryce
 * @Date: 2020/11/5 13:10
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedoPair<T> {

    private String key;
    private T value;

    public static <T> MedoPair<T> create(String key, T value) {
        return new MedoPair<>(key, value);
    }
}
