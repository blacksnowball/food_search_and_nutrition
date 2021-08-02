package A3_EXAM.model.database;

import A3_EXAM.model.dataobject.FoodItemData;
import A3_EXAM.model.dataobject.FoodItemNutrientData;

import java.util.List;

/**
 * Interface for the FoodAndNutritionDb. Provides a contract for interacting with a database for caching and retrieving data associated
 * with food API calls.
 */
public interface FoodAndNutritionDb {

    /**
     * Check if the search query term has any matching item results have been cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param userQuery The query term to check in the database
     * @return True if the query term has cached data as matching items, otherwise false
     */
    boolean checkUserQueryExistsForMatchingResults(String userQuery);

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
     * Check if the search query term has nutrition data for a selected item cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param userQuery The query term to check in the database
     * @return True if the query term has cached data as nutrition data for a food item, otherwise false
     */
    boolean checkUserQueryHasMatchingNutritionData(String userQuery);

    /**
     * Fetches cached data from the database to yield a list of FoodItemData objects representing the different matching items of a query search
     * Preconditions: The supplied search term should exist in the database
     * Postconditions: None
     * @param searchTerm The search term to look for in the cached data of teh database
     * @return A list of FoodItemData objects constructed from cached data, representing the matching items of a query
     */
    List<FoodItemData> fetchMatchingFoodItemResults(String searchTerm);

    /**
     * Fetches cached data from the database to yield a FoodItemData object representing general data about a food item. This is retrieved when the
     * user requests loading from a cache of a particular food item by search
     * Preconditions: The supplied user query should exist in the database
     * Postconditions: None
     * @param userQuery The search term inputted by the user
     * @return A FoodItemData object constructed from cached data which is associated with existing cached nutrition data
     */
    FoodItemData fetchFoodItemMatchingNutrientDataFromSearch(String userQuery);

    /**
     * Fetches cached data from the database to yield a FoodItemNutrientData object representing the stored nutrient data of a food item when
     * selected among a list of matching items.
     * Preconditions: The supplied search food id and food label should exist in the database
     * Postconditions: None
     * @param foodId The food id of the desired cached food item
     * @param foodLabel The food label of the desired cached food item
     * @return A FoodItemNutrientData object constructed from cached data, representing the nutrient data of a selected food item
     */
    FoodItemNutrientData fetchSelectedItemNutrientData(String foodId, String foodLabel);

    /**
     * Fetches cached data from the database to yield a FoodItemNutrientData object representing the stored nutrient data of a food item when
     * searched by the user.
     * Preconditions: The supplied search term should exist in the database
     * Postconditions: None
     * @param userQuery The search term provided by the user
     * @return A FoodItemNutrientData object constructed from cached data, representing the nutrient data of a selected food item
     */
    FoodItemNutrientData fetchSearchedItemNutrientData(String userQuery);

    /**
     * Caches the results of a query yielding matching food items to a search term. The database will insert the search term and the results in the
     * form of a JSON string if it does not yet exist in the database. If it already exists, the search term results will only be updated.
     * Preconditions: The JSON String should be in a valid format and not contain an error message
     * Postconditions: The data will be cached and the database will be updated to contain the supplied data
     * @param searchTerm The search term leading to a particular set of matching items to be cached
     * @param jsonStr The matching item results stored in the form of a JSON String to be cached
     */
    void insertMatchingFoodItemsResults(String searchTerm, String jsonStr);

    /**
     * Caches the results of a query yielding nutrition data of a food item. The database will insert the food id, label, associated search termand
     * the results associated with a selected food item in the form of a JSON string if it does not yet exist. If it already exists, the results
     * will be updated instead.
     * Preconditions: The JSON String should be in a valid format and not contain an error message
     * Postconditions: The data will be cached adn the database will be updated to contain the supplied data
     * @param yieldingSearchTerm The search term yielding the food item
     * @param foodId    The food id of the food item to be cached
     * @param foodLabel The food label of the food item to be cached
     * @param jsonStr   The nutrition data results stored in the form of a JSON String to be cached
     */
    void insertSelectedFoodItemNutritionResults(String yieldingSearchTerm, String foodId, String foodLabel, String jsonStr);

}
