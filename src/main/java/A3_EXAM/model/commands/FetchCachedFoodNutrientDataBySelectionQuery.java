package A3_EXAM.model.commands;

import A3_EXAM.model.facade.ModelFacade;
import A3_EXAM.model.dataobject.FoodItemData;
import A3_EXAM.model.dataobject.FoodItemNutrientData;

/**
 * Concrete implementation of a query extending a command. Encapsualtes a method call for fetching cached data
 * about the nutrition data of a food item based on it being reached through selection among a list of matching items.
 */
public class FetchCachedFoodNutrientDataBySelectionQuery extends Query {

    private FoodItemData foodItem;

    /**
     * Instantiates a new Query object encapsulating a method call.
     * @param modelFacade The model facade to make calls on
     * @param sb The StringBuilder object used to inform what calls to make and to help with yielding some return values
     * @param foodItemData The FoodItemData object which will be filled with cached nutrition data
     */
    public FetchCachedFoodNutrientDataBySelectionQuery(ModelFacade modelFacade, StringBuilder sb, FoodItemData foodItemData) {
        super(modelFacade, sb);
        this.foodItem = foodItemData;
    }

    /**
     * Gets the nutrition data of a cached food item based on selection of a food item among a list of matching items shown to the user
     * Preconditions: None
     * Postconditions: The nutrition data of a food item will be processed and made available for a user to interact with
     */
    @Override
    public void execute() {
        FoodItemNutrientData fd = modelFacade.fetchFoodItemNutrientDataBySelection(foodItem.getFoodId(), foodItem.getFoodLabel());
        modelFacade.setNutrientDataOfFoodItem(fd);
        String nutritionReportData = modelFacade.prepareNutritionReportData(fd);
        sb.delete(0, sb.length());
        sb.append(nutritionReportData);
    }
}
