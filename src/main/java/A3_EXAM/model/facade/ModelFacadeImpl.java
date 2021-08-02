package A3_EXAM.model.facade;

import A3_EXAM.model.database.FoodAndNutritionDb;
import A3_EXAM.model.api.edamam.FoodDatabaseApiTemplate;
import A3_EXAM.model.api.imgur.ImgurApiTemplate;
import A3_EXAM.model.dataobject.FoodItemData;
import A3_EXAM.model.dataobject.FoodItemNutrientData;
import A3_EXAM.model.dataobject.FoodItemNutrientDataImpl;
import A3_EXAM.model.parsers.JSONParser;
import A3_EXAM.model.parsers.ReportParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Concrete implementation of the ModelFacade interface. Defines the behaviour for the view and controller
 * to interact with the domain logic and data objects of the model component of the program, particularly with
 * API and database calls.
 */
public class ModelFacadeImpl implements ModelFacade {

    private FoodDatabaseApiTemplate foodAPI;
    private ImgurApiTemplate imgurAPI;
    private JSONParser jsonParser;
    private ReportParser reportParser;
    private FoodAndNutritionDb database;
    private List<FoodItemData> foodItems;
    private FoodItemNutrientData foodItemNutrientData;
    private int maxCalorieAmount;
    private boolean foodItemExceedsMaxCalories;

    /**
     * Instantiates a new ModelFacadeImpl object, with dependency injection of various components
     * @param foodAPI      The food api used for making queries about food and their nutrition
     * @param imgurAPI     The imgur api used for generating QR codes and uploading images to Imgur
     * @param database     The database used for caching and fetching data
     * @param jsonParser   The json parser used for processing JSON results from api calls
     * @param reportParser The report parser used for generating reports from model objects to be uploaded
     */
    public ModelFacadeImpl(FoodDatabaseApiTemplate foodAPI, ImgurApiTemplate imgurAPI, FoodAndNutritionDb database,
                           JSONParser jsonParser, ReportParser reportParser) {
        this.foodAPI = foodAPI;
        this.imgurAPI = imgurAPI;
        this.database = database;
        this.jsonParser = jsonParser;
        this.reportParser = reportParser;
        foodItems = new ArrayList<>();
        foodItemNutrientData = new FoodItemNutrientDataImpl();
        this.maxCalorieAmount = 0;
        this.foodItemExceedsMaxCalories = false;
    }



    /**
     * Caches the results of a query yielding matching food items to a search term. The database will store the search term and the results in the
     * form of a JSON string
     * Preconditions: The JSON String should be in a valid format and not contain an error message
     * Postconditions: The data will be cached and the database will be updated to contain the supplied data
     * @param searchTerm The search term leading to a particular set of matching items to be cached
     * @param jsonStr The matching item results stored in the form of a JSON String to be cached
     */
    @Override
    public void cacheMatchingItemResults(String searchTerm, String jsonStr) {
        database.insertMatchingFoodItemsResults(searchTerm, jsonStr);
    }

    /**
     * Caches the results of a query yielding nutrition data of a food item. The database will store the food id, label, and the results associated
     * with a selected food item in the form of a JSON string
     * Preconditions: The JSON String should be in a valid format and not contain an error message
     * Postconditions: The data will be cached and the database will be updated to contain the supplied data
     * @param yieldingSearchTerm The search term associated with the nutrition data
     * @param foodId    The food id of the food item to be cached
     * @param foodLabel The food label of the food item to be cached
     * @param jsonStr   The nutrition data results stored in the form of a JSON String to be cached
     */
    @Override
    public void cacheSelectedItemNutrientData(String yieldingSearchTerm, String foodId, String foodLabel, String jsonStr) {
        database.insertSelectedFoodItemNutritionResults(yieldingSearchTerm, foodId, foodLabel, jsonStr);
    }

    /**
     * Fetches cached data from the database to yield a list of FoodItemData objects representing the different matching items of a query search
     * Preconditions: The supplied search term should exist in the database
     * Postconditions: None
     * @param searchTerm The search term to look for in the cached data of teh database
     * @return A list of FoodItemData objects constructed from cached data, representing the matching items of a query
     */
    @Override
    public List<FoodItemData> fetchMatchingFoodItemResults(String searchTerm) {
        return database.fetchMatchingFoodItemResults(searchTerm);
    }

    /**
     * Fetches cached data from the database to yield a FoodItemNutrientData object representing the stored nutrient data of a food item.
     * Preconditions: The supplied search food id and food label should exist in the database
     * Postconditions: None
     * @param foodId The food id of the desired cached food item
     * @param foodLabel The food label of the desired cached food item
     * @return A FoodItemNutrientData object constructed from cached data, representing the nutrient data of a selected food item
     */
    @Override
    public FoodItemNutrientData fetchFoodItemNutrientDataBySelection(String foodId, String foodLabel) {
        return database.fetchSelectedItemNutrientData(foodId, foodLabel);
    }

