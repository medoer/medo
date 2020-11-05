package medo.common.core.java;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** KEY:VALUE Object @Author: Bryce @Date: 2020/11/5 13:10 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedoPair<K, V> {

    private K key;
    private V value;

    public static <K, V> MedoPair<K, V> create(K key, V value) {
        return new MedoPair<>(key, value);
    }
}
