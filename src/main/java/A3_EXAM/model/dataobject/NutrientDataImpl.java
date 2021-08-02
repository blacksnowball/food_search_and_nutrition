package A3_EXAM.model.dataobject;

/**
 * Concrete implementation of the NutrientData interface. Represents a model object as a single unit of nutrition data
 * of a food item as part of the nutritional data it might have.
 */
public class NutrientDataImpl implements NutrientData {

    private String nutrientLabel;
    private float quantity;
    private String unit;

    /**
     * Gets the nutrient label of the single unit of nutrition data.
     * Preconditions: None
     * Postconditions: None
     * @return The nutrient label of the single unit of nutrition data
     */
    @Override
    public String getNutrientLabel() {
        return nutrientLabel;
    }

    /**
     * Sets the nutrient label of the single unit of nutrition data
     * Preconditions: None
     * Postconditions: None
     * @param nutrientLabel The nutrient label of the single unit of nutrition data
     */
    @Override
    public void setNutrientLabel(String nutrientLabel) {
        this.nutrientLabel = nutrientLabel;
    }

    /**
     * Gets the quantity of the single unit of nutrition data
     * Preconditions: None
     * Postconditions: None
     * @return The quantity of the single unit of nutrition data
     */
    @Override
    public float getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the single unit of nutrition data
     * Preconditions: None
     * Postconditions: None
     * @param quantity The quantity of the single unit of nutrition data
     */
    @Override
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets unit type of the single unit of nutrition data
     * Preconditions: None
     * Postconditions: None
     * @return The unit type of the single unit of nutrition data
     */
    @Override
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit type of the single unit of nutrition data
     * Preconditions: None
     * Postconditions: None
     * @param unit The unit type of the single unit of nutrition data
     */
    @Override
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
