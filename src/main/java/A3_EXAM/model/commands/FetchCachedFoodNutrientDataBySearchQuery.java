package A3_EXAM.model.commands;

import A3_EXAM.model.facade.ModelFacade;
import A3_EXAM.model.dataobject.FoodItemData;
import A3_EXAM.model.dataobject.FoodItemNutrientData;


/**
 * Concrete implementation of a query extending a command. Encapsualtes a method call for fetching cached data
 * about the nutrition data of a food item based on it being reached through a search term.
 */
public class FetchCachedFoodNutrientDataBySearchQuery extends Query {

    /**
     * Instantiates a new Query object encapsulating a method call.
     * @param modelFacade The model facade to make calls on
     * @param sb The StringBuilder object used to inform what calls to make and to help with yielding some return values
     */
    public FetchCachedFoodNutrientDataBySearchQuery(ModelFacade modelFacade, StringBuilder sb) {
        super(modelFacade, sb);
    }

    /**
     * Gets the nutrition data of a cached food item based on a search term provided by a user to be used for interaction in the GUI
     * Preconditions: None
     * Postconditions: The nutrition data of a food item will be processed and made available for a user to interact with
     */
    @Override
    public void execute() {
        String query = sb.toString();
        FoodItemNutrientData nutritionData = modelFacade.fetchFoodItemNutrientDataBySearch(query);
        modelFacade.setNutrientDataOfFoodItem(nutritionData);
        FoodItemData foodItem = modelFacade.fetchFoodItemDataAssociatedWithNutritionDataSearch(query);
        modelFacade.getFoodItems().add(foodItem);
        String nutritionReportData = modelFacade.prepareNutritionReportData(nutritionData);
        sb.delete(0, sb.length());
        sb.append(nutritionReportData);
    }
}
