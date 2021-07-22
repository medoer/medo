package medo.common.core.id;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author: bryce
 * @date: 2021/6/28 08:41
 */
public class IdGeneratorTest {

    private IdGenerator idGenerator = new IdGeneratorImpl();

    @Test
    public void testIdGenerate() {
        Int128 int128 = idGenerator.generateId();
        Assertions.assertThat(int128).isNotNull();
    }
}
