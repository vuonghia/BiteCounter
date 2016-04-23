package sustainablehealthsolutionsllc.bitecounter;

import android.content.Context;
import android.content.DialogInterface;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioAttributes;
import android.os.AsyncTask;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * This is heart of the BiteCounter app. It collects all of the data that
 * is used in the BiteCounter activity as well as the graph activity.
 */
public class BiteCounter extends ActionBarActivity {
    private static final String errMsg = "errMsg";
    static Context context;
    private Vibrator vibrator;
    private BMI bmi = new BMI();

    //this will enable the circle progress bar
    @AndroidView(R.id.circle_progress_bar)
    private ProgressBar circleProgress;
    private int pStatus;
    private Counter counter = new Counter();
    private static final String LOG_ERROR = "ERROR:Issue with line:";
    private Integer date;

    /**
     * OnCreate: starts up the app activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        checkFirstRunBiteCounter();
        addListenerImageButton();
        loadImageToLayout();
        this.context = getApplicationContext();

        //sets up the
        counter.setContext(context);
        counter.setNumBites(counter.retrieveBitesOnDay());

        circleProgress = (ProgressBar) findViewById(R.id.circle_progress_bar);

        TextView viewText = (TextView) findViewById(R.id.countView);

            String starText = Integer.toString(this.counter.retrieveBitesOnDay());

            viewText.setText(starText,TextView.BufferType.EDITABLE);

        biteCounterSetLimitTest();
        circleProgress.setMax(counter.retrieveLimit(context));

        circleProgress.setProgress(counter.retrieveBitesOnDay());

        if(counter.retrieveBitesOnDay() > counter.retrieveLimit(context)) {
            circleProgress.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }

    }

    /**
     * This function is the most critical in terms of the timing, and saving of everything
     * in the app. It sets the progress bar, and the counter text from shared preferences,
     * and then runs the reseter() function which continually checks to see if the counter and
     * progress bar need to be reset.
     */
    public void onStart()  {
        super.onStart();
        counter.setNumBites(counter.retrieveBites(context));
        counter.setLimit(counter.retrieveLimit(context));
        circleProgress.setMax(counter.retrieveLimit(context));

        circleProgress.setProgress(this.counter.retrieveBitesOnDay());

        if(this.counter.retrieveBitesOnDay() > this.counter.retrieveLimit(context)) {
            circleProgress.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }

        reseter();
    }

    /**
     * onResume: is called when the app is called again from the device
     * after being on.
     */
    public void onResume() {
        super.onResume();
        biteCounterSetLimitTest();

        //this is the Progress bars update when onStart is called
       circleProgress.setMax(counter.retrieveLimit(context));
       circleProgress.setProgress(counter.retrieveBitesOnDay());

        TextView viewText = (TextView) findViewById(R.id.countView);

        String resText = Integer.toString(this.counter.retrieveBitesOnDay());

        viewText.setText(resText,TextView.BufferType.EDITABLE);

        if(counter.retrieveBitesOnDay() > counter.retrieveLimit(context)) {
            circleProgress.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        }


    }

    /**
     * onStop is called when the activity is killed
     */
    public void onStop() {
        super.onStop();
    }

