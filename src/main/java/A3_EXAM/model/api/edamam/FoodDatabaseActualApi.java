package A3_EXAM.model.api.edamam;

/**
 * A concrete implementation of the FoodDatabseAPITemplate representing 'online' access
 * to the Edamam food API.
 */
public class FoodDatabaseActualApi extends FoodDatabaseApiTemplate {


    public FoodDatabaseActualApi(String appId, String appKey) {
        super(appId, appKey);
    }

    /**
     * Makes a query to the Edamam food API for matching food items and returns the outcome as a string
     * Preconditions: The supplied query should not be empty
     * Postconditions: None
     * @param query The search term used in making a query for matching food items
     * @return A string containing the outcome of a query for matching items of a query (as a JSON string or error message)
     */
    @Override
    public String foodSearchRequest(String query) {
        query = query.replace(" ", "%20");
        String uri = String.format("https://api.edamam.com/api/food-database/parser%s&ingr=%s", authDetails, query);
        return requestClientImpl.sendGetRequest(uri);
    }

    /**
     * Makes a query to the Edamam food API for the nutrition data of a food item and returns the outcome as a string
     * Preconditions: The measure uri or food id of a food item should not be empty
     * Postconditions: None
     * @param measureUri The measure uri of a food item to be searched, forming part of an API call
     * @param foodId The food id of a food item to be searched, forming part of an API call
     * @return A string containing the outcome of a query for the nutrition data of a food item (as a JSON string or error message)
     */
    @Override
    public String foodNutritionSearchRequest(String measureUri, String foodId) {
        String jsonStr = String.format("{\n" +
                "  \"ingredients\": [\n" +
                "    {\n" +
                "      \"quantity\": 1,\n" +
                "      \"measureURI\": \"%s\",\n" +
                "      \"foodId\": \"%s\"\n" +
                "    }\n" +
                "  ]\n" +
                "}", measureUri, foodId);
        String uri = String.format("https://api.edamam.com/api/food-database/v2/nutrients%s", authDetails);
        return requestClientImpl.sendPostRequestWithJsonFile(uri, jsonStr);
    }

}
