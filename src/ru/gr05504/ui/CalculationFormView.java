package ru.gr05504.ui;

import ru.gr05504.domain.metods.MethodRealised;

public interface CalculationFormView {

    String getFormula();
    String getLowerText();
    String getUpperText();
    String getIntervalsText();

    MethodRealised getMethod();

    void setCalculateEnabled(boolean enabled);

    void setUndoEnabled(boolean enabled);

    void clearForm();
    void showError(String message);
}
