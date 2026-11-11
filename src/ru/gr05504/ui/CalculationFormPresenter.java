package ru.gr05504.ui;

import ru.gr05504.application.CalculationService;
import ru.gr05504.application.command.CalculateCommand;
import ru.gr05504.application.command.CommandHistory;

public class CalculationFormPresenter {

    private final CalculationFormView view;
    private final CalculationService service;
    private final CommandHistory commandHistory;

    public CalculationFormPresenter(
            CalculationFormView view,
            CalculationService service,
            CommandHistory commandHistory
    ) {
        this.view = view;
        this.service = service;
        this.commandHistory = commandHistory;

        updateCalculateAvailability();
    }

    public boolean calculateClicked() {
        try {
            String formula = view.getFormula().trim();

            if (formula.isEmpty()) {
                throw new IllegalArgumentException(
                        "Введите формулу, например sin(x)"
                );
            }

            double lower = parseNumber(
                    view.getLowerText(), "Нижний предел"
            );

            double upper = parseNumber(
                    view.getUpperText(), "Верхний предел"
            );

            int intervals = parseInteger(
                    view.getIntervalsText(), "Число отрезков"
            );

            CalculateCommand command = new CalculateCommand(
                    service,
                    formula,
                    lower,
                    upper,
                    intervals,
                    view.getMethod()
            );

            if (!command.canExecute()) {
                throw new IllegalArgumentException(
                        "Проверьте введённые данные"
                );
            }

            commandHistory.executeCommand(command);

            view.clearForm();
            updateCalculateAvailability();
            updateUndoAvailability();

            return true;

        } catch (IllegalArgumentException ex) {
            view.showError(ex.getMessage());
            updateCalculateAvailability();
            return false;
        }
    }

    public void updateCalculateAvailability() {
        try {
            String formula = view.getFormula();

            if (formula == null || formula.isBlank()) {
                view.setCalculateEnabled(false);
                return;
            }

            double lower = parseNumber(
                    view.getLowerText(), "Нижний предел"
            );

            double upper = parseNumber(
                    view.getUpperText(), "Верхний предел"
            );

            int intervals = parseInteger(
                    view.getIntervalsText(), "Число отрезков"
            );

            CalculateCommand command = new CalculateCommand(
                    service,
                    formula.trim(),
                    lower,
                    upper,
                    intervals,
                    view.getMethod()
            );

            view.setCalculateEnabled(command.canExecute());

        } catch (IllegalArgumentException ex) {
            view.setCalculateEnabled(false);
        }
    }

    private double parseNumber(String text, String fieldName) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " не может быть пустым"
            );
        }

        try {
            return Double.parseDouble(
                    text.trim().replace(',', '.')
            );
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    fieldName + ": введите число"
            );
        }
    }

    private int parseInteger(String text, String fieldName) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " не может быть пустым"
            );
        }

        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                    fieldName + ": введите целое число"
            );
        }
    }
    public void undoLast() {
        if (!commandHistory.canUndo()) {
            return;
        }

        commandHistory.undoLast();

        updateUndoAvailability();
        updateCalculateAvailability();
    }

    private void updateUndoAvailability() {
        view.setUndoEnabled(commandHistory.canUndo());
    }
}