package A3_EXAM.model.api.imgur;

/**
 * A concrete implementation of the ImgurApiTemplate representing 'online' access
 * to the Imgur API.
 */
public class ImgurActualApi extends ImgurApiTemplate {

    public ImgurActualApi(String clientId, String bearerToken)  {
        super(clientId, bearerToken);
    }

    /**
     * Makes a query to the Imgur API to upload a QR code representing a string containing a nutrition report of a food item
     * Preconditions: None
     * Postconditions: None
     * @param report The nutrition report data of a selected food item to be converted into a QR code and uploaded to imgur
     * @return The Imgur link to the image of a QR code containing the nutrition report data
     */
    @Override
    public String uploadNutritionReportToImgur(String report) {
        return requestClientImpl.sendByteDataForUpload(getQrCodeImage(report), clientId);
    }

}
