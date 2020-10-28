package medo.common.core.exception;

import org.junit.Assert;
import org.junit.Test;

public class ExceptionalTest {

    @Test(expected = RuntimeException.class)
    public void testOrElseThrow() {
        Exceptional.of(
                        "test",
                        () -> {
                            throw new RuntimeException("inner exception");
                        })
                .orElseThrow(RuntimeException::new);
    }

    @Test
    public void testOrElseThrowWithNoException() {
        Exceptional.of(
                        "success",
                        () -> {
                            System.out.println("test");
                        })
                .orElseThrow(() -> new RuntimeException("else throw"));
    }

    @Test
    public void testOrElseGet() {
        String originValue = "origin";
        // expect return origin value;
        String businessError =
                (String)
                        Exceptional.of(
                                        originValue,
                                        () -> {;
                                        })
                                .orElseGet(() -> "other");
        Assert.assertEquals("origin", businessError);
        // expect return other value;
        String businessError1 =
                (String)
                        Exceptional.of(
                                        originValue,
                                        () -> {
                                            throw new RuntimeException("business error");
                                        })
                                .orElseGet(() -> "other");
        Assert.assertEquals("other", businessError1);
    }
}
