package A3_EXAM.model.dataobject;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of the FoodItemData interface. Represents a model object containing the general information of a food item including
 * an overview of a nutrient data. This object will form the basis of what is displayed to the user when returning a list of matching food items
 * based on some text search query
 */
public class FoodItemDataImpl extends BasicFoodDataEntity implements FoodItemData {

    private DecimalFormat df = new DecimalFormat("#.##");
    private String brand;
    private String category;
    private String categoryLabel;
    private String yieldingSearchTerm;
    private String imageLink;
    private Map<String, Float> basicNutrients;
    private List<String> measures;

    /**
     * Gets image link of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the image link of the food item
     */
    @Override
    public String getImageLink() {
        return imageLink;
    }

    /**
     * Sets image link of the food item
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied image link
     * @param imageLink the image link of the food item
     */
    @Override
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    /**
     * Gets basic nutrients of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the basic nutrients of the food item including their quantities
     */
    @Override
    public Map<String, Float> getBasicNutrients() {
        return basicNutrients;
    }

    /**
     * Sets basic nutrients of the food item including their quantities
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied image link
     * @param basicNutrients the basic nutrients of the food item including their quantities
     */
    @Override
    public void setBasicNutrients(Map<String, Float> basicNutrients) {
        this.basicNutrients = basicNutrients;
    }

    /**
     * Gets the different measures available for the food item
     * Preconditions: None
     * Postconditions: None
     * @return the measures of the food item as a list of strings
     */
    @Override
    public List<String> getMeasures() {
        return measures;
    }

    /**
     * Sets the different measures available for the food item
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied measures
     * @param measures the measures of the food item as a list of strings
     */
    @Override
    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }

    /**
     * Gets category label of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the category label of the food item
     */
    @Override
    public String getCategoryLabel() {
        return categoryLabel;
    }

    /**
     * Sets category label.
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied measures
     * @param categoryLabel the category label of the food item
     */
    @Override
    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    /**
     * Gets brand of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the brand of the food item
     */
    @Override
    public String getBrand() {
        return brand;
    }

    /**
     * Sets brand of the food item
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied brand
     * @param brand the brand of the food item
     */
    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets category of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the category of the food item
     */
    @Override
    public String getCategory() {
        return category;
    }

    /**
     * Sets category of the food item
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied brand
     * @param category the category of the food item
     */
    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets yielding search term of the food item
     * Preconditions: None
     * Postconditions: None
     * @return the yielding search term of the food item
     */
    @Override
    public String getYieldingSearchTerm() {
        return yieldingSearchTerm;
    }

    /**
     * Sets yielding search term of the food item
     * Preconditions: None
     * Postconditions: The FoodItemData object will be updated to have the supplied brand
     * @param yieldingSearchTerm the yielding search term of the food item
     */
    @Override
    public void setYieldingSearchTerm(String yieldingSearchTerm) {
        this.yieldingSearchTerm = yieldingSearchTerm;
    }


    /**
     * Gets basic details of the food item as part of matching food items.
     * Precondition: None
     * Postcondition: None
     * @return A formatted string containing basic details about the food item to be displayed to the user as part of
     * a list of matching food results.
     */
    @Override
    public String displayUponMatchingResult() {

        if (brand == null || brand.equals("N/A")) {
            return String.format("%s (%s)", foodLabel, category);
        } else {
            return String.format("%s (%s by %s)", foodLabel, category, brand);
        }

    }

    /**
     * Gets comprehensive details of the food item upon being selected in the GUI.
     * Precondition: None
     * Postcondition: None
     * @return A formatted string containing comprehensive details about the food item to be displayed when selected
     * by the user from a list of matching results.
     */
    @Override
    public String displayUponBeingSelected() {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Information about '%s'\n", foodLabel));
        sb.append(String.format("Brand: %s\n", brand));
        sb.append(String.format("Category: %s\n", category));
        sb.append(String.format("Item type: %s\n", StringUtils.capitalize(categoryLabel)));
        sb.append(String.format("Measure(s): %s\n", StringUtils.join(measures, ", ")));
        sb.append(String.format("Food ID: %s\n", foodId));
        sb.append(String.format("Food URI: %s\n", foodUri));
        sb.append(String.format("Image link: %s\n", imageLink));
        sb.append(String.format("Yielding search term: %s\n", yieldingSearchTerm));
        sb.append(formatBasicNutrients());

        return sb.toString();

    }

    /**
     * Prepares a string of the nutrient overview of the food item including their quantities and units
     * Precondition: None
     * Postcondition: None
     * @return A formatted string containing an overview of the nutrients of the food item
     */
    private String formatBasicNutrients() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nutrition overview:\n");

        if (basicNutrients.containsKey("ENERC_KCAL")) {
            sb.append(String.format("\tEnergy: %s kcal\n", df.format(basicNutrients.get("ENERC_KCAL"))));
        }

        if (basicNutrients.containsKey("PROCNT")) {
            sb.append(String.format("\tProtein: %s mg\n", df.format(basicNutrients.get("PROCNT"))));
        }

        if (basicNutrients.containsKey("FAT")) {
            sb.append(String.format("\tFat: %s g\n", df.format(basicNutrients.get("FAT"))));
        }

        if (basicNutrients.containsKey("CHOCDF")) {
            sb.append(String.format("\tCarbs: %s g\n", df.format(basicNutrients.get("CHOCDF"))));
        }

        if (basicNutrients.containsKey("FIBTG")) {
            sb.append(String.format("\tFiber: %s g\n", df.format(basicNutrients.get("FIBTG"))));
        }


        if (sb.toString().equals("Nutrition overview:\n")) {
            sb.delete(0, sb.length());
            sb.append("Nutrition overview: N/A\n");
        }

        return sb.toString();
    }

}
