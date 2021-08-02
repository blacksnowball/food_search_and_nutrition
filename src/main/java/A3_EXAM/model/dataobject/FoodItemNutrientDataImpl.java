package A3_EXAM.model.dataobject;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the FoodItemNutrientData interface. Represents a model object containing the
 * detailed nutrition data of a food item and forms the basis of what is needed when a user requests nutrition report data
 * for a food item.
 */
public class FoodItemNutrientDataImpl extends BasicFoodDataEntity implements FoodItemNutrientData {


    private float weight;
    private String nutrientUri;
    private String foodContents;
    private List<String> dietLabels;
    private List<String> healthLabels;
    private List<String> cautions;
    private Integer glycemicIndex;
    private List<NutrientData> totalNutrients;
    private List<NutrientData> dailyNutrients;

    public FoodItemNutrientDataImpl() {
        dietLabels = new ArrayList<>();
        healthLabels = new ArrayList<>();
        cautions = new ArrayList<>();
        totalNutrients = new ArrayList<>();
        dailyNutrients = new ArrayList<>();
    }

    /**
     * Gets the food contents of the food item
     * Preconditions: None
     * Postconditions: None
     * @return The food contents of the food item
     */
    @Override
    public String getFoodContents() {
        return foodContents;
    }

    /**
     * Sets food contents of the food item
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied food contents
     * @param foodContents The food contents of the food item
     */
    @Override
    public void setFoodContents(String foodContents) {
        this.foodContents = foodContents;
    }

    /**
     * Gets nutrient uri of the food item
     * Preconditions: None
     * Postconditions: None
     * @return The nutrient uri of the food item
     */
    @Override
    public String getNutrientUri() {
        return nutrientUri;
    }

    /**
     * Sets nutrient uri of the food item
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied nutrient uri
     * @param nutrientUri The nutrient uri of the food item
     */
    @Override
    public void setNutrientUri(String nutrientUri) {
        this.nutrientUri = nutrientUri;
    }


    /**
     * Gets weight of the food item measured in grams
     * Preconditions: None
     * Postconditions: None
     * @return the weight of the food item in grams
     */
    @Override
    public float getWeight() {
        return weight;
    }

    /**
     * Sets weight of food item in grams
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied weight
     * @param weight the weight measured in grams
     */
    @Override
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * Gets cautions associated with the food item
     * Preconditions: None
     * Postconditions: None
     * @return the cautions associated with the food item
     */
    @Override
    public List<String> getCautions() {
        return cautions;
    }

    /**
     * Sets cautions associated with the food item
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied cautions
     * @param cautions the cautions associated with the food item as a list of strings
     */
    @Override
    public void setCautions(List<String> cautions) {
        this.cautions = cautions;
    }

    /**
     * Gets the diet labels associated with the food item
     * Preconditions: None
     * Postconditions: None
     * @return the diet labels associated with the food item as a list of strings
     */
    @Override
    public List<String> getDietLabels() {
        return dietLabels;
    }

    /**
     * Sets diet labels associated with the food item
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied diet labels
     * @param dietLabels the diet labels associated with the food items
     */
    @Override
    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    /**
     * Gets the health labels associated with the food item
     * Preconditions: None
     * Postconditions: None
     * @return the health labels associated with the food item as a list of strings
     */
    @Override
    public List<String> getHealthLabels() {
        return healthLabels;
    }

    /**
     * Sets health labels associated with the food item
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied health labels
     * @param healthLabels the health labels
     */
    @Override
    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    /**
     * Gets total nutrients associated with the food items, stored as a list of NutrientData objects
     * Preconditions: None
     * Postconditions: None
     * @return List of NutrientData objects representing the total nutrients associated with the food item
     */
    @Override
    public List<NutrientData> getTotalNutrients() {
        return totalNutrients;
    }

    /**
     * Sets the total nutrients associated with the food items as a list of NutrientData objects
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied total nutrients data
     * @param totalNutrients List of NutrientData objects representing the total nutrients associated with the food item
     */
    @Override
    public void setTotalNutrients(List<NutrientData> totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    /**
     * Gets daily nutrients associated with the food items, stored as a list of NutrientData objects
     * Preconditions: None
     * Postconditions: None
     * @return List of NutrientData objects representing the daily nutrients associated with the food item
     */
    @Override
    public List<NutrientData> getDailyNutrients() {
        return dailyNutrients;
    }

    /**
     * Sets the daily nutrients associated with the food items as a list of NutrientData objects
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied daily nutrients data
     * @param dailyNutrients List of NutrientData objects representing the daily nutrients associated with the food item
     */
    @Override
    public void setDailyNutrients(List<NutrientData> dailyNutrients) {
        this.dailyNutrients = dailyNutrients;
    }

    /**
     * Gets glycemic index of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the glycemic index of the food item
     */
    @Override
    public Integer getGlycemicIndex() {
        return glycemicIndex;
    }

    /**
     * Sets glycemic index of the food item
     * Preconditions: None
     * Postconditions: The FoodItemNutrientData object will be updated to have the supplied glycemic index data
     * @param glycemicIndex the glycemic index of the food item
     */
    @Override
    public void setGlycemicIndex(Integer glycemicIndex) {
        this.glycemicIndex = glycemicIndex;
    }


}