package ru.gr05504.application.command;

public interface Command {

    void execute();

    void undo();

    boolean canExecute();
}
