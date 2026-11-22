package ru.gr05504.ui.report;

public class DetailedReportCreator extends CalculationReportCreator{

    @Override
    public CalculationReport createReport(){
        return new DetailedCalculationReport();
    }

}
