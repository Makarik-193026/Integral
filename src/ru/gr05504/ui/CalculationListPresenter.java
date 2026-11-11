package ru.gr05504.ui;

import ru.gr05504.application.CalculationObserver;
import ru.gr05504.application.CalculationService;
import ru.gr05504.domain.Calculation;

public class CalculationListPresenter implements CalculationObserver {

    private final CalculationListView view;
    private final CalculationService service;

    public CalculationListPresenter(
            CalculationListView view,
            CalculationService service
    ) {
        this.view = view;
        this.service = service;
    }

    public void start() {
        service.addObserver(this);
        refresh();
    }

    public void stop() {
        service.removeObserver(this);
    }

    @Override
    public void onCalculationsChanged() {
        refresh();
    }

    public void refresh() {
        view.showCalculations(service.getAll());
    }

    public void deleteSelected() {
        Calculation calculation = view.getSelected();

        if (calculation == null) {
            view.showError("Выберите расчёт в списке");
            return;
        }

        service.delete(calculation);
    }
}