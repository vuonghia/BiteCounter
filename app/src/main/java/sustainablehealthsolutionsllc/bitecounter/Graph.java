package sustainablehealthsolutionsllc.bitecounter;


import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.app.AlertDialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Display Graphs:
 * This class will display the two bites and weights graphs
 * and updates the graphs when users swipe to the layout
 * linked to this class.
 */
public class Graph extends ActionBarActivity {

    // Instances Declaration
    Counter counter = new Counter();
    String errMsg = "errMsg1";
    BMI bmi = new BMI();
    Context context;
    Calendar calendar = Calendar.getInstance();
    TextView textview;
    private ProgressBar horizontalProgress;
    private View mChart;
    private float[] bites = new float[]{
            0, 0, 0, 0, 0, 0, 0
    };
    private float[] weights = new float[]{
            0f, 0f, 0f, 0f, 0f, 0f, 0f
    };
    private String[] mMonth = new String[]{
            "", "", "", "", "", "", ""
    };
    int dayCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
        context = getApplicationContext();
        TextView viewText = (TextView) findViewById(R.id.dayLeft);
        loadImageToLayout();
        biteGraph();
        weightGraph();


        horizontalProgress = (ProgressBar) findViewById(R.id.progressbar);
        horizontalProgress.setMax(7);
        dayCount = calendar.get(calendar.DAY_OF_WEEK);
        String dayish = Integer.toString(dayCount).concat("/7");
        horizontalProgress.setProgress(dayCount);
        viewText.setText(dayish, TextView.BufferType.EDITABLE);

    }

    public void onStart() {
        super.onStart();
        biteGraph();
        weightGraph();
    }

    public void onResume() {
        super.onResume();

        biteGraph();
        weightGraph();
        textview = new TextView(this);
        textview = (TextView) findViewById(R.id.bmiGraph);
        Float theBmi = bmi.retrieveBmi(context);
        Integer newBmi = Math.round(theBmi);
        String currentBmi = Integer.toString(newBmi);
        textview.setText(currentBmi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_counter, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_counter:
                Intent intent = new Intent(Graph.this, BiteCounter.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_aboutUs:
                Intent intentUs = new Intent(Graph.this, AboutUs.class);
                startActivity(intentUs);
                finish();
                return true;
            case R.id.action_tutorial:
                Intent intentT = new Intent(Graph.this, Tutorials.class);
                startActivity(intentT);
                finish();
                return true;
            case R.id.action_settings:
                Intent intentS = new Intent(Graph.this, SettingsActivity.class);
                startActivity(intentS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Date of Week:
     * This simply updates date of week and display on the graph correctly
     */
    public void dateOfWeek() {
        int dayCurrent = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayCurrent == 1) {
            mMonth[6] = "Today";
            mMonth[5] = "Sat";
            mMonth[4] = "Fri";
            mMonth[3] = "Thur";
            mMonth[2] = "Wed";
            mMonth[1] = "Tue";
            mMonth[0] = "Mon";
        }
        if (dayCurrent == 2) {
            mMonth[6] = "Today";
            mMonth[5] = "Sun";
            mMonth[4] = "Sat";
            mMonth[3] = "Fri";
            mMonth[2] = "Thu";
            mMonth[1] = "Wed";
            mMonth[0] = "Tue";
        }
        if (dayCurrent == 3) {
            mMonth[6] = "Today";
            mMonth[5] = "Mon";
            mMonth[4] = "Sun";
            mMonth[3] = "Sat";
            mMonth[2] = "Fri";
            mMonth[1] = "Thu";
            mMonth[0] = "Wed";
        }
        if (dayCurrent == 4) {
            mMonth[6] = "Today";
            mMonth[5] = "Tue";
            mMonth[4] = "Mon";
            mMonth[3] = "Sun";
            mMonth[2] = "Sat";
            mMonth[1] = "Fri";
            mMonth[0] = "Thu";
        }
        if (dayCurrent == 5) {
            mMonth[6] = "Today";
            mMonth[5] = "Wed";
            mMonth[4] = "Tue";
            mMonth[3] = "Mon";
            mMonth[2] = "Sun";
            mMonth[1] = "Sat";
            mMonth[0] = "Fri";
        }
        if (dayCurrent == 6) {
            mMonth[6] = "Today";
            mMonth[5] = "Thu";
            mMonth[4] = "Wed";
            mMonth[3] = "Tue";
            mMonth[2] = "Mon";
            mMonth[1] = "Sun";
            mMonth[0] = "Sat";
        }
        if (dayCurrent == 7) {
            mMonth[6] = "Today";
            mMonth[5] = "Fri";
            mMonth[4] = "Thu";
            mMonth[3] = "Wed";
            mMonth[2] = "Tue";
            mMonth[1] = "Mon";
            mMonth[0] = "Sun";
        }
    }

    /**
     * Bite of Week:
     * This simply updates bites of week and display on the graph correctly
     */
    public void biteOfWeek() {
        int dayCurrent = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayCurrent == 1) {
            bites[6] = counter.retrieveSunday(context);
            bites[5] = counter.retrieveSaturday(context);
            bites[4] = counter.retrieveFriday(context);
            bites[3] = counter.retrieveThursday(context);
            bites[2] = counter.retrieveWednesday(context);
            bites[1] = counter.retrieveTuesday(context);
            bites[0] = counter.retrieveMonday(context);
        }
        if (dayCurrent == 2) {
            bites[6] = counter.retrieveMonday(context);
            bites[5] = counter.retrieveSunday(context);
            bites[4] = counter.retrieveSaturday(context);
            bites[3] = counter.retrieveFriday(context);
            bites[2] = counter.retrieveThursday(context);
            bites[1] = counter.retrieveWednesday(context);
            bites[0] = counter.retrieveTuesday(context);
        }
        if (dayCurrent == 3) {
            bites[6] = counter.retrieveTuesday(context);
            bites[5] = counter.retrieveMonday(context);
            bites[4] = counter.retrieveSunday(context);
            bites[3] = counter.retrieveSaturday(context);
            bites[2] = counter.retrieveFriday(context);
            bites[1] = counter.retrieveThursday(context);
            bites[0] = counter.retrieveWednesday(context);
        }
        if (dayCurrent == 4) {
            bites[6] = counter.retrieveWednesday(context);
            bites[5] = counter.retrieveTuesday(context);
            bites[4] = counter.retrieveMonday(context);
            bites[3] = counter.retrieveSunday(context);
            bites[2] = counter.retrieveSaturday(context);
            bites[1] = counter.retrieveFriday(context);
            bites[0] = counter.retrieveThursday(context);
        }
        if (dayCurrent == 5) {
            bites[6] = counter.retrieveThursday(context);
            bites[5] = counter.retrieveWednesday(context);
            bites[4] = counter.retrieveTuesday(context);
            bites[3] = counter.retrieveMonday(context);
            bites[2] = counter.retrieveSunday(context);
            bites[1] = counter.retrieveSaturday(context);
            bites[0] = counter.retrieveFriday(context);
        }
        if (dayCurrent == 6) {
            bites[6] = counter.retrieveFriday(context);
            bites[5] = counter.retrieveThursday(context);
            bites[4] = counter.retrieveWednesday(context);
            bites[3] = counter.retrieveTuesday(context);
            bites[2] = counter.retrieveMonday(context);
            bites[1] = counter.retrieveSunday(context);
            bites[0] = counter.retrieveSaturday(context);
        }
        if (dayCurrent == 7) {
            bites[6] = counter.retrieveSaturday(context);
            bites[5] = counter.retrieveFriday(context);
            bites[4] = counter.retrieveThursday(context);
            bites[3] = counter.retrieveWednesday(context);
            bites[2] = counter.retrieveTuesday(context);
            bites[1] = counter.retrieveMonday(context);
            bites[0] = counter.retrieveSunday(context);
        }
    }

    /**
     * Weight of Week:
     * This simply updates weight of week and display on the graph correctly
     */
    public void weightOfWeek() {
        int dayCurrent = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayCurrent == 1) {
            weights[6] = (int) bmi.retrieveSundayWeight(context);
            weights[5] = (int) bmi.retrieveSaturdayWeight(context);
            weights[4] = (int) bmi.retrieveFridayWeight(context);
            weights[3] = (int) bmi.retrieveThursdayWeight(context);
            weights[2] = (int) bmi.retrieveWednesdayWeight(context);
            weights[1] = (int) bmi.retrieveTuesdayWeight(context);
            weights[0] = (int) bmi.retrieveMondayWeight(context);
        }
        if (dayCurrent == 2) {
            weights[6] = (int) bmi.retrieveMondayWeight(context);
            weights[5] = (int) bmi.retrieveSundayWeight(context);
            weights[4] = (int) bmi.retrieveSaturdayWeight(context);
            weights[3] = (int) bmi.retrieveFridayWeight(context);
            weights[2] = (int) bmi.retrieveThursdayWeight(context);
            weights[1] = (int) bmi.retrieveWednesdayWeight(context);
            weights[0] = (int) bmi.retrieveTuesdayWeight(context);
        }
        if (dayCurrent == 3) {
            weights[6] = (int) bmi.retrieveTuesdayWeight(context);
            weights[5] = (int) bmi.retrieveMondayWeight(context);
            weights[4] = (int) bmi.retrieveSundayWeight(context);
            weights[3] = (int) bmi.retrieveSaturdayWeight(context);
            weights[2] = (int) bmi.retrieveFridayWeight(context);
            weights[1] = (int) bmi.retrieveThursdayWeight(context);
            weights[0] = (int) bmi.retrieveWednesdayWeight(context);
        }
        if (dayCurrent == 4) {
            weights[6] = (int) bmi.retrieveWednesdayWeight(context);
            weights[5] = (int) bmi.retrieveTuesdayWeight(context);
            weights[4] = (int) bmi.retrieveMondayWeight(context);
            weights[3] = (int) bmi.retrieveSundayWeight(context);
            weights[2] = (int) bmi.retrieveSaturdayWeight(context);
            weights[1] = (int) bmi.retrieveFridayWeight(context);
            weights[0] = (int) bmi.retrieveThursdayWeight(context);
        }
        if (dayCurrent == 5) {
            weights[6] = (int) bmi.retrieveThursdayWeight(context);
            weights[5] = (int) bmi.retrieveWednesdayWeight(context);
            weights[4] = (int) bmi.retrieveTuesdayWeight(context);
            weights[3] = (int) bmi.retrieveMondayWeight(context);
            weights[2] = (int) bmi.retrieveSundayWeight(context);
            weights[1] = (int) bmi.retrieveSaturdayWeight(context);
            weights[0] = (int) bmi.retrieveFridayWeight(context);
        }
        if (dayCurrent == 6) {
            weights[6] = (int) bmi.retrieveFridayWeight(context);
            weights[5] = (int) bmi.retrieveThursdayWeight(context);
            weights[4] = (int) bmi.retrieveWednesdayWeight(context);
            weights[3] = (int) bmi.retrieveTuesdayWeight(context);
            weights[2] = (int) bmi.retrieveMondayWeight(context);
            weights[1] = (int) bmi.retrieveSundayWeight(context);
            weights[0] = (int) bmi.retrieveSaturdayWeight(context);
        }
        if (dayCurrent == 7) {
            weights[6] = (int) bmi.retrieveSaturdayWeight(context);
            weights[5] = (int) bmi.retrieveFridayWeight(context);
            weights[4] = (int) bmi.retrieveThursdayWeight(context);
            weights[3] = (int) bmi.retrieveWednesdayWeight(context);
            weights[2] = (int) bmi.retrieveTuesdayWeight(context);
            weights[1] = (int) bmi.retrieveMondayWeight(context);
            weights[0] = (int) bmi.retrieveSundayWeight(context);
        }
        Log.i(errMsg, "The value of saturday is " + Float.toString(bmi.retrieveSaturdayWeight(context)));
    }

    public int findMaxForGraph(float[] bites) {
        float max = bites[0];

        for ( int i = 1; i < bites.length; i++) {
            if ( bites[i] > max) {
                max = bites[i];
            }
        }
        int intMax = (int) max;
        return intMax;
    }

    /**
     * Bite Graph:
     * Draw the customized weight graph and display it on the related layout
     */
    private void biteGraph() {
        int width = this.getResources().getDisplayMetrics().widthPixels;
        float scale = width / 720.0f;
        int[] x = {0, 1, 2, 3, 4, 5, 6};
        // update bites of week
        biteOfWeek();

        // Creating an XYSeries for bites
        XYSeries bitesSeries = new XYSeries("bites");
        // Adding data to bites and Expense Series
        for (int i = 0; i < x.length; i++) {
            bitesSeries.add(i, bites[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding bites Series to the dataset
        dataset.addSeries(bitesSeries);

        // Creating XYSeriesRenderer to customize bitesSeries
        XYSeriesRenderer bitesRenderer = new XYSeriesRenderer();
        bitesRenderer.setColor(Color.rgb(77, 77, 77)); //color of the graph set to black
        bitesRenderer.setFillPoints(true);
        bitesRenderer.setLineWidth(2);
        bitesRenderer.setDisplayChartValues(true);
        bitesRenderer.setDisplayChartValuesDistance(8); //setting chart value distance

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("");

        /***
         * Customizing graphs
         */
        // setting text size of chart value
        bitesRenderer.setChartValuesTextSize(scale*30);
        // setting text align of chart value
        bitesRenderer.setChartValuesTextAlign(Align.CENTER);
        // setting text spacing of chart value
        bitesRenderer.setChartValuesSpacing(20);
        // setting text size of the title
        multiRenderer.setChartTitleTextSize(30); //28
        // setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(30); //24
        // setting text size of the graph label
        multiRenderer.setLabelsTextSize(scale * 24); //24
        // setting zoom buttons visibility
        multiRenderer.setZoomButtonsVisible(false);
        // setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(false, false);
        // setting click false on graph
        multiRenderer.setClickEnabled(false);
        // setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, false);
        // setting lines to display on y axis
        multiRenderer.setShowGridY(false);
        // setting lines to display on x axis
        multiRenderer.setShowGridX(false);
        // setting legend to fit the screen size
        multiRenderer.setFitLegend(false); // true
        // setting displaying line on grid
        multiRenderer.setShowGrid(false);
        // setting zoom to false
        multiRenderer.setZoomEnabled(false);
        // setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
        // setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(false); // true
        // setting to in scroll to false
        multiRenderer.setInScroll(false);
        // setting to set legend height of the graph
        multiRenderer.setShowLegend(false);
        multiRenderer.setLegendHeight(30);
        // setting x axis label align
        multiRenderer.setXLabelsAlign(Align.CENTER);
        // setting y axis label to align
        multiRenderer.setYLabelsAlign(Align.LEFT);
        // setting text style
        multiRenderer.setTextTypeface("arial", Typeface.BOLD);
        // setting no of values to display in y axis
        multiRenderer.setYLabels(0);
        // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
        // if you use dynamic values then get the max y value and set here
        float limit = findMaxForGraph(bites);
        if (limit <= 100) {
            limit += 8;
        } else if (limit <= 200 && limit > 100) {
            limit += 15;
        } else if (limit <= 400 && limit > 200){
            limit += 50;
        } else if (limit <= 750 && limit > 400) {
            limit += 100;
        } else {
            limit += 200;
        }
        multiRenderer.setYAxisMax(limit);
        // setting y min value
        multiRenderer.setYAxisMin(0);
        // setting x label's color
        multiRenderer.setXLabelsColor(Color.rgb(77, 77, 77));
        //setting used to move the graph on x-axis to .5 to the right
        multiRenderer.setXAxisMin(-.5);
        // setting max values to be display in x axis
        multiRenderer.setXAxisMax(7);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(0.5);
        //setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.WHITE);
        //setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        // setting enablity background color
        multiRenderer.setApplyBackgroundColor(true);

        //setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 30, 30, 30}); // 30,30,30,30
        // update date of week
        dateOfWeek();
        for (int i = 0; i < x.length; i++) {
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }

        // Adding bitesRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(bitesRenderer);

        //this part is used to display graph on the xml
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);
        //remove any views before u paint the chart
        chartContainer.removeAllViews();
        //drawing bar chart
        mChart = ChartFactory.getBarChartView(Graph.this, dataset, multiRenderer, Type.DEFAULT);
        //adding the view to the linear layout
        chartContainer.addView(mChart);
    }

    /**
     * Weight Graph:
     * Draw the customized weight graph and display it on the related layout
    */
    private void weightGraph() {
        int width = this.getResources().getDisplayMetrics().widthPixels;
        float scale = width / 720.0f;
        int[] x = {0, 1, 2, 3, 4, 5, 6};
        // update weight of week
        weightOfWeek();

        // Creating an XYSeries for weights
        XYSeries weightsSeries = new XYSeries("weights");
        // Adding data to weights and Expense Series
        for (int i = 0; i < x.length; i++) {
            weightsSeries.add(i, weights[i]);
        }

        // Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Adding weights Series to the dataset
        dataset.addSeries(weightsSeries);

        // Creating XYSeriesRenderer to customize weightsSeries
        XYSeriesRenderer weightsRenderer = new XYSeriesRenderer();
        weightsRenderer.setColor(Color.rgb(77, 77, 77)); //color of the graph set to black
        weightsRenderer.setFillPoints(true);
        weightsRenderer.setLineWidth(2);
        weightsRenderer.setDisplayChartValues(true);
        weightsRenderer.setDisplayChartValuesDistance(8); // 10 //setting chart value distance

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("");

        /***
         * Customizing graphs
         */
        // setting text size of chart value
        weightsRenderer.setChartValuesTextSize(scale*30);
        // setting text spacing of chart value
        weightsRenderer.setChartValuesSpacing(20);
        // setting text size of the title
        multiRenderer.setChartTitleTextSize(30); //28
        //setting text size of the axis title
        multiRenderer.setAxisTitleTextSize(30); //24
        //setting text size of the graph label
        multiRenderer.setLabelsTextSize(scale*24); //24
        //setting zoom buttons visiblity
        multiRenderer.setZoomButtonsVisible(false);
        //setting pan enablity which uses graph to move on both axis
        multiRenderer.setPanEnabled(false, false);
        //setting click false on graph
        multiRenderer.setClickEnabled(false);
        //setting zoom to false on both axis
        multiRenderer.setZoomEnabled(false, false);
        //setting lines to display on y axis
        multiRenderer.setShowGridY(false);
        //setting lines to display on x axis
        multiRenderer.setShowGridX(false);
        //setting legend to fit the screen size
        multiRenderer.setFitLegend(false); // true
        //setting displaying line on grid
        multiRenderer.setShowGrid(false);
        //setting zoom to false
        multiRenderer.setZoomEnabled(false);
        //setting external zoom functions to false
        multiRenderer.setExternalZoomEnabled(false);
        //setting displaying lines on graph to be formatted(like using graphics)
        multiRenderer.setAntialiasing(false);
        //setting to in scroll to false
        multiRenderer.setInScroll(false);
        //setting to set legend height of the graph
        multiRenderer.setShowLegend(false);
        multiRenderer.setLegendHeight(30);
        //setting x axis label align
        multiRenderer.setXLabelsAlign(Align.CENTER);
        //setting y axis label to align
        multiRenderer.setYLabelsAlign(Align.LEFT);
        //setting text style
        multiRenderer.setTextTypeface("arial", Typeface.BOLD);
        //setting no of values to display in y axis
        multiRenderer.setYLabels(0);
        // setting y axis max value, Since i'm using static values inside the graph so i'm setting y max value to 4000.
        // if you use dynamic values then get the max y value and set here
        int limit = findMaxForGraph(weights);
        if (limit <= 100) {
            limit += 8;
        } else if (limit <= 200 && limit > 100) {
            limit += 15;
        } else if (limit <= 400 && limit > 200){
            limit += 50;
        } else if (limit <= 750 && limit > 400) {
            limit += 100;
        } else {
            limit += 200;
        }
        multiRenderer.setYAxisMax(limit);
        // setting y min value
        multiRenderer.setYAxisMin(0);
        // setting x label's color
        multiRenderer.setXLabelsColor(Color.rgb(77, 77, 77));
        //setting used to move the graph on xaxiz to .5 to the right
        multiRenderer.setXAxisMin(-.5);
        // setting max values to be display in x axis
        multiRenderer.setXAxisMax(7);
        //setting bar size or space between two bars
        multiRenderer.setBarSpacing(0.5);
        //setting background color of the graph to transparent
        multiRenderer.setBackgroundColor(Color.WHITE);
        //setting margin color of the graph to transparent
        multiRenderer.setMarginsColor(getResources().getColor(R.color.transparent_background));
        // setting enblity background color
        multiRenderer.setApplyBackgroundColor(true);

        //setting the margin size for the graph in the order top, left, bottom, right
        multiRenderer.setMargins(new int[]{30, 30, 30, 30}); // 30,30,30,30
        // update date of week
        dateOfWeek();
        for (int i = 0; i < x.length; i++) {
/*            multiRenderer.addXTextLabel(i, mMonth[i]);*/
            multiRenderer.addXTextLabel(i, mMonth[i]);
        }

        // Adding weightsRenderer and expenseRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(weightsRenderer);
//            multiRenderer.addSeriesRenderer(expenseRenderer);

        //this part is used to display graph on the xml
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart2);
        //remove any views before u paint the chart
        chartContainer.removeAllViews();
        //drawing bar chart
        mChart = ChartFactory.getBarChartView(Graph.this, dataset, multiRenderer, Type.DEFAULT);
        //adding the view to the linearlayout
        chartContainer.addView(mChart);
    }

    /**
     * Load Image:
     * This method contains all the code handling load image on to layout,
     * and save current image and load that image for application's next run.
     * This is the main core of wallpaper changing section.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void loadImageToLayout() {
        // retrieve current image and load when starting application
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.graph);
        SharedPreferences settings = getSharedPreferences("image_data", 0);
        int encodedImage = settings.getInt("image_data", 0);

        // set layout' background
        int pos = encodedImage;
        Drawable wall = loadReadBitmap(encodedImage);
        rl.setBackground(wall);

        // Catch imageID passed from WallpaperBrowser
        Bundle bundle = getIntent().getExtras();
        if (getIntent().getIntExtra("imageIDGraph", 99) != 99) {
            pos = bundle.getInt("imageIDGraph");
        }

        // handle image ID
        switch (pos) {
            case 0:
                Drawable wall0 = loadReadBitmap(pos);
                rl.setBackground(wall0);
                saveCurrentBackground(pos);
                break;
            case 1:
                Drawable wall1 = loadReadBitmap(pos);
                rl.setBackground(wall1);
//                    wall = wall1.getConstantState().newDrawable();
                saveCurrentBackground(pos);
                break;
            case 2:
                Drawable wall2 = loadReadBitmap(pos);
                rl.setBackground(wall2);
                saveCurrentBackground(pos);
                break;
            case 3:
                Drawable wall3 = loadReadBitmap(pos);
                rl.setBackground(wall3);
                saveCurrentBackground(pos);
                break;
            case 4:
                Drawable wall4 = loadReadBitmap(pos);
                rl.setBackground(wall4);
                saveCurrentBackground(pos);
                break;
            case 5:
                Drawable wall5 = loadReadBitmap(pos);
                rl.setBackground(wall5);
                saveCurrentBackground(pos);
                break;
            case 6:
                Drawable wall6 = loadReadBitmap(pos);
                rl.setBackground(wall6);
                saveCurrentBackground(pos);
                break;
            case 7:
                Drawable wall7 = loadReadBitmap(pos);
                rl.setBackground(wall7);
                saveCurrentBackground(pos);
                break;
            default:
                break;
        }
    }

    /**
     * Save Current Background:
     * This basically saves current image for loading when application's start-up
     *
     * @param pos
     */
    public void saveCurrentBackground(int pos) {
        SharedPreferences settings = getSharedPreferences("image_data", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("image_data", pos);
        editor.apply();
    }

    /**
     * Load and Read Bitmap:
     * This method creates a bitmap referencing imageID and the bitmap to Drawable.
     *
     * @param position
     * @return Drawable
     */
    public Drawable loadReadBitmap(int position) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mThumbIds[position]);
        return new BitmapDrawable(getResources(), bitmap);
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.wall0, R.drawable.wall1,
            R.drawable.wall2, R.drawable.wall3,
            R.drawable.wall4, R.drawable.wall5,
            R.drawable.wall6, R.drawable.wall7,
    };

    public static class GraphFragment extends ActionBarActivity {
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_layout, container, false);
        }
    }

    public void startAlertDialog1(View view) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Please enter your weight and height");

        LinearLayout lila1 = new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL);

        final TextView weightMessage = new TextView(this);
        weightMessage.setText("Enter your weight in lbs");
        lila1.addView(weightMessage);
        final EditText weight = new EditText(this);
        lila1.addView(weight);


        final TextView heightMessage = new TextView(this);
        heightMessage.setText("Please enter your height ex: 5'7");
        lila1.addView(heightMessage);
        final EditText height = new EditText(this);
        lila1.addView(height);

        alertDialog.setView(lila1);

        alertDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // No updates are done, so it exits the alert dialog.
            }
        });
        alertDialog.setButton2("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
                String weightInput = weight.getText().toString();
                String heightInput = height.getText().toString();
                boolean isNum = true;
                boolean isNum2 = true;
                boolean isNum3 = true;
                boolean isNum4 = true;

                if (heightInput.contains("'")) {
                   try {
                       String[] heightSplit = heightInput.split("'");
                       isNum = isNumeric(heightSplit[0]);
                       isNum2 = isNumeric(heightSplit[1]);
                   } catch (Exception f) {
                       CharSequence text = "Please enter height as 5'9 or as 6";
                       int duration = Toast.LENGTH_LONG;
                       Toast toast = Toast.makeText(context, text, duration);
                       toast.show();
                   }
                } else {
                    isNum3 = isNumeric(heightInput);
                }

               isNum4 = isNumeric(weightInput);

                if (weightInput.equals("") || heightInput.equals("")) {
                    CharSequence text = "Please enter both fields";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (!isNum || !isNum2 || !isNum3 || !isNum4) {
                    CharSequence text = "Please enter only numbers";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (isTooLarge(weightInput) || isTooLarge2(heightInput)) {
                    CharSequence text = "Please numbers less than 1000";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (greaterThan1000(weightInput)) {
                    CharSequence text = "Please enter a weight less than 1000";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    bmi.setWeight(Float.valueOf(weightInput));
                    bmi.setHeight(Float.valueOf(parseHeight(heightInput)));
                    bmi.calcBmi();
                    bmi.saveBmi(context);
                    textview = (TextView) findViewById(R.id.bmiGraph);
                    String newBmi = String.valueOf((int) bmi.getBmi());
                    textview.setText(newBmi);
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//                    String theBmi = "0";
                    switch (dayOfWeek) {
                        case 1:
                            bmi.saveSundayWeight(context);
                            break;
                        case 2:
                            bmi.saveMondayWeight(context);
                            break;
                        case 3:
                            bmi.saveTuesdayWeight(context);
                            break;
                        case 4:
                            bmi.saveWednesdayWeight(context);
                            break;
                        case 5:
                            bmi.saveThursdayWeight(context);
                            break;
                        case 6:
                            bmi.saveFridayWeight(context);
                            break;
                        case 7:
                            bmi.saveSaturdayWeight(context);
                            break;
                        default:
                            Log.i(errMsg, "Unable to save bmi to shared preferences");
                            break;
                    }
                }
            }
        });

        alertDialog.show();
    }

    public int parseHeight(String height) {
        int heightInches;
        try {
            String[] parts = height.split("'");
            heightInches = 12 * Integer.valueOf(parts[0]);
            heightInches += Integer.valueOf(parts[1]);
        } catch (Exception e) {
            heightInches = Integer.valueOf(height);
        }

        return heightInches;
    }

    public static boolean isNumeric(String str)  {
        try        {
            double d = Double.parseDouble(str);
        } catch(NumberFormatException nfe)  {
            return false;
        }
        return true;
    }

    public static boolean isTooLarge(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public static boolean isTooLarge2(String str)  {
        if (str.contains("'")) {
            try {
                String[] parts = str.split("'");
                double a = Double.parseDouble(parts[0]);
                double b = Double.parseDouble(parts[1]);
            } catch (Exception e) {
                return true;
            }
        } else {
            try {
                double d = Double.parseDouble(str);
            } catch (Exception e) {
                return true;
            }

        }
        return false;
    }

    public static boolean greaterThan1000(String str) {
        double d = Double.parseDouble(str);
        if (d > 1000) {
            return true;
        } else {
            return false;
        }
    }
}
