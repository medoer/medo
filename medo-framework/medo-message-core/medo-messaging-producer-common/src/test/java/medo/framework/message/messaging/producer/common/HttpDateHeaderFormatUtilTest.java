package medo.framework.message.messaging.producer.common;

import static junit.framework.TestCase.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

public class HttpDateHeaderFormatUtilTest {

    @Test
    public void shouldFormatDateNow() {
        Assert.assertNotNull((HttpDateHeaderFormatUtil.nowAsHttpDateString()));
    }

    @Test
    public void shouldFormatDate() {
        String expected = "Tue, 15 Nov 1994 08:12:31 GMT";
        ZonedDateTime time = ZonedDateTime.parse(expected, DateTimeFormatter.RFC_1123_DATE_TIME);
        assertEquals(expected, HttpDateHeaderFormatUtil.timeAsHttpDateString(time));
    }
}
