package A3_EXAM.model.api.requestclient;

import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;

/**
 * Concrete implementation of the RequestClient interface. Defines behaviour for making POST and GET HTTP requests to interact with the
 * Edamam and Imgur APIs
 */
public class RequestClientImpl implements RequestClient {

    private HttpClient client;
    private String defaultQueryErrorMessage;
    private String imgurApiDetailsErrorMessage;
    private String imgurApiUploadErrorMessage;

    /**
     * Instantiates a new RequestClient object.
     */
    public RequestClientImpl() {
        client = HttpClient.newHttpClient();
        defaultQueryErrorMessage = "Your request could not be processed! Please try again.";
        imgurApiUploadErrorMessage = "Your request for a nutrition report could be not be processed! Please try again" +
                " and make sure you have a stable internet connection";
        imgurApiDetailsErrorMessage = "Your query for nutritional data could not be processed. Ensure the correct Imgur " +
                "API details are configured and try again!";
    }

    /**
     * Sends a GET request to the supplied URL and returns the outcome
     * Preconditions: The url should not be empty
     * Postconditions: None
     * @param url The url to make a GET HTTP request to
     * @return The outcome of the GET request. This will either be in a JSON format if successful or an error message if unsuccessful
     */
    public String sendGetRequest(String url) {

        if (url.isEmpty()) {
            return defaultQueryErrorMessage;
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofMillis(10000))
                    .build();

            return sendWebRequest(request);
        } catch (Exception e) {
            return defaultQueryErrorMessage;
        }


    }

    /**
     * Transmit a JSON string file through a POST request to the supplied URI and returns the outcome. Note the method is largely based on
     * content and examples from https://www.baeldung.com/httpurlconnection-post for sending a JSON object over a HTTP POST request
     * Preconditions: The uri should not be empty and the json string should not be empty and must be in a valid format
     * Postconditions: None
     * @param uri  The uri should not be empty
     * @param json The
     * @return The outcome of the POST request. This will either be in a JSON format if successful or an error message if unsuccessful
     */
    public String sendPostRequestWithJsonFile(String uri, String json) {

        URL url = null;
        HttpURLConnection connection = null;
        OutputStream os = null;
        BufferedReader brd = null;
        StringBuilder response = new StringBuilder();

        try {
            url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("content-type", "application/json; utf-8");
            connection.setRequestProperty("accept", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);

            os = connection.getOutputStream();
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);

            brd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            String responseLine = null;
            while ((responseLine = brd.readLine()) != null) {
                response.append(responseLine.trim());
            }

            brd.close();
            os.close();

            return response.toString();


        } catch (Exception e) {
            return defaultQueryErrorMessage;
        }

    }

    /**
     * Send web request contained in a HttpRequest object and returns the outcome.
     * Preconditions: None
     * Postconditions: None
     * @param request The HttpRequest object representing a POST or GET HTTP request
     * @return The outcome of the HTTP request. This will either be in a JSON format if successful or an error message if unsuccessful
     */
    public String sendWebRequest(HttpRequest request) {

        if (request == null) {
            return defaultQueryErrorMessage;
        }

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            return defaultQueryErrorMessage;
        }
    }

    /**
     * Send byte data as string for upload to Imgur. Based on content and examples from https://stackoverflow
     * .com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests/2793153#2793153 and https://www.baeldung.com/httpurlconnection-post
     * @param binaryReportData The nutrition report data to be uploaded in a QR code in the form of a byte array
     * @param clientID The client id of the Imgur application to be used in the upload request
     * @return The outcome of the upload request. This should be an imgur link if successful or an error message if unsuccessful
     */
    public String sendByteDataForUpload(byte[] binaryReportData, String clientID) {

        if (binaryReportData == null || clientID == null) {
            return defaultQueryErrorMessage;
        }

        URL url = null;
        HttpURLConnection connection = null;
        String reportData = Base64.getEncoder().encodeToString(binaryReportData);
        StringBuilder response = new StringBuilder();
        OutputStreamWriter owr = null;
        BufferedReader brd = null;

        try {
            url = new URL("https://api.imgur.com/3/image");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", String.format("Client-ID %s", clientID));
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            owr = new OutputStreamWriter(connection.getOutputStream());
            owr.write(reportData);
            owr.flush();


            brd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = null;
            while ((responseLine = brd.readLine()) != null) {
                response.append(responseLine.trim());
            }
            owr.close();
            brd.close();


            JSONObject imgurResponse = new JSONObject(response.toString());
            JSONObject imgurResponseData = imgurResponse.getJSONObject("data");

            if (imgurResponseData.has("error")) {
                return imgurApiDetailsErrorMessage;
            } else {
                return imgurResponseData.getString("link");
            }

        } catch (IOException e) {
            return imgurApiUploadErrorMessage;
        } catch (Exception e) {
            return imgurApiUploadErrorMessage;
        }


    }


}
