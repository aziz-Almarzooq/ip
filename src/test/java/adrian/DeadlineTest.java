package adrian;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void toString_unmarkedDeadline_correctString() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 6, 6, 18, 0);
        Deadline deadline = new Deadline("return book", dateTime);

        assertEquals(
                "[D][ ] return book (by: Jun 06 2026, 6:00 pm)",
                deadline.toString());
    }

    @Test
    public void toString_markedDeadline_correctString() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 6, 6, 18, 0);
        Deadline deadline = new Deadline("return book", dateTime);
        deadline.markAsDone();

        assertEquals(
                "[D][X] return book (by: Jun 06 2026, 6:00 pm)",
                deadline.toString());
    }

    @Test
    public void toDataString_markedDeadline_correctString() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 6, 6, 18, 0);
        Deadline deadline = new Deadline("return book", dateTime);
        deadline.markAsDone();

        assertEquals(
                "D | 1 | return book | 2026-06-06T18:00",
                deadline.toDataString());
    }
}