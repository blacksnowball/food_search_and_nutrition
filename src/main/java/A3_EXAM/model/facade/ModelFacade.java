package A3_EXAM.model.facade;

import A3_EXAM.model.dataobject.FoodItemData;
import A3_EXAM.model.dataobject.FoodItemNutrientData;

import java.util.List;

/**
 *  The high-level main access to the model for the view and controller aspects of the program.
 */
public interface ModelFacade {

    /**
     * Caches the results of a query yielding matching food items to a search term. The database will store the search term and the results in the
     * form of a JSON string
     * Preconditions: The JSON String should be in a valid format and not contain an error message
     * Postconditions: The data will be cached adn the database will be updated to contain the supplied data
     * @param searchTerm The search term leading to a particular set of matching items to be cached
     * @param jsonStr The matching item results stored in the form of a JSON String to be cached
     */
    void cacheMatchingItemResults(String searchTerm, String jsonStr);

    /**
     * Caches the results of a query yielding nutrition data of a food item. The database will store the food id, label, and the results associated
     * with a selected food item in the form of a JSON string
     * Preconditions: The JSON String should be in a valid format and not contain an error message
     * Postconditions: The data will be cached adn the database will be updated to contain the supplied data
     * @param yieldingSearchTerm The search term associated with the cached nutritional data
     * @param foodId    The food id of the food item to be cached
     * @param foodLabel The food label of the food item to be cached
     * @param jsonStr   The nutrition data results stored in the form of a JSON String to be cached
     */
    void cacheSelectedItemNutrientData(String yieldingSearchTerm, String foodId, String foodLabel, String jsonStr);

    /**
     * Fetches cached data from the database to yield a list of FoodItemData objects representing the different matching items of a query search
     * Preconditions: The supplied search term should exist in the database
     * Postconditions: None
     * @param searchTerm The search term to look for in the cached data of teh database
     * @return A list of FoodItemData objects constructed from cached data, representing the matching items of a query
     */
    List<FoodItemData> fetchMatchingFoodItemResults(String searchTerm);

    /**
     * Fetches cached data from the database to yield a FoodItemNutrientData object representing the stored nutrient data of a food item when
     * selected from a list of matching items.
     * Preconditions: The supplied search food id and food label should exist in the database
     * Postconditions: None
     * @param foodId The food id of the desired cached food item
     * @param foodLabel The food label of the desired cached food item
     * @return A FoodItemNutrientData object constructed from cached data, representing the nutrient data of a selected food item
     */
    FoodItemNutrientData fetchFoodItemNutrientDataBySelection(String foodId, String foodLabel);

    /**
     * Fetches cached data from the database to yield a FoodItemNutrientData object representing the stored nutrient data of a food item when
     * searched by the user.
     * Preconditions: The supplied search term should exist in the database
     * Postconditions: None
     * @param userQuery The search term provided by the user
     * @return A FoodItemNutrientData object constructed from cached data, representing the nutrient data of a selected food item
     */
    FoodItemNutrientData fetchFoodItemNutrientDataBySearch(String userQuery);


    /**
     * Check if the search query term and any results have been cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param query The query term to check in the database
     * @return True if the query term has cached data, otherwise false
     */
    boolean checkIfSearchQueryIsCachedForMatchingItems(String query);

    /**
     * Check if the search query term has nutrition data for a selected item cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param userQuery The query term to check in the database
     * @return True if the query term has cached data as nutrition data for a food item, otherwise false
     */
    boolean checkIfUserQueryHasMatchingNutritionData(String userQuery);

    /**
     * Checks if the data of the selected item from a list of matching food items has been cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param foodId The food id of the selected food item to check in the database
     * @param label The label of the selected food item to check in the database
     * @return True if the selected food item data has been cached, otherwise false
     */
    boolean checkIfSelectedItemIsCached(String foodId, String label);

    /**
     * Generates a list of FoodItemData objects based on the data from a Edamam API call for matching items of a query
     * Preconditions: The supplied string should be in a JSON format
     * Postconditions: None
     * @param jsonResponse The string containing the outcome of a Edamam API call to find matching items to a query, either as a JSON string or
     * error message
     * @return A list of FoodItemData objects extracted from the supplied string representing a list of matching items to a query
     */
    List<FoodItemData> getFoodItemDataObjects(String jsonResponse);

    /**
     * Generates a FoodItemNutrientData object based on the data from a Edamam API call for the nutrition data of a selected food item
     * Preconditions: The supplied string should be in a JSON format
     * Postconditions: None
     * @param jsonResponse The string containing the outcome of a Edamam API call to find matching items to a query, either as a JSON string or
     * error message
     * @return A FoodItemNutrientData object representing the comprehensive nutrient data of a selected food item
     */
    FoodItemNutrientData getFoodItemNutrientData(String jsonResponse);


