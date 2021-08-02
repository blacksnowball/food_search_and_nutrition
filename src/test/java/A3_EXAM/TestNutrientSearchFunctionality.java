package A3_EXAM;

import A3_EXAM.model.database.FoodAndNutritionDbImpl;
import A3_EXAM.model.api.edamam.FoodDatabaseActualApi;
import A3_EXAM.model.api.edamam.FoodDatabaseDummyApi;
import A3_EXAM.model.api.imgur.ImgurActualApi;
import A3_EXAM.model.dataobject.FoodItemNutrientData;
import A3_EXAM.model.dataobject.NutrientData;
import A3_EXAM.model.facade.ModelFacade;
import A3_EXAM.model.facade.ModelFacadeImpl;
import A3_EXAM.model.parsers.JSONParser;
import A3_EXAM.model.parsers.JSONParserImpl;
import A3_EXAM.model.parsers.ReportParser;
import A3_EXAM.model.parsers.ReportParserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class TestNutrientSearchFunctionality {

    private ModelFacade model;
    private JSONParser jsonParser;
    private ReportParser reportParserImpl;
    private final float delta = (float) 0.01;
    @Mock private FoodDatabaseActualApi foodApi;
    @Mock private FoodDatabaseDummyApi foodDummyApi;
    @Mock private ImgurActualApi imgurApi;
    @Mock private FoodAndNutritionDbImpl db;

    @Before
    public void setup() {
        jsonParser = new JSONParserImpl();
        reportParserImpl = new ReportParserImpl();
        model = new ModelFacadeImpl(foodApi, imgurApi, db, jsonParser, reportParserImpl);
    }

    @Test
    public void testHandleBadUserInput() {
        lenient().when(foodApi.foodNutritionSearchRequest("http://www.edamam.com/ontologies/edamam" +
                ".owl#Measure_serving", "")).thenReturn("Your query could not be processed. Please try again!");
        assertEquals("Invalid input! The food ID or measure URI fields cannot be empty.",
                model.foodNutritionSearchRequest("http://www.edamam.com/ontologies/edamam.owl#Measure_serving", ""));
        assertEquals("Invalid input! The food ID or measure URI fields cannot be empty.",
                model.foodNutritionSearchRequest("", ""));
        assertEquals("Invalid input! The food ID or measure URI fields cannot be empty.",
                model.foodNutritionSearchRequest("", "food_ax9o3jjbybptbrbzwdbrean5cgv"));
    }

    @Test
    public void testCreateFoodItemNutrientDataObject() {
        lenient().when(foodApi.foodNutritionSearchRequest("http://www.edamam.com/ontologies/edamam" +
                ".owl#Measure_serving", "food_ax9o3jjbybptbrbzwdbrean5cg4v")).thenReturn("{\"uri\":\"http://www" +
                ".edamam.com/ontologies/edamam.owl#a8377be0-c578-4959-802e-5d4d3f8e032a\",\"calories\":250,\"totalWeight\":68.03885820362122,\"dietLabels\":[],\"healthLabels\":[\"PEANUT_FREE\",\"TREE_NUT_FREE\",\"SOY_FREE\",\"FISH_FREE\",\"SHELLFISH_FREE\",\"PORK_FREE\",\"CRUSTACEAN_FREE\",\"CELERY_FREE\",\"MUSTARD_FREE\",\"SESAME_FREE\",\"LUPINE_FREE\",\"MOLLUSK_FREE\",\"ALCOHOL_FREE\"],\"cautions\":[\"GLUTEN\",\"WHEAT\",\"SULFITES\",\"FODMAP\"],\"totalNutrients\":{\"ENERC_KCAL\":{\"label\":\"Energy\",\"quantity\":250.99999999999997,\"unit\":\"kcal\"},\"FAT\":{\"label\":\"Fat\",\"quantity\":3,\"unit\":\"g\"},\"FASAT\":{\"label\":\"Saturated\",\"quantity\":1,\"unit\":\"g\"},\"CHOCDF\":{\"label\":\"Carbs\",\"quantity\":51,\"unit\":\"g\"},\"FIBTG\":{\"label\":\"Fiber\",\"quantity\":0.10000000149011612,\"unit\":\"g\"},\"SUGAR\":{\"label\":\"Sugars\",\"quantity\":3,\"unit\":\"g\"},\"PROCNT\":{\"label\":\"Protein\",\"quantity\":6,\"unit\":\"g\"},\"CHOLE\":{\"label\":\"Cholesterol\",\"quantity\":6,\"unit\":\"mg\"},\"NA\":{\"label\":\"Sodium\",\"quantity\":2142,\"unit\":\"mg\"}},\"totalDaily\":{\"ENERC_KCAL\":{\"label\":\"Energy\",\"quantity\":12.549999999999999,\"unit\":\"%\"},\"FAT\":{\"label\":\"Fat\",\"quantity\":4.615384615384615,\"unit\":\"%\"},\"FASAT\":{\"label\":\"Saturated\",\"quantity\":5,\"unit\":\"%\"},\"CHOCDF\":{\"label\":\"Carbs\",\"quantity\":17,\"unit\":\"%\"},\"FIBTG\":{\"label\":\"Fiber\",\"quantity\":0.4000000059604645,\"unit\":\"%\"},\"PROCNT\":{\"label\":\"Protein\",\"quantity\":12,\"unit\":\"%\"},\"CHOLE\":{\"label\":\"Cholesterol\",\"quantity\":2,\"unit\":\"%\"},\"NA\":{\"label\":\"Sodium\",\"quantity\":89.25,\"unit\":\"%\"}},\"ingredients\":[{\"parsed\":[{\"quantity\":1,\"measure\":\"serving\",\"food\":\"Vietnamese Pho Rice Noodle Soup, Pho Bo, Beef Flavor\",\"foodId\":\"food_ax9o3jjbybptbrbzwdbrean5cg4v\",\"foodContentsLabel\":\"Rice Noodle : Rice; Salt; Sugar; Guar Gum. Vegetables Pack : Textured Wheat Gluten; Eryngium; Leek. Oil Pack : Palm Oil; Artificial Beef Flavor. Soup Base : Salt; Sugar; Monosodium Glutamate; Artificial Beef Flavor; Onion; Disodium 5 - Inosinate And Disodium 5 - Guanylate; Caramel. Chili Sauce : Chili; Garlic; Salt; Sugar; Vinegar; Corn Starch.\",\"weight\":68.03885820362122,\"retainedWeight\":68.03885820362122,\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\",\"quantity\":2.4000000953674316},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":70}],\"servingsPerContainer\":1,\"measureURI\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"status\":\"OK\"}]}]}");

        String apiResponse = foodApi.foodNutritionSearchRequest("http://www.edamam.com/ontologies/edamam.owl#Measure_serving",
                "food_ax9o3jjbybptbrbzwdbrean5cg4v");

        FoodItemNutrientData foodNutrients = model.getFoodItemNutrientData(apiResponse);

        List<NutrientData> nutrientData = foodNutrients.getTotalNutrients();
        assertEquals(68.03885820362122, foodNutrients.getWeight(), delta);
        assertEquals("Rice Noodle : Rice; Salt; Sugar; Guar Gum. Vegetables Pack : Textured Wheat Gluten; Eryngium; " +
                "Leek. Oil Pack : Palm Oil; Artificial Beef Flavor. Soup Base : Salt; Sugar; Monosodium Glutamate; " +
                "Artificial Beef Flavor; Onion; Disodium 5 - Inosinate And Disodium 5 - Guanylate; Caramel. Chili " +
                "Sauce : Chili; Garlic; Salt; Sugar; Vinegar; Corn Starch.", foodNutrients.getFoodContents());
        assertEquals(0, foodNutrients.getDietLabels().size());
        assertEquals(13, foodNutrients.getHealthLabels().size());
        assertEquals(4, foodNutrients.getCautions().size());
        assertEquals(8, foodNutrients.getDailyNutrients().size());
        assertEquals("food_ax9o3jjbybptbrbzwdbrean5cg4v", foodNutrients.getFoodId());
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#a8377be0-c578-4959-802e-5d4d3f8e032a",
                foodNutrients.getNutrientUri());

        assertNull(foodNutrients.getGlycemicIndex());
        assertEquals(9, nutrientData.size());

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Energy")) {
                assertEquals("Energy", nutrientData.get(i).getNutrientLabel());
                assertEquals(250.99999999999997, nutrientData.get(i).getQuantity(), delta);
                assertEquals("kcal", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Fat")) {
                assertEquals("Fat", nutrientData.get(i).getNutrientLabel());
                assertEquals(3, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Saturated")) {
                assertEquals("Saturated", nutrientData.get(i).getNutrientLabel());
                assertEquals(1, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Carbs")) {
                assertEquals("Carbs", nutrientData.get(i).getNutrientLabel());
                assertEquals(51, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Fiber")) {
                assertEquals("Fiber", nutrientData.get(i).getNutrientLabel());
                assertEquals(0.10000000149011612, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Sugars")) {
                assertEquals("Sugars", nutrientData.get(i).getNutrientLabel());
                assertEquals(3, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Protein")) {
                assertEquals("Protein", nutrientData.get(i).getNutrientLabel());
                assertEquals(6, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Cholesterol")) {
                assertEquals("Cholesterol", nutrientData.get(i).getNutrientLabel());
                assertEquals(6, nutrientData.get(i).getQuantity(), delta);
                assertEquals("mg", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Sodium")) {
                assertEquals("Sodium", nutrientData.get(i).getNutrientLabel());
                assertEquals(2142, nutrientData.get(i).getQuantity(), delta);
                assertEquals("mg", nutrientData.get(i).getUnit());
                break;
            }
        }

    }

    @Test
    public void testCreateFoodItemNutrientDataObjectForDummyApi() {

        model = new ModelFacadeImpl(foodDummyApi, imgurApi, db, jsonParser, reportParserImpl);

        lenient().when(foodDummyApi.foodNutritionSearchRequest("http://www.edamam.com/ontologies/edamam" +
                ".owl#Measure_serving", "food_bk6goo7bkb4utbajbrx1lav6e0vm")).thenReturn("{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#f646f0fd-eaac-40c9-bfbd-10ffef3fd70a\",\"calories\":140,\"totalWeight\":28,\"dietLabels\":[],\"healthLabels\":[\"VEGETARIAN\",\"PESCATARIAN\",\"GLUTEN_FREE\",\"WHEAT_FREE\",\"EGG_FREE\",\"TREE_NUT_FREE\",\"FISH_FREE\",\"SHELLFISH_FREE\",\"PORK_FREE\",\"RED_MEAT_FREE\",\"CRUSTACEAN_FREE\",\"CELERY_FREE\",\"MUSTARD_FREE\",\"SESAME_FREE\",\"LUPINE_FREE\",\"MOLLUSK_FREE\",\"ALCOHOL_FREE\",\"KOSHER\"],\"cautions\":[\"SULFITES\"],\"totalNutrients\":{\"ENERC_KCAL\":{\"label\":\"Energy\",\"quantity\":140,\"unit\":\"kcal\"},\"FAT\":{\"label\":\"Fat\",\"quantity\":9.000000000000002,\"unit\":\"g\"},\"FASAT\":{\"label\":\"Saturated\",\"quantity\":1.5000000000000002,\"unit\":\"g\"},\"CHOCDF\":{\"label\":\"Carbs\",\"quantity\":16.000000000000004,\"unit\":\"g\"},\"FIBTG\":{\"label\":\"Fiber\",\"quantity\":1.0000000000000002,\"unit\":\"g\"},\"SUGAR\":{\"label\":\"Sugars\",\"quantity\":1.0000000000000002,\"unit\":\"g\"},\"PROCNT\":{\"label\":\"Protein\",\"quantity\":2.0000000000000004,\"unit\":\"g\"},\"CHOLE\":{\"label\":\"Cholesterol\",\"quantity\":0,\"unit\":\"mg\"},\"NA\":{\"label\":\"Sodium\",\"quantity\":240.00000000000006,\"unit\":\"mg\"},\"CA\":{\"label\":\"Calcium\",\"quantity\":20.000000000000004,\"unit\":\"mg\"},\"FE\":{\"label\":\"Iron\",\"quantity\":0.3600000143051148,\"unit\":\"mg\"},\"VITC\":{\"label\":\"Vitamin C\",\"quantity\":4.800000190734864,\"unit\":\"mg\"}},\"totalDaily\":{\"ENERC_KCAL\":{\"label\":\"Energy\",\"quantity\":7,\"unit\":\"%\"},\"FAT\":{\"label\":\"Fat\",\"quantity\":13.84615384615385,\"unit\":\"%\"},\"FASAT\":{\"label\":\"Saturated\",\"quantity\":7.500000000000002,\"unit\":\"%\"},\"CHOCDF\":{\"label\":\"Carbs\",\"quantity\":5.333333333333335,\"unit\":\"%\"},\"FIBTG\":{\"label\":\"Fiber\",\"quantity\":4.000000000000001,\"unit\":\"%\"},\"PROCNT\":{\"label\":\"Protein\",\"quantity\":4.000000000000001,\"unit\":\"%\"},\"CHOLE\":{\"label\":\"Cholesterol\",\"quantity\":0,\"unit\":\"%\"},\"NA\":{\"label\":\"Sodium\",\"quantity\":10.000000000000004,\"unit\":\"%\"},\"CA\":{\"label\":\"Calcium\",\"quantity\":2.0000000000000004,\"unit\":\"%\"},\"FE\":{\"label\":\"Iron\",\"quantity\":2.0000000794728603,\"unit\":\"%\"},\"VITC\":{\"label\":\"Vitamin C\",\"quantity\":5.3333335452609605,\"unit\":\"%\"}},\"ingredients\":[{\"parsed\":[{\"quantity\":1,\"measure\":\"serving\",\"food\":\"Doritos - Jacked Test Flavor - 2 Blue\",\"foodId\":\"food_bk6goo7bkb4utbajbrx1lav6e0vm\",\"foodContentsLabel\":\"Potatoes; Vegetable Oil ( Peanut Oil; Corn Oil; Sunflower Oil ); Salt; Monterey Jack; Blue And Cheddar Cheese ( Pasteurized Milk; Cheese Cultures; Salt; Enzymes ); Maltodextrin; Partially Hydrogenated Soybean Oil; Whey; Spices; Nonfat Dry Milk; Citric Acid; Natural Flavor; Disodium Inosinate And Guanylate.\",\"weight\":28,\"retainedWeight\":28,\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":28},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_chip\",\"label\":\"Chip\",\"quantity\":13}],\"servingsPerContainer\":1,\"measureURI\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"status\":\"OK\"}]}]}");

        String apiResponse = foodDummyApi.foodNutritionSearchRequest("http://www.edamam.com/ontologies/edamam" +
                ".owl#Measure_serving", "food_bk6goo7bkb4utbajbrx1lav6e0vm");

        String expectedFoodContents = "Potatoes; Vegetable Oil ( Peanut Oil; Corn Oil; Sunflower Oil ); Salt; " +
                "Monterey Jack; Blue And Cheddar Cheese ( Pasteurized Milk; Cheese Cultures; Salt; Enzymes ); " +
                "Maltodextrin; Partially Hydrogenated Soybean Oil; Whey; Spices; Nonfat Dry Milk; Citric Acid; Natural Flavor; Disodium Inosinate And Guanylate.";

        FoodItemNutrientData data = model.getFoodItemNutrientData(apiResponse);
        assertEquals("food_bk6goo7bkb4utbajbrx1lav6e0vm", data.getFoodId());
        assertEquals(expectedFoodContents, data.getFoodContents());
        assertEquals(28, data.getWeight(), delta);

        assertEquals(0, data.getDietLabels().size());
        assertEquals(18, data.getHealthLabels().size());
        assertEquals(1, data.getCautions().size());
        assertEquals(11, data.getDailyNutrients().size());
        assertNull(data.getGlycemicIndex());

        List<NutrientData> nutrientData = data.getTotalNutrients();
        assertEquals(12, nutrientData.size());

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Energy")) {
                assertEquals("Energy", nutrientData.get(i).getNutrientLabel());
                assertEquals(140, nutrientData.get(i).getQuantity(), delta);
                assertEquals("kcal", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Fat")) {
                assertEquals("Fat", nutrientData.get(i).getNutrientLabel());
                assertEquals(9.000000000000002, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }


        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Saturated")) {
                assertEquals("Saturated", nutrientData.get(i).getNutrientLabel());
                assertEquals(1.5000000000000002, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }


        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Carbs")) {
                assertEquals("Carbs", nutrientData.get(i).getNutrientLabel());
                assertEquals(16.000000000000004, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Fiber")) {
                assertEquals("Fiber", nutrientData.get(i).getNutrientLabel());
                assertEquals(1.0000000000000002, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Sugars")) {
                assertEquals("Sugars", nutrientData.get(i).getNutrientLabel());
                assertEquals(1.0000000000000002, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Protein")) {
                assertEquals("Protein", nutrientData.get(i).getNutrientLabel());
                assertEquals(2.0000000000000004, nutrientData.get(i).getQuantity(), delta);
                assertEquals("g", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Cholesterol")) {
                assertEquals("Cholesterol", nutrientData.get(i).getNutrientLabel());
                assertEquals(0, nutrientData.get(i).getQuantity(), delta);
                assertEquals("mg", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Sodium")) {
                assertEquals("Sodium", nutrientData.get(i).getNutrientLabel());
                assertEquals(240.00000000000006, nutrientData.get(i).getQuantity(), delta);
                assertEquals("mg", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Calcium")) {
                assertEquals("Calcium", nutrientData.get(i).getNutrientLabel());
                assertEquals(20.000000000000004, nutrientData.get(i).getQuantity(), delta);
                assertEquals("mg", nutrientData.get(i).getUnit());
                break;
            }
        }


        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Iron")) {
                assertEquals("Iron", nutrientData.get(i).getNutrientLabel());
                assertEquals(0.3600000143051148, nutrientData.get(i).getQuantity(), delta);
                assertEquals("mg", nutrientData.get(i).getUnit());
                break;
            }
        }

        for (int i = 0; i < nutrientData.size(); i++) {
            if (nutrientData.get(i).getNutrientLabel().equals("Vitamin C")) {
                assertEquals("Vitamin C", nutrientData.get(i).getNutrientLabel());
                assertEquals(4.800000190734864, nutrientData.get(i).getQuantity(), delta);
                assertEquals("mg", nutrientData.get(i).getUnit());
                break;
            }
        }

    }


}
