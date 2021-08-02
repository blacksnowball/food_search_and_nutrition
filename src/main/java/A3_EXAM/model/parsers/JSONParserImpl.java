package A3_EXAM.model.parsers;

import A3_EXAM.model.dataobject.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of the JSONParser interface. Provides functionality for instantiating model objects (FoodItemData, FoodNutrientItemData,
 * NutrientData) based on the strings of fresh and cached API calls.
 */
public class JSONParserImpl implements JSONParser {

    /**
     * Updates the provided FoodItemData object with data extracted during a JSON parsing process.
     * Preconditions: None
     * Postconditions: The list supplied will contain the data extracted from the JSONObject
     * @param fd The FoodItemData to be updated
     * @param foodId The food id of the food item
     * @param label The label of the food item
     * @param brand The brand of the food item
     * @param category The category of the food item
     * @param categoryLabel The category label of food item
     * @param yieldingSearchTerm The search term yielding the food item
     * @param measureUri The measure uri of of the food item
     * @param measure The measure of the food item
     * @param imageLink The image link of the food item
     * @param foodUri The food uri of the food item
     */
    private void setFoodData(FoodItemData fd, String foodId, String label, String brand,
                             String category, String categoryLabel, String yieldingSearchTerm, String measureUri,
                             String measure, String imageLink, String foodUri) {
        fd.setFoodId(foodId);
        fd.setFoodLabel(label);
        fd.setBrand(brand);
        fd.setCategory(category);
        fd.setCategoryLabel(categoryLabel);
        fd.setYieldingSearchTerm(yieldingSearchTerm);
        fd.setMeasureUri(measureUri);
        fd.setMeasure(measure);
        fd.setImageLink(imageLink);
        fd.setFoodUri(foodUri);
    }

    /**
     * Adds basic nutrition data to a supplied HashMap that will be stored in a FoodItem data object
     * Preconditions: None
     * Postconditions: The supplied HashMap will contain the basic nutrient data of the food item extracted from the JSONObject
     * @param basicNutrients The HashMap that will contain the basic nutrient data of the food item
     * @param nutrients The JSONObject containing data on the basic nutrients of a food item
     */
    private void insertNutrientData(Map<String, Float> basicNutrients, JSONObject nutrients) {

        JSONArray keys = nutrients.names();
        for (int i = 0; i < keys.length(); i++) {
            basicNutrients.put(keys.getString(i), nutrients.getFloat(keys.getString(i)));
        }

    }

    /**
     * Adds the different measures of a food item to a supplied list that will be stored in a FoodItem data object
     * Preconditions: None
     * Postconditions: The supplied list will contain the different measures extracted from a JSONArray
     * @param measuresList The list of string that will contain the different measures of the food item
     * @param measures The JSONObject containing data on the different measures of a food item
     */
    private void insertMeasures(List<String> measuresList, JSONArray measures) {

        for (int i = 0; i < measures.length(); i++) {
            JSONObject measure = measures.getJSONObject(i);
            measuresList.add(measure.getString("label"));
        }

    }

