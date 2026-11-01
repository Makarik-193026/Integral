// ui/CalculatorView.java
package ru.gr05504.ui;

import ru.gr05504.domain.Calculation;
import ru.gr05504.domain.IntegrationMethod;

import java.util.List;

// Контракт между презентерами и окном
public interface CalculatorView {
    String getFormula();
    String getLowerText();
    String getUpperText();
    String getIntervalsText();
    IntegrationMethod getMethod();
    Calculation getSelected();

    void clearForm();
    void showCalculations(List<Calculation> calculations);
    void showError(String message);
}//