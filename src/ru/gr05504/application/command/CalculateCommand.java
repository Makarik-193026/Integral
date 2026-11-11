package ru.gr05504.application.command;

import ru.gr05504.application.CalculationService;
import ru.gr05504.domain.Calculation;
import ru.gr05504.domain.metods.MethodRealised;

public class CalculateCommand implements Command {

    private final CalculationService service;
    private final String formula;
    private final double lower;
    private final double upper;
    private final int intervals;
    private final MethodRealised method;

    private Calculation calculation;
    private boolean executed;

    public CalculateCommand(
            CalculationService service,
            String formula,
            double lower,
            double upper,
            int intervals,
            MethodRealised method
    ) {
        this.service = service;
        this.formula = formula;
        this.lower = lower;
        this.upper = upper;
        this.intervals = intervals;
        this.method = method;
    }

    @Override
    public boolean canExecute() {
        return !executed
                && formula != null
                && !formula.isBlank()
                && service.isFormulaValid(formula)
                && Double.isFinite(lower)
                && Double.isFinite(upper)
                && lower < upper
                && intervals >= 1
                && method != null;
    }

    @Override
    public void execute() {
        if (!canExecute()) {
            throw new IllegalStateException(
                    "Невозможно выполнить расчёт"
            );
        }

        calculation = service.calculate(
                formula,
                lower,
                upper,
                intervals,
                method
        );

        executed = true;
    }

    @Override
    public void undo() {
        if (!executed || calculation == null) {
            return;
        }

        service.delete(calculation);
        executed = false;
    }
}