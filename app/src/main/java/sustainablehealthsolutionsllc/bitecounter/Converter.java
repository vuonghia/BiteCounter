package sustainablehealthsolutionsllc.bitecounter;

/**
 * This function sets the height and the weight to the metric system,
 * regardless of what is passed into it. It's smart enough to figure
 * it out.
 */
public class Converter {
    boolean isMetric;
    float height;
    float weight;


    /**
     * This mutator function sets whether the
     * data being entered is in the metric
     * system or in the imperial system.
     */
    public void setIsMetric(boolean newIsMetric)  {
        this.isMetric = newIsMetric;
    }

    /**
     * This accessor function return a
     * boolean value of whether the function
     * is metric or not.
     */

    public boolean getIsMetric()  {
        return this.isMetric;
    }

    /**
     * This mutator function sets the height.
     * Although it is best if the user doesn't
     * use this function, because it may be
     * hard to decide if you set it in
     * meters or in inches which is uber
     * necessary for the bmi to have.
     * The getter function will do the
     * work for you. So relax.
     */
    public void setHeight(float newHeight) {
        this.height = newHeight;
    }

    /**
     * This accessor function actually
     * calls the calcHeight() function
     * in order to set the correct height.
     * Then it returns that value to the
     * caller.
     */
    public float getHeight()  {
        return this.height;
    }

    /**
     * This function sets the weight in kilograms.
     * It saves the weight in kg even if the weight
     * passed in isn't metric. It does this by figuring
     * out whether the class is set to metric or not.
     * @param newWeight
     */
    public void setWeight(float newWeight) {
            this.weight = newWeight;
    }

    /**
     * This function just returns the weight of this class.
     * @return
     */
    public float getWeight () {
        return this.weight;
    }

    /**
     * Parser finds out if the height is metric or
     * imperial and then sets the object to metric or not.
     * then it sets the height.
     * @param newHeight
     */
    public void parser (float newHeight)  {
        String height = String.valueOf(newHeight);
        String delims = "[. ']+";
        String [] splitHeight = height.split(delims);
        Float large = Float.valueOf(splitHeight[0]);
        Float small = Float.valueOf(splitHeight[1]);
        if (small > 12) {
            setIsMetric(true);
            setHeight(large + small);
        } else {
            setIsMetric(false);
            large = large * 12;
            small = large + small;
            setHeight(small * .0254f);
        }


    }

}