    /**
     * Fetches cached data from the database to yield a FoodItemNutrientData object representing the stored nutrient data of a food item when
     * searched by the user.
     * Preconditions: The supplied search term should exist in the database
     * Postconditions: None
     * @param userQuery The search term provided by the user
     * @return A FoodItemNutrientData object constructed from cached data, representing the nutrient data of a selected food item
     */
    @Override
    public FoodItemNutrientData fetchFoodItemNutrientDataBySearch(String userQuery) {
        return database.fetchSearchedItemNutrientData(userQuery);
    }

    /**
     * Fetches cached data from the database to yield a FoodItemData object representing general data about a food item. This is retrieved when the
     * user requests loading from a cache of a particular food item by search
     * Preconditions: The supplied user query should exist in the database
     * Postconditions: None
     * @param userQuery The search term inputted by the user
     * @return A FoodItemData object constructed from cached data which is associated with existing cached nutrition data
     */
    @Override
    public FoodItemData fetchFoodItemDataAssociatedWithNutritionDataSearch(String userQuery) {
        return database.fetchFoodItemMatchingNutrientDataFromSearch(userQuery);
    }


    /**
     * Check if the search query term and any results have been cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param query The query term to check in the database
     * @return True if the query term has cached data, otherwise false
     */
    @Override
    public boolean checkIfSearchQueryIsCachedForMatchingItems(String query) {
        return database.checkUserQueryExistsForMatchingResults(query);
    }

    /**
     * Checks if the data of the selected item from a list of matching food items has been cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param foodId The food id of the selected food item to check in the database
     * @param label The label of the selected food item to check in the database
     * @return True if the selected food item data has been cached, otherwise false
     */
    @Override
    public boolean checkIfSelectedItemIsCached(String foodId, String label) {
        return database.checkIfSelectedItemIsCached(foodId, label);
    }

    /**
     * Check if the search query term has nutrition data for a selected item cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param userQuery The query term to check in the database
     * @return True if the query term has cached data as nutrition data for a food item, otherwise false
     */
    @Override
    public boolean checkIfUserQueryHasMatchingNutritionData(String userQuery) {
        return database.checkUserQueryHasMatchingNutritionData(userQuery);
    }

    /**
     * Generates a list of FoodItemData objects based on the data from a Edamam API call for matching items of a query
     * Preconditions: The supplied string should be in a JSON format
     * Postconditions: None
     * @param jsonResponse The string containing the outcome of a Edamam API call to find matching items to a query, either as a JSON string or
     * error message
     * @return A list of FoodItemData objects extracted from the supplied string representing a list of matching items to a query
     */
    @Override
    public List<FoodItemData> getFoodItemDataObjects(String jsonResponse) {
        return jsonParser.processFoodItemData(jsonResponse);
    }

    /**
     * Generates a FoodItemNutrientData object based on the data from a Edamam API call for the nutrition data of a selected food item
     * Preconditions: The supplied string should be in a JSON format
     * Postconditions: None
     * @param jsonResponse The string containing the outcome of a Edamam API call to find matching items to a query, either as a JSON string or
     * error message
     * @return A FoodItemNutrientData object representing the comprehensive nutrient data of a selected food item
     */
    @Override
    public FoodItemNutrientData getFoodItemNutrientData(String jsonResponse) {
        return jsonParser.processNutrientData(jsonResponse);
    }

    /**
     * Converts the supplied nutrition report to a QR code and uploads it to Imgur. Returns the link generated when uploading the image.
     * Preconditions: None
     * Postconditions: None
     * @param report The nutrition report of a food item
     * @return A string containing the Imgur link to the supplied nutrition report which has an image in the form of a QR Code
     */
    @Override
    public String uploadNutritionReportAndGetURL(String report) {
        if (foodItemExceedsMaxCalories) {
            report = "*" + report;
        }
        return imgurAPI.uploadNutritionReportToImgur(report);
    }

    /**
     * Prepares the nutrition data report of a FoodItemNutrientData object
     * Preconditions: None
     * Postconditions: None
     * @param foodItem The FoodItemNutrientData object upon which the report will be based
     * @return A string representing a nutrition report of the supplied food item
     */
    @Override
    public String prepareNutritionReportData(FoodItemNutrientData foodItem) {
        List<String> prioritisedNutrientData = Arrays.asList("Energy", "Protein", "Fat", "Saturated", "Carbs", "Sugars",
                "Fiber", "Sodium");
        return reportParser.prepareNutritionReportData(foodItem, prioritisedNutrientData);
    }

