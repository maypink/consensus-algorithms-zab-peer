package org.example.utilities;
import org.netbeans.core.startup.logging.NbFormatter;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

@Component
public class LogFormatter extends Formatter {

    public void format(String logMsg) {
        StringBuilder sb = new StringBuilder();

        // Prepend a timestamp
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime timestamp = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        sb.append(timestamp.toLocalTime().toString());
        sb.append(" ---- ");
        sb.append(logMsg);

        System.out.println(sb);
    }

    @Override
    public String format(LogRecord record) {
        return null;
    }
}