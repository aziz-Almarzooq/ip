package adrian;

public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public String toDataString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    public String toString() {
        return "[" + type + "][" + getStatusIcon() + "] " + description;
    }
}