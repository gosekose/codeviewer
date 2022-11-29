package codeview.main.businessservice.membergroup.presentation.dao;

import lombok.Builder;
import lombok.Data;

@Data
public class SolveChartInfo<T> {

    private T myChart;
    private T otherChart;

    @Builder
    public SolveChartInfo(T myChart, T otherChart) {
        this.myChart = myChart;
        this.otherChart = otherChart;
    }
}
