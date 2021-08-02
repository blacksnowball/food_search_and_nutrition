package A3_EXAM.model.commands;

import A3_EXAM.model.facade.ModelFacade;
import A3_EXAM.model.dataobject.FoodItemNutrientData;

/**
 * Concrete implementation of a query extending a command. Encapsualtes a method call for generating a QR code
 * of the nutrition data of a food item and uploading it to Imgur.
 */
public class UploadNutritionReportToImgurQuery extends Query {

    /**
     * Instantiates a new Query object encapsulating a method call.
     * @param modelFacade The model facade to make calls on
     * @param sb The StringBuilder object used to inform what calls to make and to help with yielding some return values
     */
    public UploadNutritionReportToImgurQuery(ModelFacade modelFacade, StringBuilder sb) {
        super(modelFacade, sb);
    }

    /**
     * Generates a QR code of the nutrition report data of a food item which is then uploaded to Imgur and the resulting link
     * displayed to the user for access
     * Preconditions: None
     * Postconditions: The application will be updated to show the user an Imgur link to the QR code containing nutrition report data
     */
    @Override
    public void execute() {
        FoodItemNutrientData foodItemNutrientData = modelFacade.getNutrientDataOfFoodItem();
        String imgurLink = modelFacade.uploadNutritionReportAndGetURL(modelFacade.prepareNutritionReportData(foodItemNutrientData));
        sb.delete(0, sb.length());
        sb.append(imgurLink);
    }

}
