package A3_EXAM.model.parsers;

import A3_EXAM.model.dataobject.FoodItemData;
import A3_EXAM.model.dataobject.FoodItemNutrientData;

import java.util.List;

/**
 * Interface for a ReportParser. Represents a contract for parsing JSON strings and instantiating data objects from them.
 */
public interface JSONParser {

    /**
     * Processes the supplied string data to instantiate a list of FoodItemData objects representing
     * a list of matching food items for a given query without nutrition data stored.
     * Preconditions: None
     * Postconditions: None
     * @param jsonStr The resulting string returned from a call to the Edamam API (fresh or cached) for matching food items
     * @return A list of FoodItemData objects extracted from the supplied string
     * representing a list of matching food items for a given query.
     */
    List<FoodItemData> processFoodItemData(String jsonStr);

    /**
     * Processes the supplied string data to instantiate a FoodItemNutrientData object which represents a food item object
     * containing comprehensive details about its nutrition data.
     * Preconditions: None
     * Postconditions: None
     * @param jsonStr The resulting string returned from a call to the Edamam API (fresh or cached) for nutrition data on a food item
     * @return A FoodItemNutrientData object extracted from the supplied string representing the nutrition data of a food item.
     */
    FoodItemNutrientData processNutrientData(String jsonStr);

}
