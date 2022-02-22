package htlwahl;

import org.testng.annotations.Test;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.testng.Assert.*;

public class CustomConsoleFormatterTest {

    @Test
    public void testFormatting() {
        Formatter formatter = new CustomConsoleFormatter();
        String testString = "test";
        assertEquals(formatter.format(new LogRecord(Level.INFO,testString)), testString);
    }

}