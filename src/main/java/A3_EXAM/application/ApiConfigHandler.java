package A3_EXAM.application;

import A3_EXAM.model.parsers.JSONParserImpl;
import A3_EXAM.model.facade.ModelFacade;
import A3_EXAM.model.facade.ModelFacadeImpl;
import A3_EXAM.model.parsers.ReportParserImpl;
import A3_EXAM.model.database.FoodAndNutritionDbImpl;
import A3_EXAM.model.api.edamam.FoodDatabaseApiTemplate;
import A3_EXAM.model.api.edamam.FoodDatabaseActualApi;
import A3_EXAM.model.api.edamam.FoodDatabaseDummyApi;
import A3_EXAM.model.api.imgur.ImgurApiTemplate;
import A3_EXAM.model.api.imgur.ImgurActualApi;
import A3_EXAM.model.api.imgur.ImgurDummyApi;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the configuration of the APIs to be setup in 'online' or 'offline' mode based on the command arguments provided and
 * injects them into a ModelFacade object to be supplied to the controller.
 */
public class ApiConfigHandler {

    /**
     * Initialises the Edamam and Imgur APIs according to a desired mode ('online' or 'offline') as specified by the user and injects these into a
     * ModelFacade object that will be returned and supplied to a controller
     * @param filename The name of the text file containing the details of the Imgur and Edamam APIs
     * @param foodApiMode The desired access mode of the Edamam API
     * @param imgurApiMode The desired access mode of the Imgur API
     * @return A ModelFacade object injected with the relevant API access modes
     */
    public ModelFacade generateFacadeBasedOnSettings(String filename, String foodApiMode, String imgurApiMode) {
        List<String> apiKeys = getApiKeysFromFile(filename);

        String edamamAppId = apiKeys.get(0);
        String edamamAppKey = apiKeys.get(1);
        String imgurClientId = apiKeys.get(2);
        String imgurBearerToken = apiKeys.get(3);

        FoodDatabaseApiTemplate foodApi = null;
        ImgurApiTemplate imgurApi = null;

        if (foodApiMode.equals("online")) {
            foodApi = new FoodDatabaseActualApi(edamamAppId, edamamAppKey);
        } else if (foodApiMode.equals("offline")) {
            foodApi = new FoodDatabaseDummyApi(edamamAppId, edamamAppKey);
        }

        if (imgurApiMode.equals("online")) {
            imgurApi = new ImgurActualApi(imgurClientId, imgurBearerToken);
        } else if (imgurApiMode.equals("offline")) {
            imgurApi = new ImgurDummyApi(edamamAppId, edamamAppKey);
        }

        return new ModelFacadeImpl(foodApi, imgurApi, new FoodAndNutritionDbImpl(), new JSONParserImpl(), new ReportParserImpl());

    }

    /**
     * Parses the text file containing the details for the Edamam and Imgur APIs and returns their values as a string list
     * @param filename The filename of the text file containing the API details
     * @return A list of strings containing the values of the API details
     */
    private List<String> getApiKeysFromFile(String filename) {

        try {
            File apiConfigFile = new File("APIConfig.txt");
            FileInputStream apiConfig = new FileInputStream(apiConfigFile);
            return IOUtils.readLines(apiConfig, "UTF-8");
        } catch (IOException e) {
            try {
                return IOUtils.readLines(this.getClass().getClassLoader().getResourceAsStream(filename), "UTF-8");
            } catch (IOException e2) {
                System.out.println("Could not find file!");
                System.exit(1);
            }
        }

        return new ArrayList<>();

    }



}
