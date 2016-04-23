package sustainablehealthsolutionsllc.bitecounter;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class sets the bmi and saves the current days
 * weight into shared preferences.
 */
public class BMI {
    boolean isEntered;
    float height;
    float weight;
    float bmi;

    /**
     * This mutator function sets isEntered
     * to true or false depending on
     * whether or not the value of it
     * is zero or not. When the value is zero,
     * it is considered uninitialized.
     */
    public void setIsEntered()  {
        if (this.height != 0 && this.weight != 0)  {
            this.isEntered = true;
        } else {
            this.isEntered = false;
        }
    }

    /**
     * This mutator function returns isEntered's value.
     * @return
     */
    public boolean getIsEntered()  {
        return this.isEntered;
    }

    /**
     * This mutator function sets the height
     * value of this class.
     * @param newHeight
     */
    public void setHeight(float newHeight)  {
        this.height = newHeight;
    }

    /**
     * This accessor function returns the
     * height of this class.
     * @return
     */
    public float getHeight()  {
        return this.height;
    }

    /**
     * This mutator function sets the weight
     * of this class.
     * @param newWeight
     */

    public void setWeight(float newWeight)  {
        this.weight = newWeight;
    }

    /**
     * This accessor function returns the
     * weight of this class.
     * @return
     */
    public float getWeight()  {
        return this.weight;
    }

    /**
     * This mutator function sets the Bmi
     * of this class. Although it is better
     * to use the calcBmi().
     * @param newBmi
     */
    public void setBmi(float newBmi)  {
        this.bmi = newBmi;
    }

    /**
     * This accessor function returns
     * the bmi of this class.
     * @return
     */
    public float getBmi()  {
        return this.bmi;
    }

    /**
     * This function calculates the bmi
     * of this class. It first makes sure that
     * the height and weight have been entered.
     * If they haven't been entered it displays
     * a warning that a height and weight need
     * to be entered.
     */
    public void calcBmi() {
        this.bmi = (703 * this.weight) / (this.height * this.height);
    }

    /**
     * This function saves mondays weight into shared preferences.
     * @param context
     */
    public void saveMondayWeight(Context context) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("mondayWeight", this.weight);
        editor.apply();
    }

    /**
     * This function returns mondays weight from shared preferences.
     * @param context
     * @return
     */
    public float retrieveMondayWeight(Context context)  {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        float mondayWeight = settings.getFloat("mondayWeight", 0);
        return mondayWeight;
    }

    /**
     * This function saves tuesdays weight into shared preferences.
     * @param context
     */
    public void saveTuesdayWeight(Context context) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("tuesdayWeight", this.weight);
        editor.apply();
    }

    /**
     * This function retrieves tuesdays weight from shared preferences.
     * @param context
     * @return
     */
    public float retrieveTuesdayWeight(Context context)  {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        float tuesdayWeight = settings.getFloat("tuesdayWeight", 0);
        return tuesdayWeight;
    }

    /**
     * This function saves wednesday's weight into shared preferences.
     * @param context
     */
    public void saveWednesdayWeight(Context context) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("wednesdayWeight", this.weight);
        editor.apply();
    }

    /**
     * This function retrieves wednesday's weight from shared preferences.
     * @param context
     * @return
     */
    public float retrieveWednesdayWeight(Context context)  {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        float wednesdayWeight = settings.getFloat("wednesdayWeight", 0);
        return wednesdayWeight;
    }

    /**
     * This functions saves thursday's weight into shared preferences.
     * @param context
     */
    public void saveThursdayWeight(Context context) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("thursdayWeight", this.weight);
        editor.apply();
    }

    /**
     * THis function retrieves thrusdays weight from shared preferences.
     * @param context
     * @return
     */
    public float retrieveThursdayWeight(Context context)  {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        float thursdayWeight = settings.getFloat("thursdayWeight", 0);
        return thursdayWeight;
    }

    /**
     * This functions saves fridays weight into shared preferences
     * @param context
     */
    public void saveFridayWeight(Context context) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("fridayWeight", this.weight);
        editor.apply();
    }

    /**
     * This function retrieves fridays weight from shared preferences
     * @param context
     * @return
     */
    public float retrieveFridayWeight(Context context)  {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        float fridayWeight = settings.getFloat("fridayWeight", 0);
        return fridayWeight;
    }


    /**
     * This function saves saturday's weight into shared preferences
     * @param context
     */
    public void saveSaturdayWeight(Context context) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("saturdayWeight", this.weight);
        editor.apply();
    }

    /**
     * This function retrieves saturdays weight from shared preferences.
     * @param context
     * @return
     */
    public float retrieveSaturdayWeight(Context context)  {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        float saturdayWeight = settings.getFloat("saturdayWeight", 0);
        return saturdayWeight;
    }

    /**
     * This function saves sundays weight into shared preferences.
     * @param context
     */
    public void saveSundayWeight(Context context) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("sundayWeight", this.weight);
        editor.apply();
    }

    /**
     * This function retrieves sunday's weight from shared preferences.
     * @param context
     * @return
     */
    public float retrieveSundayWeight(Context context)  {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        float sundayWeight = settings.getFloat("sundayWeight", 0);
        return sundayWeight;
    }


    /**
     * This function saves the member variable this.bmi into shared preferences.
     * @param context
     */
    public void saveBmi(Context context) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat("currentBmi", this.bmi);
        editor.apply();
    }

    /**
     * This function returns whatever value was in shared preferences for
     * currentBmi
     * @param context
     * @return Float - currentBmi
     */
    public Float retrieveBmi(Context context) {
        SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
        Float currentBmi = settings.getFloat("currentBmi", 0);
        return currentBmi;
    }
}
