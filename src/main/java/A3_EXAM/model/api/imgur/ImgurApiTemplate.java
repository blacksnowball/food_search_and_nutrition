package A3_EXAM.model.api.imgur;

import A3_EXAM.model.api.requestclient.RequestClient;
import A3_EXAM.model.api.requestclient.RequestClientImpl;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * An abstract class acting as a template for the attributes and behaviour of the Imgur API and its calls.
 * This is to help separate between an API caller object that represents 'online' or 'offline' access mode and their different behaviour
 */
public abstract class ImgurApiTemplate {

    protected final String clientId;
    protected final String bearerToken;
    protected RequestClient requestClientImpl;
    protected QRCodeWriter qrCodeWriter;

    /**
     * Instantiates a new imgurAPI object according to a template
     * @param clientId  The client id for the imgur application
     * @param bearerToken The bearer token of the imgur application
     */
    public ImgurApiTemplate(String clientId, String bearerToken) {
        this.clientId = clientId;
        this.bearerToken = bearerToken;
        requestClientImpl = new RequestClientImpl();
        qrCodeWriter = new QRCodeWriter();
    }

    /**
     * Makes a query to the Imgur API to upload a QR code representing a string containing a nutrition report of a food item
     * Preconditions: None
     * Postconditions: None
     * @param report The nutrition report data of a selected food item to be converted into a QR code and uploaded to imgur
     * @return The Imgur link to the image of a QR code containing the nutrition report data
     */
    public abstract String uploadNutritionReportToImgur(String report);

    /**
     * Converts a supplied string text (in this application context, the nutrition report) into a byte array that represents the string value
     * as a QR code in the form a .png file. Content of this code is largely based on the example shown at https://www.callicoder
     * .com/generate-qr-code-in-java-using-zxing/ in conjunction with the documentation at https://zxing.github.io/zxing/apidocs/index.html
     * Preconditions: None
     * Postconditions: None
     * @param text The text to be converted into a QR code
     * @return A byte array containing the values represented in a supplied string text
     */
    protected byte[] getQrCodeImage(String text) {
        BitMatrix bitMatrix = null;

        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 450, 450);
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
            return pngOutputStream.toByteArray();
        } catch (WriterException | IOException e) {
            return null;
        }
    }


}
