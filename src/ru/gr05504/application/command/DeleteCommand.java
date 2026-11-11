package ru.gr05504.application.command;

import ru.gr05504.application.CalculationService;
import ru.gr05504.domain.Calculation;

public class DeleteCommand implements Command {

    private final CalculationService service;
    private final Calculation calculation;

    private boolean executed;

    public DeleteCommand(
            CalculationService service,
            Calculation calculation
    ) {
        this.service = service;
        this.calculation = calculation;
    }

    @Override
    public boolean canExecute() {
        return !executed && calculation != null;
    }

    @Override
    public void execute() {
        if (!canExecute()) {
            throw new IllegalStateException(
                    "Невозможно удалить расчёт"
            );
        }

        service.delete(calculation);
        executed = true;
    }

    @Override
    public void undo() {
        if (!executed) {
            return;
        }

        service.restore(calculation);
        executed = false;
    }
}
