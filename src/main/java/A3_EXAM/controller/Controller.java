package A3_EXAM.controller;

import A3_EXAM.model.commands.*;
import A3_EXAM.model.dataobject.FoodItemData;
import A3_EXAM.model.dataobject.FoodItemDataImpl;
import A3_EXAM.model.facade.ModelFacade;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents the controller which is the intermediary between the model (communicated with through the model facade) and view
 * (represented through the fxml files). It communicates with the API and database using the model facade and command query objects representing
 * method calls, and relays this to the view to update what is displayed to the user through the GUI
 */
public class Controller implements Initializable {

    private ModelFacade modelFacade;
    private ObservableList<String> foodSearchResults;
    private FoodItemData selectedFoodItem;
    private Command foodItemSearchQuery;
    private Command foodItemNutrientSearchQuery;
    private Command fetchCachedMatchingFoodItemsQuery;
    private Command fetchCachedFoodNutrientDataBySelectionQuery;
    private Command fetchCachedFoodNutrientDataBySearchQuery;
    private Command uploadNutritionReportToImgurQuery;
    private Command compareItemEnergyWithMaxCaloriesQuery;
    private StringBuilder sb;
    private Runnable runnableForFreshFoodItemQueries;
    private Runnable runnableForCachedFoodItemQueries;
    private Runnable runnableForFreshNutritionalDataQueries;
    private Runnable runnableForCachedNutritionalDataQueriesBySelection;
    private Runnable runnableForCachedNutritionalDataQueriesBySearch;
    private Runnable runnableForUploadingReportToImgur;
    private Runnable runnableForCompareItemEnergyWithMaxCaloriesQuery;
    private ExecutorService threadPool;
    @FXML private TextField foodQueryText;
    @FXML private TextArea outputMessageBox;
    @FXML private ListView<String> matchingItemResultsBox;
    @FXML private Button getNutritionalDataButton;
    @FXML private Button getQRCodeButton;

    /**
     * Prompts the user through an alert popup about whether they would like to retrieve fresh or cached results regarding a query for matching food
     * items or nutrition data.
     * Preconditions: The relevant query must have cached data stored in the database for the popup to be sensibly called
     * Postconditions: None
     */
    private boolean promptUserAboutCachedResults() {

        ButtonType cachedResults =  new ButtonType("Cached results", ButtonBar.ButtonData.NO);;
        ButtonType freshResults = new ButtonType("Fresh results", ButtonBar.ButtonData.OTHER);

        Alert alert = new Alert(Alert.AlertType.NONE, "", cachedResults, freshResults);
        alert.setTitle("Choice of results");
        alert.setResizable(true);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setResizable(false);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Label label = new Label("Cached results are available for your request.\nDo you want fresh results or cached results for your search?");
        label.setFont(new Font(16));
        label.setWrapText(true);

        alert.getDialogPane().setContent(label);
        centerButtons(alert.getDialogPane());
        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(freshResults) == cachedResults;
    }