    /**
     * Makes a request to the Edamam API to retrieve matching items for a food query text. This will be in the form of a JSON string or an error
     * message if one occurs during a HTTP request
     * Preconditions: The input query string cannot be empty
     * Postconditions: None
     * @param query The query to be searched to return matching food items, forming part of the API call
     * @return The string representing the JSON outcome of a Edamam API call or an error message if one arises
     */
    @Override
    public String foodSearchRequest(String query) {

        if (query == null || query.isEmpty()) {
            return "Invalid input! Your query cannot be empty.";
        }

        String response = foodAPI.foodSearchRequest(query);

        if (response.contains("Unauthorized app_id")) {
            return "Cannot access the Edamam food API! Unauthorised credentials (application ID and/or key) are being" +
                    " used.";
        } else if (verifyErrorInAPIResponse(response)) {
            return "Your request could not be processed! Please try again and make sure you have a stable internet " +
                    "connection.";
        }

        return response;
    }

    /**
     * Makes a request to the Edamam API to retrieve the nutrition data of a food item. This will be in the form of a JSON string or an error
     * message if one occurs during a HTTP request
     * Preconditions: The input strings (measureUri or foodId) cannot be empty
     * Postconditions: None
     * @param measureUri The measure uri of the food item to be searched, forming part of the API call
     * @param foodId The food id of the food item to be searched, forming part of the API call
     * @return The string representing the JSON outcome of a Edamam API call or an error message if one arises
     */
    @Override
    public String foodNutritionSearchRequest(String measureUri, String foodId) {

        if (measureUri.isEmpty() || foodId.isEmpty()) {
            return "Invalid input! The food ID or measure URI fields cannot be empty.";
        }

        return foodAPI.foodNutritionSearchRequest(measureUri, foodId);
    }

    /**
     * Gets the food items that the GUI application is currently keeping track of based on the matching item
     * outcomes of food queries made by the user
     * Preconditions: None
     * Postconditions: None
     * @return A list of food items currently being tracked by the GUI application which match the user query for food items
     */
    @Override
    public List<FoodItemData> getFoodItems() {
        return foodItems;
    }


    /**
     * Sets the food items that the GUI application is currently keeping track of and displaying based on the
     * matching item outcomes of food queries made by the user
     * Preconditions: None
     * Postconditions: The food items the GUI application is currently keeping track and displaying will be updated
     * @param foodItems The list of food items that the GUI application should now be tracking
     */
    @Override
    public void setFoodItems(List<FoodItemData> foodItems) {
        this.foodItems = foodItems;
    }

    /**
     * Gets the nutrition data of a food item the user has selected among the list of matching food items being tracked by the GUI
     * from the results of user query
     * Preconditions: None
     * Postconditions: None
     * @return The nutrition data of a food item that has been selected among the food items the GUI is currently tracking
     */
    @Override
    public FoodItemNutrientData getNutrientDataOfFoodItem() {
        return foodItemNutrientData;
    }

    /**
     * Sets the nutrition data of a food item the user has selected among the list of matching food items being tracked by the GUI
     * from the results of user query
     * Preconditions: None
     * Postconditions: The nutrition data in the model will be updated to reflect the currently selected food item
     */
    @Override
    public void setNutrientDataOfFoodItem(FoodItemNutrientData foodItemNutrientData) {
        this.foodItemNutrientData = foodItemNutrientData;
    }

    /**
     * Updates the max calorie amount to keep track of as a point of comparison with the energy values of food items
     * Preconditions: The user must input the desired calorie amount
     * Postconditions: The max calorie count will be updated
     * @param maxCalorieAmount The inputted maximum calorie amount given by the user
     */
    @Override
    public void setMaxCalorieAmount(int maxCalorieAmount) {
        this.maxCalorieAmount = maxCalorieAmount;
    }

    /**
     * Gets the max calorie amount as a point of comparison with the energy values of food items
     * Preconditions: None
     * Postconditions: None
     * @return The maximum calorie amount set by the user
     */
    @Override
    public int getMaxCalorieAmount() {
        return maxCalorieAmount;
    }

    /**
     * Verifies whether the caloric value of the currently selected food item (taken from nutrition data) exceeds the max calorie count
     * Preconditions: None
     * Postconditions: None
     * @return True if the caloric value of the food item exceeds the max calorie amount, otherwise false
     */
    @Override
    public boolean checkFoodItemExceedsMaxCalories() {
        return foodItemExceedsMaxCalories;
    }

    /**
     * Updates whether the caloric value of the currently selected food item (taken from nutrition data) exceeds the max calorie count
     * Preconditions: The user must first select a food item and review its nutrition data
     * Postconditions: The status of whether the currently selected food item exceeds the max calorie count will be updated
     * @param status The status of whether the caloric value of the food item exceeds the max calorie count
     */
    @Override
    public void updateFoodItemExceedsMaxCalories(boolean status) {
        this.foodItemExceedsMaxCalories = status;
    }

    /**
     * Verifies whether a string is in valid JSON format or contains an error message
     * Preconditions: None
     * Postconditions: None
     * @param json The string to be checked
     * @return True if the String is in json format and without error contents, otherwise false
     */
    private boolean verifyErrorInAPIResponse(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("error")) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            return true;
        }
    }


}
