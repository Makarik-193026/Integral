package ru.gr05504.ui;

import ru.gr05504.application.CalculationService;
import ru.gr05504.application.command.CalculateCommand;
import ru.gr05504.application.command.CommandHistory;
import ru.gr05504.domain.FunctionDefinition;

import java.util.LinkedHashMap;
import java.util.Map;

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
            FunctionDefinition definition = createDefinition();

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
                    definition,
                    service,
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
            FunctionDefinition definition = createDefinition();

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
                    definition,
                    service,
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

    private FunctionDefinition createDefinition() {
        String formula = view.getFormula();

        if (formula == null || formula.isBlank()) {
            throw new IllegalArgumentException("Введите формулу");
        }

        String variable = view.getVariable();

        if (variable == null || variable.isBlank()) {
            throw new IllegalArgumentException(
                    "Введите переменную интегрирования"
            );
        }

        FunctionDefinition.Builder builder = FunctionDefinition
                .builder(formula.trim())
                .variable(variable.trim());

        Map<String, Double> parameters =
                parseParameters(view.getParametersText());

        parameters.forEach(builder::parameter);

        return builder.build();
    }

    private Map<String, Double> parseParameters(String text) {
        Map<String, Double> parameters = new LinkedHashMap<>();

        if (text == null || text.isBlank()) {
            return parameters;
        }

        String[] entries


                = text.split("[,;]");

        for (String entry : entries) {
            String[] parts = entry.trim().split("=");

            if (parts.length != 2
                    || parts[0].isBlank()
                    || parts[1].isBlank()) {
                throw new IllegalArgumentException(
                        "Параметры вводятся в формате a=3, b=5"
                );
            }

            String name = parts[0].trim();

            if (parameters.containsKey(name)) {
                throw new IllegalArgumentException(
                        "Параметр " + name + " указан несколько раз"
                );
            }

            double value = parseNumber(
                    parts[1], "Значение параметра " + name
            );

            parameters.put(name, value);
        }

        return parameters;
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