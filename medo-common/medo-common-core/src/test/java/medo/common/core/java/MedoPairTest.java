package medo.common.core.java;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/** @Author: Bryce @Date: 2020/11/5 13:15 */
public class MedoPairTest {

    @Test
    public void testCreate() {
        MedoPair<String> stringMedoPair = MedoPair.create("key", "value");
        Assertions.assertThat(stringMedoPair.getKey()).isEqualTo("key");
        Assertions.assertThat(stringMedoPair.getValue()).isEqualTo("value");
    }
}
