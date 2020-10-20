package medo.common.core.context;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CommonContextTest {

    @Test
    public void testGetConcurrentContext() {
        CommonContext<String, Integer> commonContext = CommonContext.getCurrentContext();
        commonContext.put("2", 2);
        assertThat(commonContext).isNotNull();

        Thread cur =
                new Thread(
                        () -> {
                            CommonContext<String, String> commonContext1 =
                                    CommonContext.getCurrentContext();
                            assertThat(commonContext).isNotEqualTo(commonContext1);
                            commonContext1.put("1", "1");
                            CommonContext<String, Integer> commonContext2 =
                                    CommonContext.getCurrentContext();
                            assertThat(commonContext2).isEqualTo(commonContext);
                            assertThat(commonContext2.get("1")).isEqualTo(1);
                            CommonContext.remove();
                            Integer value = commonContext.get("2");
                            assertThat(value).isEqualTo(2);
                        });
        cur.start();
        CommonContext.putValue("1", 1);
        assertThat(commonContext.get("1")).isEqualTo(CommonContext.getValue("1"));
        CommonContext.remove();
    }

    @Test
    public void testRemove() {
        CommonContext<String, Integer> currentContext = CommonContext.getCurrentContext();
        CommonContext.putValue("1", 2);
        assertThat(currentContext.get("1")).isEqualTo(2);
        CommonContext.remove();
        assertThat(currentContext.get("1")).isEqualTo(2);
    }
}
