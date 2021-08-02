package A3_EXAM.model.api.edamam;

import A3_EXAM.model.api.requestclient.RequestClient;
import A3_EXAM.model.api.requestclient.RequestClientImpl;

/**
 * An abstract class acting as a template for the attributes and behaviour of a Edamam food database API and its calls.
 * This is to help separate between an API caller object that represents 'online' or 'offline' access mode and their different behaviour
 */
public abstract class FoodDatabaseApiTemplate {

    protected final String authDetails;
    protected RequestClient requestClientImpl;

    /**
     * Instantiates a new FoodDatabaseAPI object according to a template
     * @param appId  The application id
     * @param appKey The application key
     */
    public FoodDatabaseApiTemplate(String appId, String appKey) {
        authDetails = String.format("?app_id=%s&app_key=%s", appId, appKey);
        requestClientImpl = new RequestClientImpl();
    }

    /**
     * Makes a query to the Edamam food API for matching food items and returns the outcome as a string
     * Preconditions: The supplied query should not be empty
     * Postconditions: None
     * @param query The search term used in making a query for matching food items
     * @return A string containing the outcome of a query for matching items of a query (as a JSON string or error message)
     */
    public abstract String foodSearchRequest(String query);

    /**
     * Makes a query to the Edamam food API for the nutrition data of a food item and returns the outcome as a string
     * Preconditions: The measure uri or food id of a food item should not be empty
     * Postconditions: None
     * @param measureUri The measure uri of a food item to be searched, forming part of an API call
     * @param foodId The food id of a food item to be searched, forming part of an API call
     * @return A string containing the outcome of a query for the nutrition data of a food item (as a JSON string or error message)
     */
    public abstract String foodNutritionSearchRequest(String measureUri, String foodId);


}
