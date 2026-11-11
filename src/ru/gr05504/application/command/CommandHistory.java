package ru.gr05504.application.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandHistory {

    private final Deque<Command> history = new ArrayDeque<>();

    public void executeCommand(Command command) {
        if (command == null || !command.canExecute()) {
            return;
        }

        command.execute();
        history.push(command);
    }

    public void undoLast() {
        if (!canUndo()) {
            return;
        }

        Command command = history.pop();
        command.undo();
    }

    public boolean canUndo() {
        return !history.isEmpty();
    }
}
