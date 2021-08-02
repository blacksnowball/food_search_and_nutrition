package A3_EXAM.model.parsers;

import A3_EXAM.model.dataobject.FoodItemNutrientData;

import java.util.List;

/**
 * Interface for a ReportParser. Represents a contract for generating report data about the nutrition of a food item.
 */
public interface ReportParser {

    /**
     * Prepares a report outlining the nutrition data of a food item. Some of the nutrition data may be given preference and ordered
     * according to a provided list of nutrient label data.
     * Preconditions: None
     * Postconditions: None
     * @param nutrientData A FoodItemNutrientData object upon which the report will be based
     * @param prioritisedNutrientData A list of nutrient label strings to be given preference to appear earlier in the report
     * @return A string containing the nutrition data of a food item formatted as a report
     */
    String prepareNutritionReportData(FoodItemNutrientData nutrientData, List<String> prioritisedNutrientData);
}
