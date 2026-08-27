package adrian;

public class Todo extends Task {

    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toDataString() {
        return "T | " + super.toDataString();
    }
}