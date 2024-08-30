package shrimp.command;

import shrimp.task.Task;
import shrimp.task.TaskList;
import shrimp.utility.Ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the finding of tasks in the list by a keyword.
 */
public class FindCommand implements Command {

    private final String keyword;

    /**
     * Constructs a {@code FindCommand} with the specified keyword.
     *
     * @param keyword The keyword to search for in the task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the command to find and display tasks that contain the keyword.
     *
     * @param taskList The list of tasks to search through.
     * @param ui The user interface to interact with the user.
     */
    @Override
    public void run(TaskList taskList, Ui ui) {
        TaskList matchingTasks = new TaskList();

        for (Task task : taskList) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.addTask(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            ui.printError("Can't find any matching task...");
        } else {
            ui.printFind(matchingTasks);
        }
    }
}
