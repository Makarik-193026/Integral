package ru.gr05504.ui.report;

import ru.gr05504.domain.Calculation;

public interface CalculationReport {

    String generate(Calculation calculation);

}
