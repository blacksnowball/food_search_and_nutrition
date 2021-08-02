package A3_EXAM.model.dataobject;

/**
 * This interface is a record of a single unit of nutrition data that forms part of the comprehensive data about the
 * nutrition of a food item. It includes details about the label, quantity, and units - e.g. energy or calories as measured in units kcal with a
 * quantity of n
 */
public interface NutrientData {
    /**
     * Gets the nutrient label of the single unit of nutrition data.
     * Preconditions: None
     * Postconditions: None
     * @return The nutrient label of the single unit of nutrition data
     */
    String getNutrientLabel();

    /**
     * Sets the nutrient label of the single unit of nutrition data
     * Preconditions: None
     * Postconditions: None
     * @param nutrientLabel The nutrient label of the single unit of nutrition data
     */
    void setNutrientLabel(String nutrientLabel);

    /**
     * Gets the quantity of the single unit of nutrition data
     * Preconditions: None
     * Postconditions: None
     * @return The quantity of the single unit of nutrition data
     */
    float getQuantity();

    /**
     * Sets the quantity of the single unit of nutrition data
     * Preconditions: None
     * Postconditions: None
     * @param quantity The quantity of the single unit of nutrition data
     */
    void setQuantity(float quantity);

    /**
     * Gets unit type of the single unit of nutrition data
     * Preconditions: None
     * Postconditions: None
     * @return The unit type of the single unit of nutrition data
     */
    String getUnit();

    /**
     * Sets the unit type of the single unit of nutrition data
     * Preconditions: None
     * Postconditions: None
     * @param unit The unit type of the single unit of nutrition data
     */
    void setUnit(String unit);
}
