package sustainablehealthsolutionsllc.bitecounter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class SettingsActivity extends ActionBarActivity {
    Context context;
    public Counter mcounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = getApplicationContext();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buzzerAlert(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Buzzer Editor");

        alertDialog.setButton("Don't Buzz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // No updates are done, so it exits the alert dialog.
                saveBuzzer(false);
                Intent intent = new Intent(SettingsActivity.this, BiteCounter.class);
                startActivity(intent);
            }
        });
        alertDialog.setButton2("Buzz", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
                saveBuzzer(true);
                Intent intent = new Intent(SettingsActivity.this, BiteCounter.class);
                startActivity(intent);
            }
        });
        alertDialog.show();

    }

    public void limitAlert(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Bite Limit Editor!");

        LinearLayout lila1= new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL);

        final TextView limitMessage = new TextView(this);
        limitMessage.setText("Please Enter a new Limit");
        lila1.addView(limitMessage);
        final EditText newLimit = new EditText(this);
        lila1.addView(newLimit);

        alertDialog.setView(lila1);

        alertDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // No updates are done, so it exits the alert dialog.
            }
        });
        alertDialog.setButton2("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
                String limitInput = newLimit.getText().toString();
                //Test if limitInput is a number
                if (limitInput.equals("") || !isNumeric(limitInput)
                        || isTooLarge(limitInput) || greaterThan1000(limitInput)
                        || containsNewline(limitInput) || containsSpaces(limitInput)) {
                    CharSequence text = "Please enter a number less than 1000";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
//                    mcounter = new Counter();
                    int theLimit = Integer.parseInt(limitInput);
//                    Log.i("errMsg", "The ..............................limit Input is " + limitInput + ": " + Integer.toString(theLimit));
//                    mcounter.setLimit(theLimit);
                    SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("theLimit", theLimit);
                    editor.apply();
                    Intent intent = new Intent(SettingsActivity.this, BiteCounter.class);
                    startActivity(intent);
                }
            }
        });
        alertDialog.show();
    }

    public void biteAlert(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Bite Number Editor");

        LinearLayout lila1= new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL);

        final TextView biteMessage = new TextView(this);
        biteMessage.setText("Please Enter the correct number of bites");
        lila1.addView(biteMessage);
        final EditText bites = new EditText(this);
        lila1.addView(bites);

        alertDialog.setView(lila1);

        alertDialog.setButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // No updates are done, so it exits the alert dialog.
            }
        });
        alertDialog.setButton2("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // here you can add functions
                String biteInput = bites.getText().toString();
                //Test if limitInput is a number
                if (biteInput.equals("") || !isNumeric(biteInput)
                        || isTooLarge(biteInput) || greaterThan1000(biteInput)
                        || containsSpaces(biteInput) || containsNewline(biteInput)) {
                    CharSequence text = "Please enter a number less than 1000";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    saveBitesOnDay(biteInput);
                    Intent intent = new Intent(SettingsActivity.this, BiteCounter.class);
                    startActivity(intent);
                }
            }
        });
        alertDialog.show();
    }

    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean isTooLarge(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public boolean greaterThan1000(String str) {
        double d = Double.parseDouble(str);
        if (d > 1000) {
            return true;
        } else {
            return false;
        }
    }

    public boolean containsSpaces(String str) {
        if (str.contains(" ")) {
            return true;
        }
        return false;
    }

    public boolean containsNewline(String str) {
        if (str.contains("\n")) {
            return true;
        }
        return false;
    }

    public void saveBuzzer(boolean buzz) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("buzz", buzz);
        editor.apply();
    }

    public boolean retrieveBuzzer() {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        boolean shouldBuzz = settings.getBoolean("buzz", true);
        return shouldBuzz;
    }

    public void saveBitesOnDay(String newBitesToSave) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int answer = Integer.parseInt(newBitesToSave);
        switch (dayOfWeek) {
            case 1:
                SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("sunday", answer);
                editor.apply();
                break;
            case 2:
                SharedPreferences setting = context.getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor edito = setting.edit();
                edito.putInt("monday", answer);
                edito.apply();
                break;
            case 3:
                SharedPreferences settin = context.getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor edit = settin.edit();
                edit.putInt("tuesday", answer);
                edit.apply();
                break;
            case 4:
                SharedPreferences setti = context.getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor edi = setti.edit();
                edi.putInt("wednesday", answer);
                edi.apply();
                break;
            case 5:
                SharedPreferences sett = context.getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor ed = sett.edit();
                ed.putInt("thursday", answer);
                ed.apply();
                break;
            case 6:
                SharedPreferences set = context.getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor e = set.edit();
                e.putInt("friday", answer);
                e.apply();
                break;
            case 7:
                SharedPreferences se = context.getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor es = se.edit();
                es.putInt("saturday", answer);
                es.apply();
                break;
            default:
                System.out.println("Wasn't saved correctly :(");
                break;
        }
    }

}