    /**
     * Processes the supplied string data to instantiate a list of FoodItemData objects representing
     * a list of matching food items for a given query without nutrition data stored.
     * Preconditions: None
     * Postconditions: None
     * @param jsonStr The resulting string returned from a call to the Edamam API (fresh or cached) for matching food items
     * @return A list of FoodItemData objects extracted from the supplied string
     * representing a list of matching food items for a given query.
     */
    public List<FoodItemData> processFoodItemData(String jsonStr) {

        List<FoodItemData> data = new ArrayList<>();

        try {

            JSONObject parser = new JSONObject(jsonStr);

            String searched = parser.getString("text");
            JSONArray hints = parser.getJSONArray("hints");

            for (int i = 0; i < hints.length(); i++) {

                JSONObject queryResult = hints.getJSONObject(i);
                JSONObject foodItem = queryResult.getJSONObject("food");

                JSONObject nutrients = foodItem.getJSONObject("nutrients");

                Map<String, Float> basicNutrients = new HashMap<>();
                insertNutrientData(basicNutrients, nutrients);

                String id = foodItem.getString("foodId");
                String label = foodItem.getString("label");
                String category = foodItem.getString("category");
                String categoryLabel = foodItem.getString("categoryLabel");


                String brand = null;
                if (foodItem.has("brand")) {
                    brand = foodItem.getString("brand");
                } else {
                    brand = "N/A";
                }

                String image = null;
                if (foodItem.has("image")) {
                    image = foodItem.getString("image");
                } else {
                    image = "N/A";
                }

                String foodUri = null;
                if (foodItem.has("uri")) {
                    foodUri = foodItem.getString("uri");
                } else {
                    foodUri = "N/A";
                }


                JSONArray measures = queryResult.getJSONArray("measures");
                JSONObject primaryMeasure = measures.getJSONObject(0);
                String measureUri = primaryMeasure.getString("uri");
                String measure = primaryMeasure.getString("label");

                List<String> measuresList = new ArrayList<>();
                insertMeasures(measuresList, measures);

                FoodItemData foodData = new FoodItemDataImpl();
                setFoodData(foodData, id, label, brand, category, categoryLabel, searched, measureUri, measure,
                        image, foodUri);
                foodData.setBasicNutrients(basicNutrients);
                foodData.setMeasures(measuresList);
                data.add(foodData);

            }

            return data;

        } catch (JSONException e) {
            return data;
        }


    }

    /**
     * Updates the provided NutrientData data object with data extracted during a JSON parsing process
     * Preconditions: None
     * Postconditions: The NutrientData object will contain the data supplied
     * @param nutrientData The nutrition data point.
     * @param label The label representing a nutrition data point.
     * @param qty The quantity representing a nutrition data point.
     * @param unit The unit representing a nutrition data point.
     */
    private void setNutrientData(NutrientData nutrientData, String label, float qty, String unit) {
        nutrientData.setNutrientLabel(label);
        nutrientData.setQuantity(qty);
        nutrientData.setUnit(unit);
    }

    /**
     * Updates the provided FoodItemNutrientData object with data extracted during a JSON parsing process.
     * Preconditions: None
     * Postconditions: The list supplied will contain the data extracted from the JSONObject
     * @param foodItem The FoodItemNutrientData object to be updated
     * @param foodId The foodID
     * @param foodlabel The name of the food item
     * @param measure The primary measure of the food item
     * @param weight The weight of the food item
     * @param nutrientUri The nutrient uri of the food item
     * @param foodURI The food uri of the food item
     * @param measureUri The measure uri of the primary measure
     * @param foodContents The ingredients of the food item
     * @param glycemicIndex The glycemic index of the food item
     */
    private void updateSelectedFoodItemNutrientData(FoodItemNutrientData foodItem, String foodId, String foodlabel,
                                                    String measure, float weight, String nutrientUri, String foodURI,
                                                    String measureUri, String foodContents, Integer glycemicIndex) {
        foodItem.setFoodId(foodId);
        foodItem.setFoodLabel(foodlabel);
        foodItem.setMeasure(measure);
        foodItem.setWeight(weight);
        foodItem.setNutrientUri(nutrientUri);
        foodItem.setFoodUri(foodURI);
        foodItem.setMeasureUri(measureUri);
        foodItem.setFoodContents(foodContents);
        foodItem.setGlycemicIndex(glycemicIndex);
    }

