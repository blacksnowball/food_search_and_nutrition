package A3_EXAM.model.commands;
import A3_EXAM.model.dataobject.FoodItemNutrientData;
import A3_EXAM.model.dataobject.NutrientData;
import A3_EXAM.model.facade.ModelFacade;

/**
 * Concrete implementation of a query extending a command. Encapsualtes a method call for comparing the caloric or energy value
 * of the current food item based on its nutrient data, with the max calorie value set by the user
 */
public class CompareItemEnergyWithMaxCaloriesQuery extends Query {

    /**
     * Instantiates a new Query object encapsulating a method call.
     * @param modelFacade The model facade to make calls on
     * @param sb The StringBuilder object used to inform what calls to make and to help with yielding some return values
     */
    public CompareItemEnergyWithMaxCaloriesQuery(ModelFacade modelFacade, StringBuilder sb) {
        super(modelFacade, sb);
    }


    /**
     * Updates whether the caloric value of the currently selected food item (taken from nutrition data) exceeds the max calorie count such that
     * the GUI can respond by raising an alert to the user
     * Preconditions: The user must first select a food item and review its nutrition data
     * Postconditions: The status of whether the currently selected food item exceeds the max calorie count will be updated
     */
    @Override
    public void execute() {
        FoodItemNutrientData foodItemNutrientData = modelFacade.getNutrientDataOfFoodItem();
        modelFacade.setNutrientDataOfFoodItem(foodItemNutrientData);
        int maxCalories = modelFacade.getMaxCalorieAmount();

        float foodItemCalories = 0;
        for (NutrientData nd : foodItemNutrientData.getTotalNutrients()) {
            if (nd.getNutrientLabel().equalsIgnoreCase("energy")) {
                foodItemCalories = nd.getQuantity();
            }
        }

        modelFacade.updateFoodItemExceedsMaxCalories(foodItemCalories > maxCalories);
    }

}