    /**
     * Alerts the user that for the currently selected food item, the caloric or energy value exceeds the max calorie amount set by the user at the
     * start of the application
     * Preconditions: The user must first select a food item and query its nutrition data
     * Postconditions: The user will be alerted through a pop-up that the food item's energy value exceeds the maximum calorie amount
     */
    private void promptUserAboutItemCalories() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Excessive calorie count");
        alert.setHeaderText(String.format("The item is above the max calorie amount of %d", modelFacade.getMaxCalorieAmount()));
        alert.setContentText(null);
        centerButtons(alert.getDialogPane());
        alert.showAndWait();
    }

    /**
     * Centrally aligns the button options in an alert popup to retrieve fresh or cached results for their query. This piece of code was not
     * written by me and is based on content from https://stackoverflow.com/questions/36009764/how-to-align-ok-button-of-a-dialog-pane-in-javafx
     * Preconditions: None
     * Postconditions: None
     */
    private void centerButtons(DialogPane dialogPane) {
        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hboxDialogPane = (HBox) dialogPane.lookup(".container");
        hboxDialogPane.getChildren().add(spacer);
    }

    /**
     * Updates the StringBuilder object to store the query search term requested by the user
     * Preconditions: None
     * Postconditions: None
     */
    private void setUserQuery(String query) {
        sb.delete(0, sb.length());
        sb.append(query);
    }

    /**
     * Injects the controller with a ModelFacade object which will be the point of interaction with the model aspect of the program.
     * Preconditions: None
     * Postconditions: The controller will be updated to make calls to the supplied ModelFacade object
     * @param facade The ModelFacade object to inject
     */
    public void setModelFacade(ModelFacade facade) {
        modelFacade = facade;
    }

    /**
     * Verifies whether a string is in valid JSON format or contains an error message
     * Preconditions: None
     * Postconditions: None
     * @param response The string to be checked
     * @return True if the String is in json format and without error contents, otherwise false
     */
    private boolean verifyValidQueryResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Updates the text area which notifies users about the outcomes of their query
     * Preconditions: None
     * Postconditions: None
     * @param text The text to be shown to the user
     */
    private void setOutputText(String text) {
        outputMessageBox.setText(text);
        outputMessageBox.setWrapText(true);
    }

    /**
     * Prepares the selected item from a list of matching items to be used for retrieving nutritional information about a food item
     * Preconditions: None
     * Postconditions: None
     */
    private void querySelectedItemForNutrition() {
        int chosenIndex = matchingItemResultsBox.getSelectionModel().getSelectedIndex();
        selectedFoodItem = modelFacade.getFoodItems().get(chosenIndex);
        foodItemNutrientSearchQuery = new FoodItemNutrientSearchQuery(modelFacade, sb, selectedFoodItem);
        foodItemNutrientSearchQuery.execute();
    }

    /**
     * Displays details about the selected item from a list of matching items to the user.
     * Preconditions: None
     * Postconditions: None
     */
    @FXML
    void displaySelectedItem() {
        int chosenIndex = matchingItemResultsBox.getSelectionModel().getSelectedIndex();

        try {
            selectedFoodItem = modelFacade.getFoodItems().get(chosenIndex);
            setOutputText(selectedFoodItem.displayUponBeingSelected());
        } catch (IndexOutOfBoundsException e) {
            return;
        }

    }


    /**
     * Prepares a Runnable task that will execute instructions for making a fresh query for nutrition data and caching its results, and then
     * displaying this to the user. This will be done on a thread separate to the main thread for handling the GUI.
     * Preconditions: A request should be made for fresh nutritional data by the user
     * Postconditions: The application will be updated to have the relevant FoodItemNutritionData and NutrientData objects based on fresh data from
     * the Edamam API. This data should be inserted or updated in the database.
     */
    private void prepareRunnableForFreshNutritionalDataQueries() {

        runnableForFreshNutritionalDataQueries = () -> {
            querySelectedItemForNutrition();
            String reportData = sb.toString();

            Platform.runLater(() -> {
                if (!reportData.contains("Nutritional facts")) {
                    setOutputText(reportData);
                } else {
                    Platform.runLater(() -> setOutputText(String.format("Successfully generated nutrition data for '%s' using fresh results.\n\n%s\n",
                            selectedFoodItem.displayUponMatchingResult(), reportData)));
                }
            });
            enableGuiButtons();
        };
    }

    /**
     * Prepares a Runnable task that will execute instructions for generating a QR code containing nutritional report data which is then uploaded
     * to Imgur. Displays the resulting link to the user. This will be done on a thread separate to the main thread for handling the GUI.
     * Preconditions: A request should be made for a QR code by the user
     * Postconditions: The application will be updated to show the user an Imgur link to the QR code containing nutrition report data
     */
    private void prepareRunnableForUploadingReportToImgur() {

        runnableForUploadingReportToImgur = () -> {
            uploadNutritionReportToImgurQuery = new UploadNutritionReportToImgurQuery(modelFacade, sb);
            uploadNutritionReportToImgurQuery.execute();


            String imgurLink = sb.toString();

            Platform.runLater(() -> {
                if (!imgurLink.contains("i.imgur.com")) {
                    setOutputText(imgurLink);
                } else {

                    if (!modelFacade.checkFoodItemExceedsMaxCalories()) {
                        setOutputText(String.format("Successfully uploaded the nutrition report for '%s': %s",
                        selectedFoodItem.displayUponMatchingResult(), imgurLink));
                    } else {
                        setOutputText(String.format("Successfully uploaded the nutrition report for '%s'. Note that the calorie amount for this " +
                                        "food item exceeds the maximum calorie amount: %s", selectedFoodItem.displayUponMatchingResult(), imgurLink));
                    }

                }
            });

            enableGuiButtons();
        };

    }

    /**
     * Executes instructions associated with generating a QR code containing the nutrition report data of a selected food item (based on fresh or
     * cached results) which is then uploaded to Imgur and the accessible link displayed to the user.
     * Preconditions: A user must have first requested nutrition data for a food item at least once and must request for a QR code
     * Postconditions: The application will be updated to show the user an Imgur link to the QR code containing nutrition report data
     */
    @FXML
    void uploadReportToImgur() {

        if (checkNoFoodItemSelected()) {
            return;
        }

        disableGuiButtons();

        if (modelFacade.checkIfSelectedItemIsCached(selectedFoodItem.getFoodId(), selectedFoodItem.getFoodLabel())) {
            modelFacade.setNutrientDataOfFoodItem(modelFacade.fetchFoodItemNutrientDataBySelection(selectedFoodItem.getFoodId(),
                    selectedFoodItem.getFoodLabel()));
            compareItemEnergyWithMaxCaloriesQuery.execute();
            threadPool.execute(runnableForUploadingReportToImgur);
        } else if ((modelFacade.getNutrientDataOfFoodItem().getFoodId() == null || selectedFoodItem.getFoodLabel() == null) ||
                (!modelFacade.getNutrientDataOfFoodItem().getFoodId().equals(selectedFoodItem.getFoodId()))) {
            setOutputText("You must first make a request for the nutrition data for a selected food item before a report of it may be uploaded as a" +
                    " QR code to Imgur!");
            enableGuiButtons();
        } else {
            compareItemEnergyWithMaxCaloriesQuery.execute();
            threadPool.execute(runnableForUploadingReportToImgur);
        }
    }

    /**
     * Prepares a Runnable task that will execute instructions for making a query for nutrition data based on retrieving cached results from a
     * selected food item among a list of matching items and then uploading this nutrition report to Imgur as a QR Code. This will be done on a thread
     * separate to the main thread for handling the GUI.
     * Preconditions: A request should be made for cached nutritional data by the user and the selected item data must exist in the database
     * Postconditions: The application will be updated to have the relevant FoodItemNutritionData and NutrientData objects based on cached data from the database.
     */
    private void prepareRunnableForCachedNutritionalDataQueriesBySelection() {

        runnableForCachedNutritionalDataQueriesBySelection = () -> {
            fetchCachedFoodNutrientDataBySelectionQuery = new FetchCachedFoodNutrientDataBySelectionQuery(modelFacade, sb, selectedFoodItem);
            fetchCachedFoodNutrientDataBySelectionQuery.execute();
            Platform.runLater(() -> setOutputText(String.format("Successfully generated nutrition data for '%s' using cached results.\n\n%s\n",
                    selectedFoodItem.displayUponMatchingResult(), sb.toString())));
            enableGuiButtons();
        };

    }

    /**
     * Prepares a Runnable task that will execute instructions for making a query for nutrition data based on retrieving cached results from a
     * food search and then uploading this nutrition report to Imgur as a QR Code. This will be done on a thread separate to the main thread for
     * handling the GUI.
     * Preconditions: A request should be made for cached nutritional data by the user and the selected item data must exist in the database
     * Postconditions: The application will be updated to have the relevant FoodItemNutritionData and NutrientData objects based on cached data from the database.
     */
    private void prepareRunnableForCachedNutritionalDataQueriesBySearch() {

        runnableForCachedNutritionalDataQueriesBySearch = () -> {
            fetchCachedFoodNutrientDataBySearchQuery = new FetchCachedFoodNutrientDataBySearchQuery(modelFacade, sb);
            fetchCachedFoodNutrientDataBySearchQuery.execute();

            Platform.runLater(() -> {
                if (modelFacade.getFoodItems().size() == 1 && modelFacade.getFoodItems().get(0) != null) {
                    selectedFoodItem = modelFacade.getFoodItems().get(0);
                    refreshMatchingFoodItems();
                }
                setOutputText(String.format("Successfully generated nutrition data for '%s' using cached results.\n\n%s\n",
                        selectedFoodItem.displayUponMatchingResult(), sb.toString()));

            });

            enableGuiButtons();
        };

    }


    /**
     * Executes instructions associated with getting the nutrition data of a selected item based on a fresh API call
     * Preconditions: None
     * Postconditions: None
     */
    private void getFreshNutritionData() {
        setOutputText(String.format("Generating a nutrition report for '%s' using fresh " +
                "results...", selectedFoodItem.displayUponMatchingResult()));
        threadPool.execute(runnableForFreshNutritionalDataQueries);
    }

    /**
     * Executes instructions associated with getting the nutrition data of a selected food item either using fresh or cached results. The main thread
     * will handle updating the GUI while the Runnable tasks will be executed by a ThreadPool to perform API calls and database caching and
     * retrieval in the background.
     * Preconditions: A user must select a food item from a list of matching food items and make a request for nutrition data
     * Postconditions: The nutrition data of a food item will be loaded and displayed based on fresh or cached data
     * @param event The event of clicking the button to get nutritional data of an item
     */
    @FXML
    void getItemNutritionalData(ActionEvent event) {

        if (checkNoFoodItemSelected()) {
            return;
        }

        disableGuiButtons();

        if (modelFacade.checkIfSelectedItemIsCached(selectedFoodItem.getFoodId(), selectedFoodItem.getFoodLabel())) {

            if (promptUserAboutCachedResults()) {
                setOutputText(String.format("Generating nutritional data for '%s' using cached results." +
                        "...", selectedFoodItem.displayUponMatchingResult()));
                threadPool.execute(runnableForCachedNutritionalDataQueriesBySelection);
            } else {
                getFreshNutritionData();
            }

        } else {
            getFreshNutritionData();
        }

        threadPool.execute(runnableForCompareItemEnergyWithMaxCaloriesQuery);

    }


    /**
     * Prepares a Runnable task that will execute instructions for making a fresh query for finding matching items to a search term. This will be
     * done on a thread separate to the main thread for handling the GUI.
     * Preconditions: A request should be made for fresh matching items by the user based on a search term being entered
     * Postconditions: The application will be updated to have the relevant FoodItemData objects based on fresh data from the API. The associated
     * data will be inserted or updated in the database.
     */
    private void prepareRunnableForFreshFoodItemQuery() {

        runnableForFreshFoodItemQueries = () -> {

            foodItemSearchQuery = new FoodItemSearchQuery(modelFacade, sb);
            foodItemSearchQuery.execute();
            String queryResponse = sb.toString();

            Platform.runLater(() -> {
                if (!verifyValidQueryResponse(queryResponse)) {
                    setOutputText(queryResponse);
                } else {
                    if (modelFacade.getFoodItems().isEmpty()) {
                        setOutputText(String.format("No matching items found for '%s'. (fresh results)", foodQueryText.getText()));
                    } else {
                        setOutputText(String.format("Successfully retrieved matching items for '%s' (fresh results)", foodQueryText.getText()));
                        refreshMatchingFoodItems();
                    }
                }
            });

            enableGuiButtons();
        };

    }

    /**
     * Prepares a Runnable task that will execute instructions for making a query for finding matching items to a search term based on cached
     * results. This will be done on a thread separate to the main thread for handling the GUI.
     * Preconditions: A request should be made for cached matching items by the user based on a search term being entered and the associated data
     * must exist in the database.
     * Postconditions: The application will be updated to have the relevant FoodItemData objects based on cached data from the database.
     */
    private void prepareRunnableForCachedFoodItemQuery() {

        runnableForCachedFoodItemQueries = () -> {

            fetchCachedMatchingFoodItemsQuery = new FetchCachedFoodItemsQuery(modelFacade, sb);
            fetchCachedMatchingFoodItemsQuery.execute();

            Platform.runLater(() -> {
                if (modelFacade.getFoodItems().isEmpty()) {
                    setOutputText(String.format("No matching results found for '%s'. (cached results)", foodQueryText.getText()));
                } else {
                    setOutputText(String.format("Successfully retrieved matching items for '%s' (cached results)", foodQueryText.getText()));
                    refreshMatchingFoodItems();
                }
            });

            enableGuiButtons();

        };



    }

    /**
     * Prepares a Runnable task that will execute instructions for making a query for retrieving the caloric value of the selected food item to be
     * compared to the maximum calorie amount set by the user. An alert pop-up will be raised if this is the case and this will be done on a thread
     * separate to the main thread for handling the GUI.
     * Preconditions: The user must first select a food item and query its nutrition data
     * Postconditions: The user will be alerted through a pop-up that the food item's energy value exceeds the maximum calorie amount
     */
    private void prepareRunnableForCompareItemEnergyWithMaxCalorieQuery() {

        runnableForCompareItemEnergyWithMaxCaloriesQuery = () -> {
            compareItemEnergyWithMaxCaloriesQuery = new CompareItemEnergyWithMaxCaloriesQuery(modelFacade, sb);
            compareItemEnergyWithMaxCaloriesQuery.execute();
            Platform.runLater(() -> {
                if (modelFacade.checkFoodItemExceedsMaxCalories()) {
                    promptUserAboutItemCalories();
                }
            });
        };
    }


    /**
     * Verifies whether the user is currently selecting a food item and displays a warning message as they attempt to perform some action; either
     * requesting nutrition data or a QR code
     * Preconditions: The user must first attempt to make a request for nutrition report data or a QR code
     * Postconditions: The user will be warned that they must first select a food item if they have not selected any and wish to make a request for
     * nutrition report data or a QR code
     * @return Whether the user is currently selecting a food item from the list of matching items. Returns true if not and false if yes
     */
    private boolean checkNoFoodItemSelected() {
        String selectedItem = matchingItemResultsBox.getSelectionModel().getSelectedItem();
        if (null == selectedItem || selectedItem.isEmpty() || modelFacade.getFoodItems().isEmpty()) {
            setOutputText("No items selected!");
            return true;
        }
        return false;
    }

    /**
     * Executes instructions associated with getting the matching items of a search term based on a fresh API call
     * Preconditions: None
     * Postconditions: None
     */
    private void getFreshResultsForMatchingItems() {
        setOutputText(String.format("Retrieving fresh results for your query '%s' ...", foodQueryText.getText()));
        threadPool.execute(runnableForFreshFoodItemQueries);
    }


    /**
     * Executes instructions associated with getting matching food items to a query either using fresh or cached results. The main thread
     * will handle updating the GUI while the Runnable tasks will be executed by a ThreadPool to perform API calls and database caching and
     * retrieval in the background.
     * Preconditions: The user must enter some input in the search bar and submit their request
     * Postconditions: The nutrition data of a food item will be loaded and displayed based on fresh or cached data
     * @param event The event of entering the query in the search bar
     */
    @FXML
    void getMatchingItems(ActionEvent event) {

        disableGuiButtons();
        modelFacade.getFoodItems().clear();
        matchingItemResultsBox.getItems().clear();
        setUserQuery(foodQueryText.getText());

        if (modelFacade.checkIfUserQueryHasMatchingNutritionData(foodQueryText.getText())) {

            if (promptUserAboutCachedResults()) {
                setOutputText(String.format("Retrieving cached results for your query '%s' ...", foodQueryText.getText()));
                threadPool.execute(runnableForCachedNutritionalDataQueriesBySearch);
                threadPool.execute(runnableForCompareItemEnergyWithMaxCaloriesQuery);
            } else {
                getFreshResultsForMatchingItems();
            }


        } else if (modelFacade.checkIfSearchQueryIsCachedForMatchingItems(foodQueryText.getText())) {

            if (promptUserAboutCachedResults()) {
                setOutputText(String.format("Retrieving cached results for your query '%s' ...", foodQueryText.getText()));
                threadPool.execute(runnableForCachedFoodItemQueries);
            } else {
                getFreshResultsForMatchingItems();
            }

        } else {
            getFreshResultsForMatchingItems();
        }

        adjustListViewTextSize();


    }


    /**
     * Updates the entries displayed by the listview which tracks the list of different matching items according to a food search query
     * Preconditions: The list of items tracked by the listview containing matching items needs to be updated
     * Postconditions: The entries displayed by the listview will be updated to reflecting a different number of items being tracked
     */
    private void refreshMatchingFoodItems() {
        foodSearchResults.removeAll(foodSearchResults);
        modelFacade.getFoodItems().forEach(foodItemData -> foodSearchResults.add(foodItemData.displayUponMatchingResult()));
        matchingItemResultsBox.getItems().addAll(foodSearchResults);
    }

    /**
     * Disables elements of the GUI involving API and database calls to prevent overload of user input.
     * Preconditions: Elements of the GUI involving API and database calls will only be disabled when a task involving them is called
     * Postconditions: Elements of the GUI involving API and database calls will be disabled until the relevant task is complete
     */
    private void disableGuiButtons() {
        foodQueryText.setDisable(true);
        matchingItemResultsBox.setDisable(true);
        getNutritionalDataButton.setDisable(true);
        getQRCodeButton.setDisable(true);
    }

    /**
     * Enables elements of the GUI involving API and database calls once a task involving them is done
     * Preconditions: Elements of the GUI involving API and database calls will only be enabled when a task involving them is finished
     * Postconditions: Elements of the GUI involving API and database calls will be enabled for users to interact with
     */

    private void enableGuiButtons() {
        foodQueryText.setDisable(false);
        matchingItemResultsBox.setDisable(false);
        getNutritionalDataButton.setDisable(false);
        getQRCodeButton.setDisable(false);
    }

    /**
     * Updates the size of each entry in the list view containing the matching items for a food query. Largely based on content from
     * https://stackoverflow.com/questions/39620548/change-listview-font-size-in-javafx with slight adjustment to the context
     * Preconditions: The list of items tracked by the listview containing matching items needs to be updated
     * Postconditions: The text size of each entry in the listview containing matchings items will be updated
     */
    private void adjustListViewTextSize() {
        matchingItemResultsBox.setCellFactory(cell -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setFont(Font.font(18));
            }
        });
    }

    /**
     * Verifies whether the user input in relation to the maximum calorie amount is valid.
     * Preconditions: The user must first provide input
     * Postconditions: If a user's input is invalid, the prompt window will be updated advising them of this error and to try again.
     * @param input The data inputted by the user
     * @param maxCaloriePrompt The dialog window that is presented to the user prompting them for input
     * @return True if the user input is valid (i.e. an integer in the range from 1 - 1000) otherwise false
     */
    private boolean validateUserCalorieInput(String input, TextInputDialog maxCaloriePrompt) {

        if (input.isEmpty()) {
            maxCaloriePrompt.setContentText("Your input cannot be empty. Try again!");
            return false;
        }

        try {
            Integer value = Integer.valueOf(input);

            if (value < 1) {
                maxCaloriePrompt.setContentText("The number given must be at least greater than 1. Try again!");
                return false;
            } else if (value > 1000) {
                maxCaloriePrompt.setContentText("The number given cannot be greater than 1000. Try again!");
                return false;
            }

            modelFacade.setMaxCalorieAmount(value);
            return true;

        } catch (NumberFormatException err) {
            maxCaloriePrompt.setContentText("Invalid input! Only integers are permitted. Try again!");
            return false;
        }
    }

    /**
     * Prompts the user upon starting the application to provide a number between 1 - 1000 to be set as the maximum calorie amount
     * Preconditions: The application must first be launched
     * Postconditions: Upon entering a valid number, the program will set that number to the maximum calorie amount
     */
    public void promptUserForMaxCalorieAmount() {
        TextInputDialog maxCaloriePrompt = new TextInputDialog();
        maxCaloriePrompt.initStyle(StageStyle.UNDECORATED);
        maxCaloriePrompt.setTitle("Maximum calorie amount");
        maxCaloriePrompt.setHeaderText("Enter an integer from 1 - 1000.\nThis will be the max calorie amount.");
        maxCaloriePrompt.setContentText("");
        maxCaloriePrompt.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        TextField input = maxCaloriePrompt.getEditor();
        final Button cancelButton = (Button) maxCaloriePrompt.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setVisible(false);
        maxCaloriePrompt.setOnCloseRequest(event -> {
            if (!validateUserCalorieInput(input.getText(), maxCaloriePrompt)) {
                event.consume();
            }
        });
        maxCaloriePrompt.showAndWait();
    }

    /**
     * Called to initialise the controller after its root element has been completely processed. Prepares the different Runnable tasks be executed
     * concurrently by the ThreadPool later as requested and sets up the GUI environment to be responsive to user interactions.
     * Preconditions: None
     * Postconditions: None
     * @param location  The location used to resolve relative paths for the root object, or if the location is not known.
     * @param resources The resources used to localize the root object
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sb = new StringBuilder();
        selectedFoodItem = new FoodItemDataImpl();
        threadPool = Executors.newFixedThreadPool(1);
        foodSearchResults = FXCollections.observableArrayList();
        outputMessageBox.setEditable(false);

        matchingItemResultsBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        matchingItemResultsBox.setOnKeyPressed(event -> {
            if ((event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) && matchingItemResultsBox.getItems().size() > 0) {
                displaySelectedItem();
            }
        });

        prepareRunnableForFreshFoodItemQuery();
        prepareRunnableForCachedFoodItemQuery();
        prepareRunnableForFreshNutritionalDataQueries();
        prepareRunnableForCachedNutritionalDataQueriesBySelection();
        prepareRunnableForCachedNutritionalDataQueriesBySearch();
        prepareRunnableForUploadingReportToImgur();
        prepareRunnableForCompareItemEnergyWithMaxCalorieQuery();
    }

    /**
     * Ensures that all thread processes finish their work and threads are shutdown when the application is exited
     * Preconditions: The GUI application needs to be shutting down
     * Postconditions: None
     */
    public void cleanupThreadPool() {
        threadPool.shutdown();
    }

}