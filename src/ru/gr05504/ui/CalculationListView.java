package ru.gr05504.ui;

import ru.gr05504.domain.Calculation;
import java.util.List;

public interface CalculationListView {

    Calculation getSelected();

    void showCalculations(List<Calculation> calculations);

    void setDeleteEnabled(boolean enabled);
    void setUndoEnabled(boolean enabled);

    void showError(String message);
}
