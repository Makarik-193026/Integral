package ru.gr05504.ui.report;

import ru.gr05504.domain.Calculation;

public abstract class CalculationReportCreator {

    // Фабричный метод
    public abstract CalculationReport createReport();

    // Общая логика формирования отчёта
    public String generateReport(Calculation calculation) {
        CalculationReport report = createReport();
        return report.generate(calculation);
    }
}
