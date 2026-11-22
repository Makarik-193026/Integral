package ru.gr05504.ui.report;

import ru.gr05504.domain.Calculation;

public class ShortCalculationReport implements CalculationReport {

    @Override
    public String generate(Calculation calculation) {
        return "КРАТКИЙ ОТЧЁТ\n\n"
                + "Формула: " + calculation.getFormula() + "\n"
                + "Пределы интегрирования: ["
                + calculation.getLower() + "; "
                + calculation.getUpper() + "]\n"
                + "Количество интервалов: " + calculation.getIntervals() + "\n"
                + "Результат: " + calculation.getResult();
    }
}
