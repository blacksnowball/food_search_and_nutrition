package A3_EXAM.model.commands;

import A3_EXAM.model.facade.ModelFacade;
import A3_EXAM.model.dataobject.FoodItemData;
import A3_EXAM.model.dataobject.FoodItemNutrientData;

/**
 * Concrete implementation of a query extending a command. Encapsualtes a method call for getting matching food nutrition
 * information for a food item based on selection from a list of matching items.
 */
public class FoodItemNutrientSearchQuery extends Query {

    private FoodItemData foodItem;

    /**
     * Instantiates a new Query object encapsulating a method call.
     * @param modelFacade The model facade to make calls on
     * @param sb The StringBuilder object used to inform what calls to make and to help with yielding some return values
     * @param foodItemData The FooodItemData object that will be used to inform method calls to get nutrition data and cache it
     */
    public FoodItemNutrientSearchQuery(ModelFacade modelFacade, StringBuilder sb, FoodItemData foodItemData) {
        super(modelFacade, sb);
        this.foodItem = foodItemData;
    }

    /**
     * Gets nutrition information of a selected food item from an API call. The report data associated with it will then be processed to
     * be visible to the user and uploaded to Imgur as a QR code.
     * Preconditions: None
     * Postconditions: The nutrition data of a food item will be processed and made available for a user to interact with
     */
    @Override
    public void execute() {
        String apiResponse = modelFacade.foodNutritionSearchRequest(foodItem.getMeasureUri(), foodItem.getFoodId());
        FoodItemNutrientData nutrientData = modelFacade.getFoodItemNutrientData(apiResponse);
        modelFacade.setNutrientDataOfFoodItem(nutrientData);
        sb.delete(0, sb.length());

        if (apiResponse.contains("{")) {
            String nutritionReportData = modelFacade.prepareNutritionReportData(nutrientData);
            sb.append(nutritionReportData);
            modelFacade.cacheSelectedItemNutrientData(foodItem.getYieldingSearchTerm(), foodItem.getFoodId(), foodItem.getFoodLabel(), apiResponse);
        } else {
            sb.append(apiResponse);
        }


    }

}