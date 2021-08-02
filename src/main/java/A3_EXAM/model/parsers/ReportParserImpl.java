package A3_EXAM.model.parsers;

import A3_EXAM.model.dataobject.FoodItemNutrientData;
import A3_EXAM.model.dataobject.NutrientData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the ReportParser interface. Provides report generation functionality for the application
 */
public class ReportParserImpl implements ReportParser {

    private DecimalFormat df;

    /**
     * Instantiates a new ReportParserImpl object.
     */
    public ReportParserImpl() {
        df = new DecimalFormat("#.##");
    }

    /**
     * Prepares a report outlining the nutrition data of a food item. Some of the nutrition data may be given preference and ordered
     * according to a provided list of nutrient label data.
     * Preconditions: None
     * Postconditions: None
     * @param nutrientData A FoodItemNutrientData object upon which the report will be based
     * @param prioritisedNutrientData A list of nutrient label strings to be given preference to appear earlier in the report
     * @return A string containing the nutrition data of a food item formatted as a report
     */
    public String prepareNutritionReportData(FoodItemNutrientData nutrientData, List<String> prioritisedNutrientData)  {
        StringBuilder sb = new StringBuilder();

        // header
        sb.append(String.format("Nutritional facts for '%s'\n\nAmount per %s (Weight of %s g)\n\n",
                WordUtils.capitalizeFully(nutrientData.getFoodLabel()),
                nutrientData.getMeasure(), df.format(nutrientData.getWeight())));

        // we want to order the report such that it first contains energy, protein, fat (total and saturated), carbs
        // (total and sugar), fibre, sodium, and the rest may be randomised
        List<NutrientData> totalNutrients = nutrientData.getTotalNutrients();
        List<NutrientData> dailyNutrients = nutrientData.getDailyNutrients();

        if (totalNutrients.isEmpty()) {
            sb.append("***Total nutrients***\nN/A");
            sb.append("\n\n");
        } else {
            sb.append("***Total nutrients***\n");
            getNutrientStrings(prioritisedNutrientData, sb, totalNutrients);
            sb.append("\n");
        }


        if (dailyNutrients.isEmpty()) {
            sb.append("***Daily nutrients***\nN/A");
            sb.append("\n\n");
        } else {
            sb.append("***Daily nutrients***\n");
            getNutrientStrings(prioritisedNutrientData, sb, dailyNutrients);
            sb.append("\n");
        }

        // now go through labels for diet, health, and caution
        List<String> cautions = nutrientData.getCautions();
        List<String> healthLabels = nutrientData.getHealthLabels();
        List<String> dietLabels = nutrientData.getDietLabels();


        if (cautions.isEmpty()) {
            sb.append("***Cautions***\nN/A");
        } else {
            sb.append("***Cautions***\n");
            List<String> temp = new ArrayList<>();
            cautions.forEach(caution -> temp.add(StringUtils.capitalize(caution.toLowerCase())));
            sb.append(StringUtils.join(temp, ", "));
        }

        sb.append("\n\n");


        if (healthLabels.isEmpty()) {
            sb.append("***Health labels***\nN/A");
        } else {
            sb.append("***Health labels***\n");
            getDietOrHealthLabels(sb, healthLabels);
        }

        sb.append("\n\n");

        if (dietLabels.isEmpty()) {
            sb.append("***Diet labels***\nN/A");
        } else {
            sb.append("***Diet labels***\n");
            getDietOrHealthLabels(sb, dietLabels);
        }

        sb.append("\n\n");

        // ingredients
        String foodContents = nutrientData.getFoodContents();
        if (foodContents == null || foodContents.equals("null") || foodContents.equalsIgnoreCase("n/a")) {
            sb.append(String.format("***Ingredients***\nN/A"));
        } else {
            sb.append(String.format("***Ingredients***\n%s", WordUtils.wrap(WordUtils.capitalizeFully(foodContents), 75, "\n", false)));
        }

        sb.append("\n\n***Miscellaneous***\n");

        sb.append(String.format("Food ID: %s\n", nutrientData.getFoodId()));
        sb.append(String.format("Nutrition URI: %s\n", nutrientData.getNutrientUri()));

        if (nutrientData.getMeasure() == null) {
            sb.append(String.format("Measure URI: N/A\n"));
        } else {
            sb.append(String.format("Measure URI: %s\n", nutrientData.getMeasureUri()));
        }

        if (nutrientData.getFoodUri() == null) {
            sb.append(String.format("Food URI: N/A\n"));
        } else {
            sb.append(String.format("Food URI: %s\n", nutrientData.getFoodUri()));
        }

        // glycemic index
        Integer glycemicIndex = nutrientData.getGlycemicIndex();
        if (glycemicIndex == null) {
            sb.append("Glycemic index: N/A");
        } else {
            sb.append("Glycemic index: ").append(glycemicIndex);
        }

        return sb.toString();

    }

