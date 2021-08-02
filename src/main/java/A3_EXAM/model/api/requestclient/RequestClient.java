package A3_EXAM.model.api.requestclient;

import java.net.http.HttpRequest;

/**
 * Interface for a RequestClient. Provides a contract for sending POST and GET HTTP requests to different APIs
 */
public interface RequestClient {

    /**
     * Sends a GET request to the supplied URL and returns the outcome
     * Preconditions: The url should not be empty
     * Postconditions: None
     * @param url The url to make a GET HTTP request to
     * @return The outcome of the GET request. This will either be in a JSON format if successful or an error message if unsuccessful
     */
    String sendGetRequest(String url);

    /**
     * Transmit a JSON string file through a a POST request to the supplied URI and returns the outcome
     * Preconditions: The uri should not be empty and the json string should not be empty and must be in a valid format
     * Postconditions: None
     * @param uri  The uri should not be empty
     * @param json The
     * @return The outcome of the POST request. This will either be in a JSON format if successful or an error message if unsuccessful
     */
    String sendPostRequestWithJsonFile(String uri, String json);

    /**
     * Send web request contained in a HttpRequest object and returns the outcome.
     * Preconditions: None
     * Postconditions: None
     * @param request The HttpRequest object representing a POST or GET HTTP request
     * @return The outcome of the HTTP request. This will either be in a JSON format if successful or an error message if unsuccessful
     */
    String sendWebRequest(HttpRequest request);

    /**
     * Send byte data for upload string.
     *
     * @param binaryReportData The nutrition report data to be uploaded in a QR code in the form of a byte array
     * @param clientID The client id of the Imgur application to be used in the upload request
     * @return The outcome of the upload request. This should be an imgur link if successful or an error message if unsuccessful
     */
    String sendByteDataForUpload(byte[] binaryReportData, String clientID);

}
