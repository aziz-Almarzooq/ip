package adrian;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that takes place between a start and end date and time.
 */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    /** Date and time at which this event starts. */
    protected LocalDateTime startDateTime;
    /** Date and time at which this event ends. */
    protected LocalDateTime endDateTime;

    /**
     * Creates an incomplete event with the given description and time range.
     *
     * @param description description of the event.
     * @param startDateTime date and time at which the event starts.
     * @param endDateTime date and time at which the event ends.
     */
    public Event(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(description, TaskType.EVENT);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Returns the event in the format used for persistent storage.
     *
     * @return serialized event data.
     */
    @Override
    public String toDataString() {
        return "E | " + super.toDataString()
                + " | " + startDateTime
                + " | " + endDateTime;
    }

    /**
     * Returns a display-friendly representation containing the event time range.
     *
     * @return formatted event description.
     */
    @Override
    public String toString() {
        return super.toString()
                + " (from: " + startDateTime.format(OUTPUT_FORMAT)
                + " to: " + endDateTime.format(OUTPUT_FORMAT) + ")";
    }
}