    /**
     * Processes the supplied string data to instantiate a FoodItemNutrientData object which represents a food item object
     * containing comprehensive details about its nutrition data.
     * Preconditions: None
     * Postconditions: None
     * @param jsonStr The resulting string returned from a call to the Edamam API (fresh or cached) for nutrition data on a food item
     * @return A FoodItemNutrientData object extracted from the supplied string representing the nutrition data of a food item.
     */
    public FoodItemNutrientData processNutrientData(String jsonStr) {

        FoodItemNutrientData foodItemNutrientData = new FoodItemNutrientDataImpl();

        try {

            JSONObject parser = new JSONObject(jsonStr);

            float totalWeight = parser.getFloat("totalWeight");
            String nutrientUri = parser.getString("uri");
            JSONArray ingredients = parser.getJSONArray("ingredients");
            JSONObject ingredientsFirstLevel = ingredients.getJSONObject(0);
            JSONArray parsed = ingredientsFirstLevel.getJSONArray("parsed");
            JSONObject ingredientData = parsed.getJSONObject(0);

            String foodLabel = ingredientData.getString("food");
            String foodId = ingredientData.getString("foodId");
            String measure = ingredientData.getString("measure");

            String foodUri = null;
            if (ingredientData.has("foodURI")) {
                foodUri = ingredientData.getString("foodURI");
            }

            String measureUri = null;
            if (ingredientData.has("measureURI")) {
                measureUri = ingredientData.getString("measureURI");
            }

            Integer glycemicIndex = null;
            if (parser.has("glycemicIndex")) {
                glycemicIndex = parser.getInt("glycemicIndex");
            }

            String foodContentsLabel = null;
            if (ingredientData.has("foodContentsLabel")) {
                foodContentsLabel = ingredientData.getString("foodContentsLabel");
            }

            updateSelectedFoodItemNutrientData(foodItemNutrientData, foodId, foodLabel, measure, totalWeight,
                    nutrientUri, foodUri, measureUri, foodContentsLabel, glycemicIndex);

            List<NutrientData> totalNutrientsList = new ArrayList<>();
            List<NutrientData> dailyNutrientsList = new ArrayList<>();
            List<String> dietLabelsList = new ArrayList<>();
            List<String> healthLabelsList = new ArrayList<>();
            List<String> cautionsList = new ArrayList<>();


            // extract data for nutrients
            JSONObject totalNutrients = parser.getJSONObject("totalNutrients");
            createNutrientDataObjects(totalNutrientsList, totalNutrients);

            JSONObject totalDaily = parser.getJSONObject("totalDaily");
            createNutrientDataObjects(dailyNutrientsList, totalDaily);

            JSONArray dietLabels = parser.getJSONArray("dietLabels");
            JSONArray healthLabels = parser.getJSONArray("healthLabels");
            JSONArray cautions = parser.getJSONArray("cautions");

            createLabelList(dietLabelsList, dietLabels);
            createLabelList(healthLabelsList, healthLabels);
            createLabelList(cautionsList, cautions);

            foodItemNutrientData.setTotalNutrients(totalNutrientsList);
            foodItemNutrientData.setDailyNutrients(dailyNutrientsList);
            foodItemNutrientData.setDietLabels(dietLabelsList);
            foodItemNutrientData.setHealthLabels(healthLabelsList);
            foodItemNutrientData.setCautions(cautionsList);

            return foodItemNutrientData;

        } catch (JSONException e) {
            return foodItemNutrientData;
        }

    }


    /**
     * Adds nutrient labels (diet labels, health labels, and cautions) to a supplied string list as extracted from a JSONArray
     * Preconditions: None
     * Postconditions: The list supplied list will contain the nutrient labels extracted from the JSONArray
     * @param labelList A list to hold the labels of a food item
     * @param labelArr The JSONArray containing nutrition data of a food item
     */
    private void createLabelList(List<String> labelList, JSONArray labelArr) {
        for (int i = 0; i < labelArr.length(); i++) {
            labelList.add(labelArr.getString(i));
        }
    }

    /**
     * Initialise a set of NutrientData objects representing nutrition information from a JSONObject containing the data. Adds these objects to a
     * supplied NutrientData list
     * Preconditions: None
     * Postconditions: The list supplied will contain the data extracted from the JSONObject
     * @param nutrientsList A list to hold the NutrientData objects that will be generated after extraction from the JSONObject
     * @param nutrientsObj The JSONObject containing nutrition data of a food item
     */
    private void createNutrientDataObjects(List<NutrientData> nutrientsList, JSONObject nutrientsObj) {
        JSONArray totalNutrientsArr = nutrientsObj.toJSONArray(nutrientsObj.names());
        for (int i = 0; i < totalNutrientsArr.length(); i++) {
            JSONObject singularNutrientData = totalNutrientsArr.getJSONObject(i);
            String label = singularNutrientData.getString("label");
            float qty = singularNutrientData.getFloat("quantity");
            String unit = singularNutrientData.getString("unit");

            NutrientData currentNutrientData = new NutrientDataImpl();
            setNutrientData(currentNutrientData, label, qty, unit);
            nutrientsList.add(currentNutrientData);
        }
    }


}