    /**
     * called when the app is totally killed
     */
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current bite number and limit
        savedInstanceState.putInt("bites", this.counter.retrieveBites(this.context));
        savedInstanceState.putInt("limit", this.counter.retrieveLimit(this.context));

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

    }

    /**
     * This function was supposed to be getting called when the activity is restored.
     * But this function shouldn't be getting used. Default to onStart() if you
     * wish to change the state of anything in the app.
     * @param savedInstanceState
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        int restoredBites = savedInstanceState.getInt("bites");
        int restoredLimit = savedInstanceState.getInt("limit");
        this.counter.setNumBites(restoredBites);
        this.counter.setLimit(restoredLimit);
    }

    /**
     * counter method: enables count ability to both the Progress Bar
     * and the TextView
     * @param view The view of the activity Bite Counter
     */
    public void counter(View view) {

        vibrator = (Vibrator) getSystemService(context.VIBRATOR_SERVICE);

        TextView text = (TextView) findViewById(R.id.countView);

        circleProgress = (ProgressBar) findViewById(R.id.circle_progress_bar);

        circleProgress.setMax(counter.retrieveLimit(context));

        counter.incrementBite(context);

        pStatus = counter.retrieveBitesOnDay();
                circleProgress.setProgress(pStatus);


        //need to do this weird set so progress bar will update

               circleProgress.setProgress(pStatus);


    if(counter.retrieveBites(context) > counter.retrieveLimit(context)) {

        circleProgress.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        //we want to vibrate when over the limit
        //vibrate for 5 seconds and play alarm
        if(vibrator.hasVibrator() && retrieveBuzzer()) {
            vibrator.vibrate(500);
        }

    }
        String num = Integer.toString(counter.getNumBites());

        text.setText(num, TextView.BufferType.EDITABLE);


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
                case R.id.action_graph:
                    Intent intent = new Intent(BiteCounter.this, Graph.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.action_aboutUs:
                    Intent intentUs = new Intent(BiteCounter.this, AboutUs.class);
                    startActivity(intentUs);
                    finish();
                    return true;
                case R.id.action_tutorial:
                    Intent intentT = new Intent(BiteCounter.this, Tutorials.class);
                    startActivity(intentT);
                    finish();
                    return true;
                case R.id.action_settings:
                    Intent intentS = new Intent(BiteCounter.this, SettingsActivity.class);
                    startActivity(intentS);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    /**
     * This function activates when the Weight Button is pushed.
     * It creates an alertdialog that prompts the user for
     * information.
     * @param view - needs the view of the Bite Counter Activity
     */
    public void startAlertDialog (View view) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Please enter your weight");

        LinearLayout lila1= new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL);

        final TextView weightMessage = new TextView(this);
        weightMessage.setText("Please Enter your weight in lbs");
        lila1.addView(weightMessage);
        final EditText weight = new EditText(this);
        lila1.addView(weight);

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
                boolean isNum = isNumeric(weightInput);

                if (weightInput.equals("")){
                    CharSequence text = "Please reenter your weight";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }  else if (!isNum) {
                    CharSequence text = "Please enter only numbers";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (isTooLarge(weightInput)) {
                    CharSequence text = "Please enter a number less than 1000";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (greaterThan1000(weightInput)) {
                    CharSequence text = "Please enter a number less than 1000";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                        Float newWeight = Float.valueOf(weightInput);
                        Converter converter = new Converter();
                        converter.setWeight(newWeight);  //Needs to be implemented after converter.parser
                        bmi.setWeight(converter.getWeight());

                        Calendar calendar = Calendar.getInstance();
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
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
                                Log.i(errMsg, "The day wasn't saved correctly");
                                break;
                        }

                    }
                }
        });
        alertDialog.show();
    }

    /**
     * Wallpaper Button:
     * This method is a button handler called the loadImageToLayout
     * to let users change wallpapers
     */
    public void addListenerImageButton(){
        ImageButton imageButton = (ImageButton) findViewById(R.id.button_image);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BiteCounter.this, WallpaperBrowser.class);
                startActivity(intent);
            }
        });
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
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.bite_counter);
        SharedPreferences settings = getSharedPreferences("image_data", 0);
        int encodedImage = settings.getInt("image_data", 0);

        // set layout' background
        int pos = encodedImage;
        Drawable wall = loadReadBitmap(encodedImage);
        rl.setBackground(wall);

        // Catch imageID passed from WallpaperBrowser
        Bundle bundle = getIntent().getExtras();
        if (getIntent().getIntExtra("imageID", 99) != 99) {
            pos = bundle.getInt("imageID");
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
     * @param pos - image ID
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
     * @param position - The position for the bitmap to start?
     * @return Drawable
     */
    public Drawable loadReadBitmap(int position){
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

    public class BiteCounterFragment extends ActionBarActivity {

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_layout, container, false);

            return rootView;
        }
    }

    /**
     * THis function saves the weight and number of bites on the current day of the week.
     * However this function shouldn't be used as the logic conflicts with the other logic in the
     * above functions when it is used. However, this function was very helpful in the initial
     * project build.
     */
    public void saveBitesOnDifferentDays() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                counter.saveSunday(context);
                bmi.saveSundayWeight(context);
                break;
            case 2:
                counter.saveMonday(context);
                bmi.saveMondayWeight(context);
                break;
            case 3:
                counter.saveTuesday(context);
                bmi.saveTuesdayWeight(context);
                break;
            case 4:
                counter.saveWednesday(context);
                bmi.saveWednesdayWeight(context);
                break;
            case 5:
                counter.saveThursday(context);
                bmi.saveThursdayWeight(context);
                break;
            case 6:
                counter.saveFriday(context);
                counter.saveFriday(context);
                break;
            case 7:
                counter.saveSaturday(context);
                bmi.saveSaturdayWeight(context);
                break;
            default:
                Log.i(errMsg, "The day wasn't saved correctly");
                break;
        }
    }


    /**
     * This function calls calendar in order to find out what day of
     * the week it is and then it saves that number into sharedPreferences.
     */
    public void setTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        Integer todaysDate = calendar.get(Calendar.DAY_OF_WEEK);
        this.date = todaysDate;
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("todaysDate", todaysDate);
        editor.apply();

    }

    /**
     * This function returns what the last date in shared preferences is.
     * @return integer - todaysDate
     */
    public Integer getLastDate() {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        int todaysDate = settings.getInt("todaysDate", 0);
        return todaysDate;
    }

    /**
     * This function tests to see if the data entered is a number
     * or if it isn't.
     * @param str
     * @return boolean
     */
    public static boolean isNumeric(String str)    {
        try        {
            double d = Double.parseDouble(str);
        } catch(NumberFormatException nfe)        {
            return false;
        }
        return true;
    }

    /**
     * This function tests to see if the number passed in
     * exceeds the value of a double. If it does they probablly entered
     * the wrong number.
     * @param str
     * @return
     */
    public static boolean isTooLarge(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    /**
     * THis function checks to see if a number is greater
     * than a thousand.
     * @param str
     * @return boolean
     */
    public static boolean greaterThan1000(String str) {
        double d = Double.parseDouble(str);
        if (d > 1000) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function was a tester function used in a button
     * in order to reset the counter. But, the code for this is also
     * found in the reseter function.
     * @param view
     */
    public void reset(View view) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("todaysDate", 0);
        editor.apply();

        CharSequence text = "Reset button pushed";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * This function increments the number of days that the app has
     * been running and then saves that value into shared Preferences.
     */
    public void setDaysRun() {
        int newDaysRun = getDaysRun() + 1;
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("daysRun", newDaysRun);
        editor.apply();
    }


    /**
     * This function returns whatever value is in shared preferences
     * for how many days the app has been running.
     * @return int - daysRun
     */
    public  int getDaysRun() {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        int numDaysRun = settings.getInt("daysRun", 0);
        return numDaysRun;
    }

    /**
     * This function runs when the app is on. It finds out if
     * the app is running for the first time. If it is it
     * displays the firstRunDialog.
     */
    public void checkFirstRunBiteCounter() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRunBiteCounter", true);
        if (isFirstRun){
            // Place your dialog code here to display the dialog

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRunBiteCounter", false)
                    .apply();
            View view = null;
            firstRunDialog(view);

        }
    }

    /**
     * This function is called the first time
     * that the user uses the app. When it is called it
     * prompts the user for their weight.
     * @param view
     */
    public void firstRunDialog (View view) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Please enter your weight");

        LinearLayout lila1= new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL);

        final TextView weightMessage = new TextView(this);
        weightMessage.setText("Please Enter your weight in lbs");
        lila1.addView(weightMessage);
        final EditText weight = new EditText(this);
        lila1.addView(weight);

        alertDialog.setView(lila1);
        alertDialog.setButton2("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
                String weightInput = weight.getText().toString();
                boolean isNum = isNumeric(weightInput);
                if (weightInput.equals("")){
                    CharSequence text = "Please reenter your weight";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }  else if (!isNum) {
                    CharSequence text = "Please enter only numbers";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (isTooLarge(weightInput)) {
                    CharSequence text = "Please enter a number less than 1000";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else if (greaterThan1000(weightInput)) {
                    CharSequence text = "Please enter a number less than 1000";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    Float newWeight = Float.valueOf(weightInput);
                    Converter converter = new Converter();
                    converter.setWeight(newWeight);  //Needs to be implemented after converter.parser
                    bmi.setWeight(converter.getWeight());

                    Calendar calendar = Calendar.getInstance();
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
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
                            Log.i(errMsg, "The day wasn't saved correctly");
                            break;
                    }

                }

            }
        });
        alertDialog.show();
    }

    /**
     * This function takes a boolean that is passed into it
     * and then saves that value into shared preferences as
     * to whether or not the buzzer should buzz.
     * @param buzz
     */
    public void saveBuzzer(boolean buzz) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("buzz", buzz);
        editor.apply();
    }

    /**
     * This function returns the boolean value of buzz
     * from shared preferences.
     * @return boolean - shouldBuzz
     */
    public boolean retrieveBuzzer() {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        boolean shouldBuzz = settings.getBoolean("buzz", true);
        return shouldBuzz;
    }

    /**
     * Super awesome background thread that runs while the
     * app is running. It checks to find out if the date
     * has changed. If the date has changed it resets the
     * counter and fixes the date. This function also calls
     * setDaysRun() which increments the amount of days this app
     * has been running.
     */
    public void reseter() {
        Thread thread = new Thread(){
            public void run(){
                while (true) {
                    Calendar calendar = Calendar.getInstance();
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    if (getLastDate() != dayOfWeek) {
                        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("todaysDate", 0);
                        editor.apply();
                        counter.resetCounter();
                        setTodaysDate();
                        counter.setDayToZero();
                        setDaysRun();
                    }
                }
            }
        };
        thread.start();
    }

    /**
     * Checks to see if the app has been running less then
     * 8 days, if it has the limit is set to 1000.
     * If the app has been running for 8 days, it
     * calls counters reduceBy20 function which
     * takes the average number of bites and
     * reduces this amount by 20%
     */
    public void biteCounterSetLimitTest () {
        if (getDaysRun() == 8) {
            counter.reduceBy20(context);
        } else if (getDaysRun() < 8) {
            counter.setLimit(1000);
        }
    }

}

