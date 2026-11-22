package ru.gr05504.ui.report;

public class ShortReportCreator extends CalculationReportCreator{

    @Override
    public CalculationReport createReport(){
        return new ShortCalculationReport();
    }

}
