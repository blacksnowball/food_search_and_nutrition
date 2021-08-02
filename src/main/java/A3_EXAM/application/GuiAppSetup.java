package A3_EXAM.application;


import A3_EXAM.controller.Controller;
import A3_EXAM.model.facade.ModelFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

/**
 * This class is responsible for setting up the GUI application. Configuration of the APIs in either offline and online
 * mode and setup of the model, view, and controller occurs here.
 */
public class GuiAppSetup extends Application {

    /**
     * Configures and launches the GUI application according to preferences inputted in the command line. Initialises
     * the model, view, and controller objects needed to enable the application to run smoothly.
     * @param stage The main platform container for the GUI application
     */
    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FoodNutritionSearchGuiApp.fxml"));
            Parent root = fxmlLoader.load();
            Controller controller = fxmlLoader.getController();

            // configure model facade based on given arguments and inject into controller
            ApiConfigHandler apiConfigHandler = new ApiConfigHandler();
            List<String> launchApiParams = getParameters().getRaw();
            ModelFacade modelFacade = apiConfigHandler.generateFacadeBasedOnSettings("APIConfig.txt",
                    launchApiParams.get(0), launchApiParams.get(1));
            controller.setModelFacade(modelFacade);

            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            root.requestFocus();
            controller.promptUserForMaxCalorieAmount();
            stage.setOnCloseRequest(event -> controller.cleanupThreadPool());

        } catch (Exception e) {
            System.err.println(("An error occurred while launching the GUI application! Ensure that the " +
                    "'FoodNutritionSearchGuiApp.fxml' file is located in the resources folder under the main directory and try again."));
            System.exit(1);
        }



    }

    /**
     * Sets up the GUI application based on the arguments provided by the user in relation to the access modes for the different APIs.
     * Preconditions: None
     * Postconditions: The GUI application will launch with APIs in the access mode specified by the user
     * @param args The arguments provided by the user when launching the application via the command line
     */
    public void performAppSetup(String[] args) {

        // check only two args are supplied
        if (args.length != 2) {
            System.out.println(("Invalid number of arguments! The application will only take in two arguments to specify whether " +
                    "the APIs used are 'online' or 'offline'"));
            System.exit(1);
        }

        String inputApiMode = args[0];
        String outputApiMode = args[1];

        // launch application only if two args supplied are valid as 'offline' or online'
        if ((inputApiMode.equals("online") || inputApiMode.equals("offline")) &&
                (outputApiMode.equals("online") || outputApiMode.equals("offline"))) {
            Application.launch(GuiAppSetup.class, inputApiMode, outputApiMode);
        } else {
            System.out.println(("Invalid argument(s)! The application will only take in arguments either as 'online' " +
                    "or 'offline'"));
            System.exit(1);
        }

    }


}