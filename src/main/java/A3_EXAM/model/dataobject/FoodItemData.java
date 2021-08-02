package A3_EXAM.model.dataobject;

import java.util.List;
import java.util.Map;

/**
 * This interface is a record of a type of object that stores the general data of a food item including a basic overview of its nutrition data, going
 * beyond the very basic information offered in the BasicFoodData interface.
 */
public interface FoodItemData extends BasicFoodData {

    /**
     * Gets image link of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the image link of the food item
     */
    String getImageLink();

    /**
     * Sets image link of the food item
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied image link
     * @param imageLink the image link of the food item
     */
    void setImageLink(String imageLink);

    /**
     * Gets basic nutrients of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the basic nutrients of the food item including their quantities
     */
    Map<String, Float> getBasicNutrients();

    /**
     * Sets basic nutrients of the food item including their quantities
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied image link
     * @param basicNutrients the basic nutrients of the food item including their quantities
     */
    void setBasicNutrients(Map<String, Float> basicNutrients);

    /**
     * Gets the different measures available for the food item
     * Preconditions: None
     * Postconditions: None
     * @return the measures of the food item as a list of strings
     */
    List<String> getMeasures();

    /**
     * Sets the different measures available for the food item
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied measures
     * @param measures the measures of the food item as a list of strings
     */
    void setMeasures(List<String> measures);

    /**
     * Gets category label of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the category label of the food item
     */
    String getCategoryLabel();

    /**
     * Sets category label.
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied measures
     * @param categoryLabel the category label of the food item
     */
    void setCategoryLabel(String categoryLabel);

    /**
     * Gets brand of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the brand of the food item
     */
    String getBrand();

    /**
     * Sets brand of the food item
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied brand
     * @param brand the brand of the food item
     */
    void setBrand(String brand);

    /**
     * Gets category of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the category of the food item
     */
    String getCategory();

    /**
     * Sets category of the food item
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied brand
     * @param category the category of the food item
     */
    void setCategory(String category);


    /**
     * Gets yielding search term of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the yielding search term of the food item
     */
    String getYieldingSearchTerm();

    /**
     * Sets yielding search term of the food item
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied brand
     * @param yieldingSearchTerm the yielding search term of the food item
     */
    void setYieldingSearchTerm(String yieldingSearchTerm);

    /**
     * String to display upon the food item being presented as a matching item
     * Preconditions: This method will only be called when the food item is presented to the user
     * as a matching result of a query search
     * Postconditions: None
     * @return A string showing the label, category, and brand (where avaialble) of the food item
     */
    String displayUponMatchingResult();

    /**
     * String to display upon the food item being selected among a list of matching items
     * Preconditions: None
     * Postconditions: None
     * @return A string showing the details of the food item
     */
    String displayUponBeingSelected();

}
