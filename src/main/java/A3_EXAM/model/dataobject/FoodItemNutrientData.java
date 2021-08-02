package A3_EXAM.model.dataobject;

import java.util.List;

/**
 * This interface is a record of a type of object that stores the comprehensive nutrition data of a food item. It is composed of many single units of
 * NutrientData objects and also contains other relevant data.
 */
public interface FoodItemNutrientData extends BasicFoodData {

   /**
    * Gets the food contents of the food item
    * Preconditions: None
    * Postconditions: None
    * @return The food contents of the food item
    */
   String getFoodContents();

   /**
    * Sets food contents of the food item
    * Preconditions: None
    * Postconditions: The FoodItemNutrientData object will be updated to have the supplied food contents
    * @param foodContents The food contents of the food item
    */
   void setFoodContents(String foodContents);

   /**
    * Gets nutrient uri of the food item
    * Preconditions: None
    * Postconditions: None
    * @return The nutrient uri of the food item
    */
   String getNutrientUri();

   /**
    * Sets nutrient uri of the food item
    * Preconditions: None
    * Postconditions: The FoodItemNutrientData object will be updated to have the supplied nutrient uri
    * @param nutrientUri The nutrient uri of the food item
    */
   void setNutrientUri(String nutrientUri);

   /**
    * Gets weight of the food item measured in grams
    * Preconditions: None
    * Postconditions: None
    * @return the weight of the food item in grams
    */
   float getWeight();

   /**
    * Sets weight of food item in grams
    * Preconditions: None
    * Postconditions: The FoodItemNutrientData object will be updated to have the supplied weight
    * @param weight the weight measured in grams
    */
   void setWeight(float weight);

   /**
    * Gets cautions associated with the food item
    * Preconditions: None
    * Postconditions: None
    * @return the cautions associated with the food item
    */
   List<String> getCautions();

   /**
    * Sets cautions associated with the food item
    * Preconditions: None
    * Postconditions: The FoodItemNutrientData object will be updated to have the supplied cautions
    * @param cautions the cautions associated with the food item as a list of strings
    */
   void setCautions(List<String> cautions);

   /**
    * Gets the diet labels associated with the food item
    * Preconditions: None
    * Postconditions: None
    * @return the diet labels associated with the food item as a list of strings
    */
   List<String> getDietLabels();

   /**
    * Sets diet labels associated with the food item
    * Preconditions: None
    * Postconditions: The FoodItemNutrientData object will be updated to have the supplied diet labels
    * @param dietLabels the diet labels associated with the food items
    */
   void setDietLabels(List<String> dietLabels);

   /**
    * Gets the health labels associated with the food item
    * Preconditions: None
    * Postconditions: None
    * @return the health labels associated with the food item as a list of strings
    */
   List<String> getHealthLabels();

   /**
    * Sets health labels associated with the food item
    * Preconditions: None
    * Postconditions: The FoodItemNutrientData object will be updated to have the supplied health labels
    * @param healthLabels the health labels
    */
   void setHealthLabels(List<String> healthLabels);

   /**
    * Gets total nutrients associated with the food items, stored as a list of NutrientData objects
    * Preconditions: None
    * Postconditions: None
    * @return List of NutrientData objects representing the total nutrients associated with the food item
    */
   List<NutrientData> getTotalNutrients();

   /**
    * Sets the total nutrients associated with the food items as a list of NutrientData objects
    * Preconditions: None
    * Postconditions: The FoodItemNutrientData object will be updated to have the supplied total nutrients data
    * @param totalNutrients List of NutrientData objects representing the total nutrients associated with the food item
    */
   void setTotalNutrients(List<NutrientData> totalNutrients);

   /**
    * Gets daily nutrients associated with the food items, stored as a list of NutrientData objects
    * Preconditions: None
    * Postconditions: None
    * @return List of NutrientData objects representing the daily nutrients associated with the food item
    */
   List<NutrientData> getDailyNutrients();

   /**
    * Sets the daily nutrients associated with the food items as a list of NutrientData objects
    * Preconditions: None
    * Postconditions: The FoodItemNutrientData object will be updated to have the supplied daily nutrients data
    * @param dailyNutrients List of NutrientData objects representing the daily nutrients associated with the food item
    */
   void setDailyNutrients(List<NutrientData> dailyNutrients);

   /**
    * Gets glycemic index of the food item
    * Preconditions: None
    * Postconditions: None
    * @return the glycemic index of the food item
    */
   Integer getGlycemicIndex();

   /**
    * Sets glycemic index of the food item
    * Preconditions: None
    * Postconditions: The FoodItemNutrientData object will be updated to have the supplied glycemic index data
    * @param glycemicIndex the glycemic index of the food item
    */
   void setGlycemicIndex(Integer glycemicIndex);

}
