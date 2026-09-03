package adrian;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void toString_unmarkedEvent_correctString() {
        LocalDateTime from = LocalDateTime.of(2026, 8, 6, 14, 0);
        LocalDateTime to = LocalDateTime.of(2026, 8, 6, 16, 0);
        Event event = new Event("project meeting", from, to);

        assertEquals(
                "[E][ ] project meeting (from: Aug 06 2026, 2:00 pm"
                        + " to: Aug 06 2026, 4:00 pm)",
                event.toString());
    }

    @Test
    public void toString_markedEvent_correctString() {
        LocalDateTime from = LocalDateTime.of(2026, 8, 6, 14, 0);
        LocalDateTime to = LocalDateTime.of(2026, 8, 6, 16, 0);
        Event event = new Event("project meeting", from, to);
        event.markAsDone();

        assertEquals(
                "[E][X] project meeting (from: Aug 06 2026, 2:00 pm"
                        + " to: Aug 06 2026, 4:00 pm)",
                event.toString());
    }

    @Test
    public void toDataString_unmarkedEvent_correctString() {
        LocalDateTime from = LocalDateTime.of(2026, 8, 6, 14, 0);
        LocalDateTime to = LocalDateTime.of(2026, 8, 6, 16, 0);
        Event event = new Event("project meeting", from, to);

        assertEquals(
                "E | 0 | project meeting | 2026-08-06T14:00 | 2026-08-06T16:00",
                event.toDataString());
    }
}