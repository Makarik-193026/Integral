package ru.gr05504.ui;

import ru.gr05504.domain.Calculation;

public class CalculationDescriptionFormatter {

    public String format(Calculation c) {
        return "f(x) = " + c.getFormula()
                + " | [" + String.format("%.4f", c.getLower())
                + "; " + String.format("%.4f", c.getUpper()) + "]"
                + " | " + c.getMethod()
                + " | n = " + c.getIntervals()
                + " | I = " + String.format("%.8f", c.getResult());
    }
}