    /**
     * Fetches cached data from the database to yield a FoodItemData object representing general data about a food item. This is retrieved when the
     * user requests loading from a cache of a particular food item by search
     * Preconditions: The supplied user query should exist in the database
     * Postconditions: None
     * @param userQuery The search term inputted by the user
     * @return A FoodItemData object constructed from cached data which is associated with existing cached nutrition data
     */
    FoodItemData fetchFoodItemDataAssociatedWithNutritionDataSearch(String userQuery);

    /**
     * Converts the supplied nutrition report to a QR code and uploads it to Imgur. Returns the link generated when uploading the image. Note that
     * if the currently selected food item's caloric value exceeds the max calorie amount, the report contents will start with an asterisk.
     * Preconditions: None
     * Postconditions: None
     * @param report The nutrition report of a food item
     * @return A string containing the Imgur link to the supplied nutrition report which has an image in the form of a QR Code, formatted according
     * to whether the selected food item's energy value exceeds the max calorie amount given by the user
     */
    String uploadNutritionReportAndGetURL(String report);

    /**
     * Prepares the nutrition data report of a FoodItemNutrientData object
     * Preconditions: None
     * Postconditions: None
     * @param foodItem The FoodItemNutrientData object upon which the report will be based
     * @return A string representing a nutrition report of the supplied food item
     */
    String prepareNutritionReportData(FoodItemNutrientData foodItem);

    /**
     * Makes a request to the Edamam API to retrieve matching items for a food query text. This will be in the form of a JSON string or an error
     * message if one occurs during a HTTP request
     * Preconditions: The input query string cannot be empty
     * Postconditions: None
     * @param query The query to be searched to return matching food items, forming part of the API call
     * @return The string representing the JSON outcome of a Edamam API call or an error message if one arises
     */
    String foodSearchRequest(String query);

    /**
     * Makes a request to the Edamam API to retrieve the nutrition data of a food item. This will be in the form of a JSON string or an error
     * message if one occurs during a HTTP request
     * Preconditions: The input strings (measureUri or foodId) cannot be empty
     * Postconditions: None
     * @param measureUri The measure uri of the food item to be searched, forming part of the API call
     * @param foodId The food id of the food item to be searched, forming part of the API call
     * @return The string representing the JSON outcome of a Edamam API call or an error message if one arises
     */
    String foodNutritionSearchRequest(String measureUri, String foodId);

    /**
     * Gets the food items that the GUI application is currently keeping track of based on the matching item
     * outcomes of food queries made by the user
     * Preconditions: None
     * Postconditions: None
     * @return A list of food items currently being tracked by the GUI application which match the user query for food items
     */
    List<FoodItemData> getFoodItems();

    /**
     * Sets the food items that the GUI application is currently keeping track of and displaying based on the
     * matching item outcomes of food queries made by the user
     * Preconditions: None
     * Postconditions: The food items the GUI application is currently keeping track and displaying will be updated
     * @param foodItems The list of food items that the GUI application should now be tracking
     */
    void setFoodItems(List<FoodItemData> foodItems);

    /**
     * Gets the nutrition data of a food item the user has selected among the list of matching food items being tracked by the GUI
     * from the results of user query
     * Preconditions: None
     * Postconditions: None
     * @return The nutrition data of a food item that has been selected among the food items the GUI is currently tracking
     */
    FoodItemNutrientData getNutrientDataOfFoodItem();

    /**
     * Sets the nutrition data of a food item the user has selected among the list of matching food items being tracked by the GUI
     * from the results of user query
     * Preconditions: None
     * Postconditions: The nutrition data in the model will be updated to reflect the currently selected food item
     * @param foodItemNutrientData The nutrition data of a food item that will match the currently selected food item in the GUI application
     */
    void setNutrientDataOfFoodItem(FoodItemNutrientData foodItemNutrientData);

    /**
     * Updates the max calorie amount to keep track of as a point of comparison with the energy values of food items
     * Preconditions: The user must input the desired calorie amount
     * Postconditions: The max calorie count will be updated
     * @param maxCalorieAmount The inputted maximum calorie amount given by the user
     */
    void setMaxCalorieAmount(int maxCalorieAmount);

    /**
     * Gets the max calorie amount as a point of comparison with the energy values of food items
     * Preconditions: None
     * Postconditions: None
     * @return The maximum calorie amount set by the user
     */
    int getMaxCalorieAmount();

    /**
     * Verifies whether the caloric value of the currently selected food item (taken from nutrition data) exceeds the max calorie count
     * Preconditions: None
     * Postconditions: None
     * @return True if the caloric value of the food item exceeds the max calorie amount, otherwise false
     */
    boolean checkFoodItemExceedsMaxCalories();

    /**
     * Updates whether the caloric value of the currently selected food item (taken from nutrition data) exceeds the max calorie count
     * Preconditions: The user must first select a food item and review its nutrition data
     * Postconditions: The status of whether the currently selected food item exceeds the max calorie count will be updated
     * @param status The status of whether the caloric value of the food item exceeds the max calorie count
     */
    void updateFoodItemExceedsMaxCalories(boolean status);


}
