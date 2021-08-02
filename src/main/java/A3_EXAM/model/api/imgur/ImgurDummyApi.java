package A3_EXAM.model.api.imgur;

/**
 * A concrete implementation of the ImgurAPITemplate representing 'offline' access to the Imgur API.
 */
public class ImgurDummyApi extends ImgurApiTemplate {

    public ImgurDummyApi(String clientId, String bearerToken)  {
        super(clientId, bearerToken);
    }

    /**
     * Emulates the Imgur API call to upload a QR code of a string representing a nutrition report of a food item by returning
     * a fixed Imgur link value.
     * Preconditions: None
     * Postconditions: None
     * @param report The nutrition report data of a selected food item to be converted into a QR code and uploaded to imgur
     * @return A fixed Imgur link to an image of a QR code containing the nutrition report data of a food item
     */
    @Override
    public String uploadNutritionReportToImgur(String report) {
        return "https://i.imgur.com/tUbbJWe.png";
    }
}
