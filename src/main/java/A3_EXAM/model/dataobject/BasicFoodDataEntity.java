package A3_EXAM.model.dataobject;

/**
 * This abstract class acts as a template for recording the most basic information about a food item
 * to be stored by the system. This is a foundation for other data objects to inherit from and to expand based on further data to store and behaviour
 * to exhibit.
 */
public abstract class BasicFoodDataEntity implements BasicFoodData {

    protected String foodId;
    protected String foodLabel;
    protected String foodUri;
    protected String measureUri;
    protected String measure;

    /**
     * Gets food id of the food item
     * Preconditions: None
     * Postconditions: None
     *
     * @return the food id of the food item
     */
    @Override
    public String getFoodId() {
        return foodId;
    }

    /**
     * Sets food id of the food item
     * Preconditions: None
     * Postconditions: The BasicFoodData object will be updated to have the supplied id
     *
     * @param foodId the food id of the food item
     */
    @Override
    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    /**
     * Gets food label of the food item
     * Preconditions: None
     * Postconditions: None
     *
     * @return the food label of the food item
     */
    @Override
    public String getFoodLabel() {
        return foodLabel;
    }

    /**
     * Sets food label of the food item
     * Preconditions: None
     * Postconditions: The BasicFoodData object will be updated to have the supplied label
     *
     * @param foodLabel the food label of the food item
     */
    @Override
    public void setFoodLabel(String foodLabel) {
        this.foodLabel = foodLabel;
    }

    /**
     * Gets food uri of the food item
     * Preconditions: None
     * Postconditions: None
     *
     * @return The food uri of the food item
     */
    @Override
    public String getFoodUri() {
        return foodUri;
    }

    /**
     * Sets food uri of the food item
     * Preconditions: None
     * Postconditions: The BasicFoodData object will be updated to have the supplied food uri
     *
     * @param foodUri the food uri of the food item
     */
    @Override
    public void setFoodUri(String foodUri) {
        this.foodUri = foodUri;
    }

    /**
     * Gets primary measure of the food item
     * Preconditions: None
     * Postconditions: None
     *
     * @return the primary measure of food item
     */
    @Override
    public String getMeasure() {
        return measure;
    }

    /**
     * Sets primary measure of the food item
     * Preconditions: None
     * Postconditions: The BasicFoodData object will be updated to have the supplied primary measure
     *
     * @param measure the primary measure of the food item
     */
    @Override
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    /**
     * Gets measure uri of the food item based on the primary measure
     * Preconditions: None
     * Postconditions: None
     *
     * @return the measure uri of the food item based on the primary measure
     */
    @Override
    public String getMeasureUri() {
        return measureUri;
    }

    /**
     * Sets measure uri of the food item based on the primary measure
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied measure uri
     *
     * @param measureUri the measure uri of the food item based on the primary measure
     */
    @Override
    public void setMeasureUri(String measureUri) {
        this.measureUri = measureUri;
    }
}
