package A3_EXAM.model.dataobject;

/**
 * This interface is a record of a type of object that can relay the most basic information about a food item
 * to be stored by the system. This is a foundation for other model interfaces that are concerned with storing more
 * data
 */
public interface BasicFoodData {

    /**
     * Gets food id of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the food id of the food item
     */
    String getFoodId();

    /**
     * Sets food id of the food item
     * Preconditions: None
     * Postconditions: The BasicFoodData object will be updated to have the supplied id
     * @param foodId the food id of the food item
     */
    void setFoodId(String foodId);

    /**
     * Gets food label of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the food label of the food item
     */
    String getFoodLabel();

    /**
     * Sets food label of the food item
     * Preconditions: None
     * Postconditions: The BasicFoodData object will be updated to have the supplied label
     * @param foodLabel the food label of the food item
     */
    void setFoodLabel(String foodLabel);

    /**
     * Gets food uri of the food item
     * Preconditions: None
     * Postconditions: None
     * @return The food uri of the food item
     */
    String getFoodUri();

    /**
     * Sets food uri of the food item
     * Preconditions: None
     * Postconditions: The BasicFoodData object will be updated to have the supplied food uri
     * @param foodUri the food uri of the food item
     */
    void setFoodUri(String foodUri);

    /**
     * Gets primary measure of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the primary measure of food item
     */
    String getMeasure();

    /**
     * Sets primary measure of the food item
     * Preconditions: None
     * Postconditions: The BasicFoodData object will be updated to have the supplied primary measure
     * @param measure the primary measure of the food item
     */
    void setMeasure(String measure);

    /**
     * Gets measure uri of the food item based on the primary measure
     * Preconditions: None
     * Postconditions: None
     * @return the measure uri of the food item based on the primary measure
     */
    String getMeasureUri();

    /**
     * Sets measure uri of the food item based on the primary measure
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied measure uri
     * @param measureUri the measure uri of the food item based on the primary measure
     */
    void setMeasureUri(String measureUri);

}
