package A3_EXAM.model.database;

import A3_EXAM.model.parsers.JSONParser;
import A3_EXAM.model.parsers.JSONParserImpl;
import A3_EXAM.model.dataobject.FoodItemData;
import A3_EXAM.model.dataobject.FoodItemNutrientData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete implementation of the FoodAndNutritionDb. Defines the access layer for interaction with the database
 * for caching and retrieving stored values related to the Edamam food API.
 */
public class FoodAndNutritionDbImpl implements FoodAndNutritionDb {

    private JSONParser jsonParser;
    private String defaultSQLErrorMessage;

    /**
     * Instantiates a FoodAndNutribitionDb object. Upon construction, checks whether the relevant tables for storing data exist (matching
     * food items and nutrient data. If true, then those tables will be created.
     */
    public FoodAndNutritionDbImpl() {

        jsonParser = new JSONParserImpl();
        defaultSQLErrorMessage = "An error occurred when interacting with the SQLITE Database. Reload the application" +
                " and clear the database file!";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite::FoodAndNutritionResults.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("create table if not exists MatchingFoodItems (search_term string, json_str " +
                    "string)");
            statement.executeUpdate("create table if not exists SelectedFoodItemNutritionResults (search_term string, food_id string, food_label string, nutrition_json)");
            statement.close();

        } catch(SQLException e) {
            System.out.println(defaultSQLErrorMessage);
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.out.println(defaultSQLErrorMessage);
            }
        }
    }

    /**
     * Check if the search query term has any matching item results have been cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param userQuery The query term to check in the database
     * @return True if the query term has cached data, otherwise false
     */
    @Override
    public boolean checkUserQueryExistsForMatchingResults(String userQuery) {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite::FoodAndNutritionResults.db");

            String getTermResults = "select * from MatchingFoodItems where lower(search_term) = ?";
            PreparedStatement pstmt = connection.prepareStatement(getTermResults);
            pstmt.setString(1, userQuery.toLowerCase().trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rs.close();
                pstmt.close();
                return true;
            } else {
                rs.close();
                pstmt.close();
                return false;
            }


        } catch (SQLException e) {
            System.out.println(defaultSQLErrorMessage);
            return false;
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.out.println(defaultSQLErrorMessage);
            }
        }

    }

    /**
     * Checks if the data of the selected item from a list of matching food items has been cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param foodId The food id of the selected food item to check in the database
     * @param label The label of the selected food item to check in the database
     * @return True if the selected food item data has been cached, otherwise false
     */
    @Override
    public boolean checkIfSelectedItemIsCached(String foodId, String label) {

        if (foodId == null || label == null) {
            return false;
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite::FoodAndNutritionResults.db");

            String getItemResults = "select * from SelectedFoodItemNutritionResults where food_id = ? and lower(food_label) = ?";
            PreparedStatement pstmt = connection.prepareStatement(getItemResults);



            pstmt.setString(1, foodId);
            pstmt.setString(2, label.toLowerCase().trim());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rs.close();
                pstmt.close();
                return true;
            } else {
                rs.close();
                pstmt.close();
                return false;
            }


        } catch (SQLException e) {
            System.out.println(defaultSQLErrorMessage);
            return false;
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.out.println(defaultSQLErrorMessage);
            }
        }


    }

    /**
     * Check if the search query term has nutrition data for a selected item cached in the database.
     * Preconditions: None
     * Postconditions: None
     * @param userQuery The query term to check in the database
     * @return True if the query term has cached data as nutrition data for a food item, otherwise false
     */
    @Override
    public boolean checkUserQueryHasMatchingNutritionData(String userQuery) {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite::FoodAndNutritionResults.db");

            String getItemResults = "select * from SelectedFoodItemNutritionResults where lower(food_label) = ?";
            PreparedStatement pstmt = connection.prepareStatement(getItemResults);
            pstmt.setString(1, userQuery.toLowerCase().trim());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rs.close();
                pstmt.close();
                return true;
            } else {
                rs.close();
                pstmt.close();
                return false;
            }


        } catch (SQLException e) {
            System.out.println(defaultSQLErrorMessage);
            return false;
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.out.println(defaultSQLErrorMessage);
            }
        }


    }


    /**
     * Fetches cached data from the database to yield a FoodItemData object representing general data about a food item. This is retrieved when the
     * user requests loading from a cache of a particular food item by search
     * Preconditions: The supplied user query should exist in the database
     * Postconditions: None
     * @param searchTerm The search term inputted by the user
     * @return A FoodItemData object constructed from cached data which is associated with existing cached nutrition data
     */
    @Override
    public List<FoodItemData> fetchMatchingFoodItemResults(String searchTerm) {


        Connection connection = null;
        List<FoodItemData> foodItems = new ArrayList<>();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite::FoodAndNutritionResults.db");


            String getTermResults = "select * from MatchingFoodItems where lower(search_term) = ?";
            PreparedStatement pstmt = connection.prepareStatement(getTermResults);
            pstmt.setString(1, searchTerm.toLowerCase().trim());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String jsonStr = rs.getString("json_str");
                rs.close();
                pstmt.close();
                foodItems = jsonParser.processFoodItemData(jsonStr);
            } else {
                rs.close();
                pstmt.close();
            }

            return foodItems;

        } catch (SQLException e) {
            System.out.println(defaultSQLErrorMessage);
            return foodItems;
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.out.println(defaultSQLErrorMessage);
            }
        }



    }



    /**
     * Caches the results of a query yielding matching food items to a search term. The database will insert the search term and the results in the
     * form of a JSON string if it does not yet exist in the database. If it already exists, the search term results will only be updated.
     * Preconditions: The JSON String should be in a valid format and not contain an error message
     * Postconditions: The data will be cached and the database will be updated to contain the supplied data
     * @param searchTerm The search term leading to a particular set of matching items to be cached
     * @param jsonStr The matching item results stored in the form of a JSON String to be cached
     */
    @Override
    public void insertMatchingFoodItemsResults(String searchTerm, String jsonStr) {

        Connection connection = null;

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite::FoodAndNutritionResults.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("create table if not exists MatchingFoodItems (search_term string, json_str " +
                    "string)");
            String checkExistingTerm = "select * from MatchingFoodItems where lower(search_term) = ?";
            PreparedStatement ps = connection.prepareStatement(checkExistingTerm);
            ps.setString(1, searchTerm.toLowerCase().trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String updateExistingTerm = "update MatchingFoodItems set json_str = ? where lower(search_term) = ?";
                ps = connection.prepareStatement(updateExistingTerm);
                ps.setString(1, jsonStr);
                ps.setString(2, searchTerm.toLowerCase().trim());
                ps.executeUpdate();

            } else {
                String insertNewTerm = "insert into MatchingFoodItems (search_term, json_str) values (?, ?)";
                ps = connection.prepareStatement(insertNewTerm);
                ps.setString(1, searchTerm.toLowerCase().trim());
                ps.setString(2, jsonStr);
                ps.executeUpdate();
            }

            rs.close();
            ps.close();
            statement.close();

        } catch(SQLException e) {
            System.out.println(defaultSQLErrorMessage);
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.out.println(defaultSQLErrorMessage);
            }

        }
    }

    /**
     * Caches the results of a query yielding nutrition data of a food item. The database will insert the food id, label, associated search termand
     * the results associated with a selected food item in the form of a JSON string if it does not yet exist. If it already exists, the results
     * will be updated instead.
     * Preconditions: The JSON String should be in a valid format and not contain an error message
     * Postconditions: The data will be cached adn the database will be updated to contain the supplied data
     * @param yieldingSearchTerm The search term yielding the food item
     * @param foodId    The food id of the food item to be cached
     * @param foodLabel The food label of the food item to be cached
     * @param jsonStr   The nutrition data results stored in the form of a JSON String to be cached
     */
    @Override
    public void insertSelectedFoodItemNutritionResults(String yieldingSearchTerm, String foodId, String foodLabel, String jsonStr) {

        Connection connection = null;

        try {
            // create a database connection

            connection = DriverManager.getConnection("jdbc:sqlite::FoodAndNutritionResults.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("create table if not exists SelectedFoodItemNutritionResults (search_term string," +
                    " food_id string, food_label string, nutrition_json)");
            String checkExistingTerm = "select * from SelectedFoodItemNutritionResults where food_id = ? and lower(food_label) = ?";
            PreparedStatement ps = connection.prepareStatement(checkExistingTerm);
            ps.setString(1, foodId);
            ps.setString(2, foodLabel.toLowerCase().trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                rs.close();
                String updateExistingTerm = "update SelectedFoodItemNutritionResults set search_term = ?, nutrition_json = ?, " +
                        "food_label = ?, food_id = ? where food_label = ? and food_id = ?";
                ps = connection.prepareStatement(updateExistingTerm);
                ps.setString(1, yieldingSearchTerm.toLowerCase().trim());
                ps.setString(2, jsonStr);
                ps.setString(3, foodLabel.toLowerCase().trim());
                ps.setString(4, foodId);
                ps.setString(5, foodLabel.toLowerCase().trim());
                ps.setString(6, foodId);
                ps.executeUpdate();

            } else {
                rs.close();
                String insertNewTerm = "insert into SelectedFoodItemNutritionResults (search_term, food_id, food_label, nutrition_json) " +
                        "values (?, ?, ?, ?)";
                ps = connection.prepareStatement(insertNewTerm);
                ps.setString(1, yieldingSearchTerm.toLowerCase().trim());
                ps.setString(2, foodId);
                ps.setString(3, foodLabel.toLowerCase().trim());
                ps.setString(4, jsonStr);
                ps.executeUpdate();
            }

            ps.close();
            statement.close();

        } catch(SQLException e) {
            System.out.println(defaultSQLErrorMessage);
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.out.println(defaultSQLErrorMessage);
            }
        }
    }

    /**
     * Fetches cached data from the database to yield a FoodItemNutrientData object representing the stored nutrient data of a food item.
     * Preconditions: The supplied search food id and food label should exist in the database
     * Postconditions: None
     * @param foodId The food id of the desired cached food item
     * @param foodLabel The food label of the desired cached food item
     * @return A FoodItemNutrientData object constructed from cached data, representing the nutrient data of a selected food item
     */
    @Override
    public FoodItemNutrientData fetchSelectedItemNutrientData(String foodId, String foodLabel) {

        Connection connection = null;
        FoodItemNutrientData foodItemNutrientData = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite::FoodAndNutritionResults.db");
            String getTermResults = "select * from SelectedFoodItemNutritionResults where food_id = ? and lower(food_label) = ?";
            PreparedStatement pstmt = connection.prepareStatement(getTermResults);
            pstmt.setString(1, foodId);
            pstmt.setString(2, foodLabel.toLowerCase().trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String jsonStr = rs.getString("nutrition_json");
                rs.close();
                pstmt.close();
                foodItemNutrientData = jsonParser.processNutrientData(jsonStr);
            } else {
                rs.close();
                pstmt.close();
            }

            return foodItemNutrientData;


        } catch (SQLException e) {
            System.out.println(defaultSQLErrorMessage);
            return foodItemNutrientData;
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.out.println(defaultSQLErrorMessage);
            }
        }

    }

    /**
     * Fetches cached data from the database to yield a FoodItemNutrientData object representing the stored nutrient data of a food item when
     * searched by the user.
     * Preconditions: The supplied search term should exist in the database
     * Postconditions: None
     * @param userQuery The search term provided by the user
     * @return A FoodItemNutrientData object constructed from cached data, representing the nutrient data of a selected food item
     */
    @Override
    public FoodItemNutrientData fetchSearchedItemNutrientData(String userQuery) {

        Connection connection = null;
        FoodItemNutrientData foodItemNutrientData = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite::FoodAndNutritionResults.db");
            String getTermResults = "select * from SelectedFoodItemNutritionResults where lower(food_label) = ?";
            PreparedStatement pstmt = connection.prepareStatement(getTermResults);
            pstmt.setString(1, userQuery.toLowerCase().trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String jsonStr = rs.getString("nutrition_json");
                rs.close();
                pstmt.close();
                foodItemNutrientData = jsonParser.processNutrientData(jsonStr);
            } else {
                rs.close();
                pstmt.close();
            }

            return foodItemNutrientData;


        } catch (SQLException e) {
            System.out.println(defaultSQLErrorMessage);
            return foodItemNutrientData;
        } finally {

            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.out.println(defaultSQLErrorMessage);
            }
        }



    }

    public FoodItemData fetchFoodItemMatchingNutrientDataFromSearch(String userQuery) {

        Connection connection = null;
        FoodItemData foodItemData = null;

        try {

            connection = DriverManager.getConnection("jdbc:sqlite::FoodAndNutritionResults.db");
            String getTermResults = "select * from SelectedFoodItemNutritionResults where lower(food_label) = ?";
            PreparedStatement pstmt = connection.prepareStatement(getTermResults);
            pstmt.setString(1, userQuery.toLowerCase().trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                String desiredFoodId = rs.getString("food_id");
                String desiredFoodLabel = rs.getString("food_label");

                rs.close();
                pstmt.close();

                String getMatchingItems = "select * from MatchingFoodItems";
                pstmt = connection.prepareStatement(getMatchingItems);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    String json = rs.getString("json_str");
                    List<FoodItemData> matchingFoodItems = jsonParser.processFoodItemData(json);
                    for (FoodItemData fd : matchingFoodItems) {
                        if (fd.getFoodId().equals(desiredFoodId) && fd.getFoodLabel().equalsIgnoreCase(desiredFoodLabel)) {
                            rs.close();
                            pstmt.close();
                            return fd;
                        }
                    }
                }


                rs.close();
                pstmt.close();


            } else {
                rs.close();
                pstmt.close();
            }

            return foodItemData;


        } catch (SQLException e) {
            System.out.println(defaultSQLErrorMessage);
            return foodItemData;
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException e) {
                System.out.println(defaultSQLErrorMessage);
            }
        }




    }


}