package com.example.nimit.crackquizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TeacherViewStatisticsActivity extends AppCompatActivity {
    DatabaseHelper dbHelper = new DatabaseHelper(this);

    private List<QuestionRecord> questionRecordList;

    private BarChart graphView;
    private TextView textViewGradedStudentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_statistics);

        graphView = (BarChart) findViewById(R.id.graphView);
        List<BarEntry> barEntries = new ArrayList<>();

        List<Integer> studentScoreList = new ArrayList<>();
        List<Double> studentPercentageList = new ArrayList<>();

        questionRecordList = dbHelper.retrieveQuestions();
        int totalQuestions = questionRecordList.size();
        studentScoreList = dbHelper.getScoresList();

        textViewGradedStudentNumber = (TextView) findViewById(R.id.textViewClassTotal);
        textViewGradedStudentNumber.setText("Total Number of Students : "+studentScoreList.size());
        //Toast.makeText(this,studentScoreList.size()+"" , Toast.LENGTH_SHORT).show();
        for (int i = 0; i < studentScoreList.size(); i++)
        {
            String eachScore = studentScoreList.get(i)+"";

            double scoreDouble = 0;
            try{
                scoreDouble = Double.parseDouble(eachScore);
                studentPercentageList.add((scoreDouble/totalQuestions)*100);
            }catch(Exception e){

            }
        }

        int number_below_50 = 0;
        int number_50_60 = 0;
        int number_60_70 = 0;
        int number_70_80 = 0;
        int number_80_90 = 0;
        int number_90_100 = 0;


        for (int i = 0; i < studentPercentageList.size(); i++)
        {
          if(studentPercentageList.get(i) <50.0){
              number_below_50++;
          }else if(studentPercentageList.get(i)>=50 && studentPercentageList.get(i)<60){
              number_50_60++;
          }else if(studentPercentageList.get(i)>=60 && studentPercentageList.get(i)<70){
              number_60_70++;
          }else if(studentPercentageList.get(i)>=70 && studentPercentageList.get(i)<80){
              number_70_80++;
          }else if(studentPercentageList.get(i)>=80 && studentPercentageList.get(i)<90){
              number_80_90++;
          }else{
              number_90_100++;
          }

        }

        barEntries.add(new BarEntry(0,number_below_50));
        barEntries.add(new BarEntry(1,number_50_60));
        barEntries.add(new BarEntry(2,number_60_70));
        barEntries.add(new BarEntry(3,number_70_80));
        barEntries.add(new BarEntry(4,number_80_90));
        barEntries.add(new BarEntry(5,number_90_100));

        BarDataSet barDataSet =  new BarDataSet(barEntries, "Number of Students");

        final List<String> percentages = new ArrayList<>();
        percentages.add("below 50%");
        percentages.add("50%-60%");
        percentages.add("60%-70%");
        percentages.add("70%-80%");
        percentages.add("80%-90%");
        percentages.add("90%-100%");

        XAxis xAxis = graphView.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        graphView.getXAxis().setValueFormatter(new IndexAxisValueFormatter(percentages));

        BarData theData = new BarData(barDataSet);

        Description description = new Description();
        description.setText("Percentages");

        graphView.setDescription(description);
        graphView.setData(theData);
        graphView.setTouchEnabled(true);
        graphView.setScaleEnabled(true);
        graphView.setDragEnabled(true);

    }

}
