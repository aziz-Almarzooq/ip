package adrian;

/**
 * Represents a task tracked by Adrian.
 */
public class Task {
    /** Description of the work represented by this task. */
    protected String description;
    /** Whether this task has been completed. */
    protected boolean isDone;
    /** Category used to identify and display this task. */
    protected TaskType type;

    /**
     * Creates an incomplete task with the given description and type.
     *
     * @param description description of the task
     * @param type category of the task
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the symbol used to display this task's completion status.
     *
     * @return {@code X} if completed, or a space if incomplete
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the task in the format used for persistent storage.
     *
     * @return serialized completion status and description
     */
    public String toDataString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a display-friendly representation of this task.
     *
     * @return formatted task type, completion status, and description
     */
    @Override
    public String toString() {
        return "[" + type + "][" + getStatusIcon() + "] " + description;
    }
}
