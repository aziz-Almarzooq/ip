package adrian;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests Adrian's command processing independently of the console and JavaFX interfaces.
 */
public class AdrianTest {
    @TempDir
    private Path temporaryDirectory;

    /**
     * Verifies that task commands update, find, and remove tasks through the response API.
     */
    @Test
    public void getResponse_taskCommands_returnsExpectedResponses() {
        Adrian adrian = createAdrian();

        assertTrue(adrian.getResponse("todo read book").contains("[T][ ] read book"));
        assertTrue(adrian.getResponse("deadline submit report /by 2026-09-10 1800")
                .contains("[D][ ] submit report"));
        assertTrue(adrian.getResponse("event meeting /from 2026-09-11 1000 /to 2026-09-11 1100")
                .contains("[E][ ] meeting"));

        String taskList = adrian.getResponse("list");
        assertTrue(taskList.contains("1.[T][ ] read book"));
        assertTrue(taskList.contains("2.[D][ ] submit report"));
        assertTrue(taskList.contains("3.[E][ ] meeting"));

        assertTrue(adrian.getResponse("mark 1").contains("[T][X] read book"));
        assertTrue(adrian.getResponse("unmark 1").contains("[T][ ] read book"));

        String searchResults = adrian.getResponse("find report");
        assertTrue(searchResults.contains("[D][ ] submit report"));
        assertFalse(searchResults.contains("read book"));

        assertTrue(adrian.getResponse("delete 2").contains("[D][ ] submit report"));
        assertFalse(adrian.getResponse("list").contains("submit report"));
    }

    /**
     * Verifies that invalid commands return an explanatory error response.
     */
    @Test
    public void getResponse_invalidCommand_returnsErrorResponse() {
        Adrian adrian = createAdrian();

        assertTrue(adrian.getResponse("unknown").startsWith("OOPS!!!"));
        assertTrue(adrian.getResponse("mark 1").contains("That task number does not exist."));
        assertTrue(adrian.getResponse("deadline task /by tomorrow")
                .contains("Please use date format yyyy-MM-dd HHmm."));
    }

    /**
     * Verifies that saved tasks are available in a new Adrian session.
     */
    @Test
    public void constructor_savedTaskExists_loadsTask() {
        Storage storage = new Storage(temporaryDirectory.resolve("data/adrian.txt"));
        Adrian firstSession = new Adrian(storage);
        firstSession.getResponse("todo persisted task");

        Adrian secondSession = new Adrian(storage);

        assertTrue(secondSession.getResponse("list").contains("[T][ ] persisted task"));
    }

    /**
     * Verifies that the exit command returns a farewell and records the exit request.
     */
    @Test
    public void getResponse_bye_requestsExit() {
        Adrian adrian = createAdrian();

        assertTrue(adrian.getResponse("bye").contains("Bye."));
        assertTrue(adrian.isExitRequested());
    }

    /**
     * Creates an Adrian instance backed by an isolated task file.
     *
     * @return Adrian instance for the current test.
     */
    private Adrian createAdrian() {
        Path dataFile = temporaryDirectory.resolve("data/adrian.txt");
        return new Adrian(new Storage(dataFile));
    }
}
