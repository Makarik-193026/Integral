package ru.gr05504.ui;

import ru.gr05504.application.CalculationObserver;
import ru.gr05504.application.CalculationService;
import ru.gr05504.application.command.CommandHistory;
import ru.gr05504.application.command.DeleteCommand;
import ru.gr05504.domain.Calculation;

public class CalculationListPresenter implements CalculationObserver {

    private final CalculationListView view;
    private final CalculationService service;
    private final CommandHistory commandHistory;

    public CalculationListPresenter(
            CalculationListView view,
            CalculationService service,
            CommandHistory commandHistory
    ) {
        this.view = view;
        this.service = service;
        this.commandHistory = commandHistory;
    }

    public void start() {
        service.addObserver(this);
        refresh();
        updateUndoAvailability();
    }

    public void stop() {
        service.removeObserver(this);
    }

    @Override
    public void onCalculationsChanged() {
        refresh();
    }

    public void refresh() {
        view.showCalculations(service.getAll());
    }

    public void deleteSelected() {
        Calculation calculation = view.getSelected();

        if (calculation == null) {
            view.showError("Выберите расчёт в списке");
            return;
        }

        DeleteCommand command = new DeleteCommand(
                service,
                calculation
        );

        if (command.canExecute()) {
            commandHistory.executeCommand(command);
            updateUndoAvailability();
        }
    }

    private void updateUndoAvailability() {
        view.setUndoEnabled(commandHistory.canUndo());
    }
}//