package htlwahl;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class CustomConsoleFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return record.getMessage();
    }
}