    /**
     * Handles the formatting and addition of health labels, diet labels, and cautions (as strings) of
     * a food item into the nutrition report
     * Preconditions: None
     * Postconditions: The StringBuilder object will contain information about the health labels, diet labels, or cautions
     * @param sb The StringBuilder object that holds the report data
     * @param labels List of strings containing data of either the health labels, diet labels, or cautions
     */
    private void getDietOrHealthLabels(StringBuilder sb, List<String> labels) {
        List<String> temp = new ArrayList<>();
        for (String label : labels) {
            label = label.replace("_", " ");
            label = StringUtils.capitalize(label.toLowerCase());
            temp.add(label);
        }
        String s = StringUtils.join(temp, ", ");
        sb.append(WordUtils.wrap(s, 75, "\n", false));
    }


    /**
     * Handles the formatting and addition of total nutrients or daily nutrients of a food item into the nutrition report
     * Preconditions: None
     * Postconditions: The StringBuilder object will contain information about the daily or total nutrients
     * @param prioritisedNutrientData A list of nutrient label strings to be given preference to appear earlier in the report
     * @param sb The StringBuilder object that holds the report data
     * @param nutrientList List of NutrientData objects containing data for the daily or total nutrients
     */
    private void getNutrientStrings(List<String> prioritisedNutrientData, StringBuilder sb, List<NutrientData> nutrientList) {

        List<String> nutrientStr = new ArrayList<>();
        nutrientList.forEach(x -> nutrientStr.add(x.getNutrientLabel()));

        for (String desiredNutrient : prioritisedNutrientData) {

            int index = nutrientStr.indexOf(desiredNutrient);

            if (index >= 0) {
                NutrientData nd = nutrientList.get(index);

                if (nd.getNutrientLabel().equals("Fat")) {
                    sb.append("Fat\n");
                    sb.append(String.format("\tTotal: %s %s\n", df.format(nd.getQuantity()), nd.getUnit()));
                } else if (nd.getNutrientLabel().equals("Saturated")) {
                    sb.append(String.format("\tSaturated: %s %s\n", df.format(nd.getQuantity()), nd.getUnit()));
                } else if (nd.getNutrientLabel().equals("Carbs")) {
                    sb.append("Carbohydrate\n");
                    sb.append(String.format("\tTotal: %s %s\n", df.format(nd.getQuantity()), nd.getUnit()));
                } else if (nd.getNutrientLabel().equals("Sugars")) {
                    sb.append(String.format("\tSugars: %s %s\n", df.format(nd.getQuantity()), nd.getUnit()));
                } else {
                    sb.append(String.format("%s: %s %s\n", nd.getNutrientLabel(), df.format(nd.getQuantity()), nd.getUnit()));
                }
            }

        }

        // get remaining nutritional data
        for (NutrientData nd : nutrientList) {
            if (!prioritisedNutrientData.contains(nd.getNutrientLabel())) {
                sb.append(String.format("%s: %s %s\n", nd.getNutrientLabel(), df.format(nd.getQuantity()), nd.getUnit()));
            }
        }
    }


}
