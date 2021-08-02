package A3_EXAM;

import A3_EXAM.model.commands.UploadNutritionReportToImgurQuery;
import A3_EXAM.model.database.FoodAndNutritionDbImpl;
import A3_EXAM.model.api.edamam.FoodDatabaseActualApi;
import A3_EXAM.model.api.edamam.FoodDatabaseDummyApi;
import A3_EXAM.model.api.imgur.ImgurActualApi;
import A3_EXAM.model.api.imgur.ImgurDummyApi;
import A3_EXAM.model.commands.FoodItemNutrientSearchQuery;
import A3_EXAM.model.commands.FoodItemSearchQuery;
import A3_EXAM.model.commands.Command;
import A3_EXAM.model.dataobject.FoodItemData;
import A3_EXAM.model.dataobject.FoodItemDataImpl;
import A3_EXAM.model.dataobject.FoodItemNutrientData;
import A3_EXAM.model.facade.ModelFacade;
import A3_EXAM.model.facade.ModelFacadeImpl;
import A3_EXAM.model.parsers.JSONParser;
import A3_EXAM.model.parsers.JSONParserImpl;
import A3_EXAM.model.parsers.ReportParser;
import A3_EXAM.model.parsers.ReportParserImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TestCommandQueriesForSearchAndUploadFunctionality {

    private ModelFacade model;
    private StringBuilder sb;
    private JSONParser jsonParser;
    private ReportParser reportParserImpl;

    @Mock private FoodDatabaseActualApi foodApi;
    @Mock private FoodDatabaseDummyApi foodDummyApi;
    @Mock private ImgurActualApi imgurApi;
    @Mock private ImgurDummyApi imgurDummyApi;
    @Mock private FoodAndNutritionDbImpl db;
    private Command foodSearchCommand;
    private Command nutrientSearchCommand;
    private Command uploadReportCommand;

    @Before
    public void setup() {
        jsonParser = new JSONParserImpl();
        reportParserImpl = new ReportParserImpl();
        model = new ModelFacadeImpl(foodApi, imgurApi, db, jsonParser, reportParserImpl);
        sb = new StringBuilder();
        foodSearchCommand = new FoodItemSearchQuery(model, sb);
    }

    @Test
    public void testNutrientSearchReturnsImgurLinkAndUpdatesItem() {
        lenient().when(foodApi.foodSearchRequest("app")).thenReturn("{\"text\":\"app\",\"parsed\":[],\"hints\":[{\"food\":{\"foodId\":\"food_bzvzpk8bjqsr7wa9fimylaz2j78u\",\"label\":\"Bon Appe, Nutella, Gourmet Fudge Brownie\",\"nutrients\":{\"ENERC_KCAL\":410,\"PROCNT\":5.130000114440918,\"FAT\":20.510000228881836,\"CHOCDF\":53.849998474121094,\"FIBTG\":2.5999999046325684},\"brand\":\"Nutella\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"SUGAR; NUTELLA (SUGAR; PALM OIL; HAZELNUTS; COCOA; SKIM MILK; WHEY; SOY LECITHIN; VANILLIN); ENRICHED BLEACHED FLOUR (WHEAT FLOUR; CHLORINE; NIACIN; BENZOYL PEROXIDE; REDUCED IRON; THIAMINE MONONITRATE; RIBOFLAVIN; FOLIC ACID); SOYBEAN AND/OR CANOLA OIL; EGGS; WATER; COCOA POWDER (ALKALIZED); INVERT SUGAR; SALT; ARTIFICIAL FLAVOR.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_slice\",\"label\":\"Slice\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_bb0042jbjvdqatacn9c4cas5trmz\",\"label\":\"Gogo Squeez Applesauce Vty App/Cinn 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Cinnamon : Apple; Apple Puree Concentrate; Lemon Juice Concentrate; Cinnamon.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_aiholehb8m60fbbvql02mabvp8m0\",\"label\":\"Gogo Squeez Applesauce Vty App/Ban/Mang 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Banana : Apple; Banana; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Mango : Apple; Mango; Apple Puree Concentrate; Lemon Juice Concentrate.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_bbs5150aqik3g0ak8voa9ad6xrke\",\"label\":\"Gogo Squeez Applesauce Vty App/Pch/Gimme5 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Peach : Apple; Peach; Apple Puree Concentrate; Lemon Juice Concentrate. Gimme Five : Apple; Apple Puree Concentrate; Mango; Strawberry; Peach; Banana.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_b7nwtlybbcgs6dbrmtj1jafrz50q\",\"label\":\"Gogo Squeez Applesauce Vty App/Ban/Straw 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Banana : Apple; Banana; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Strawberry : Apple; Apple Puree Concentrate; Strawberry; Blackcurrant.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]}]}");
        sb.append("app");
        foodSearchCommand.execute();
        FoodItemData food = model.getFoodItems().get(0);
        lenient().when(foodApi.foodNutritionSearchRequest(food.getMeasureUri(), food.getFoodId())).thenReturn("{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#c425cd07-c662-4c9b-9403-d9c2816c85f1\",\"calories\":159,\"totalWeight\":39,\"dietLabels\":[],\"healthLabels\":[\"VEGETARIAN\",\"PESCATARIAN\",\"PEANUT_FREE\",\"FISH_FREE\",\"SHELLFISH_FREE\",\"PORK_FREE\",\"RED_MEAT_FREE\",\"CRUSTACEAN_FREE\",\"CELERY_FREE\",\"MUSTARD_FREE\",\"SESAME_FREE\",\"LUPINE_FREE\",\"MOLLUSK_FREE\",\"ALCOHOL_FREE\",\"KOSHER\"],\"cautions\":[\"SULFITES\",\"FODMAP\"],\"totalNutrients\":{\"ENERC_KCAL\":{\"label\":\"Energy\",\"quantity\":159.9,\"unit\":\"kcal\"},\"FAT\":{\"label\":\"Fat\",\"quantity\":7.998900089263916,\"unit\":\"g\"},\"FASAT\":{\"label\":\"Saturated\",\"quantity\":1.5014999628067016,\"unit\":\"g\"},\"FATRN\":{\"label\":\"Trans\",\"quantity\":0,\"unit\":\"g\"},\"CHOCDF\":{\"label\":\"Carbs\",\"quantity\":21.00149940490723,\"unit\":\"g\"},\"FIBTG\":{\"label\":\"Fiber\",\"quantity\":1.0139999628067018,\"unit\":\"g\"},\"SUGAR\":{\"label\":\"Sugars\",\"quantity\":17.00010005950928,\"unit\":\"g\"},\"PROCNT\":{\"label\":\"Protein\",\"quantity\":2.0007000446319583,\"unit\":\"g\"},\"CHOLE\":{\"label\":\"Cholesterol\",\"quantity\":19.89,\"unit\":\"mg\"},\"NA\":{\"label\":\"Sodium\",\"quantity\":74.88,\"unit\":\"mg\"},\"CA\":{\"label\":\"Calcium\",\"quantity\":19.89,\"unit\":\"mg\"},\"FE\":{\"label\":\"Iron\",\"quantity\":0.3588000065088272,\"unit\":\"mg\"},\"VITC\":{\"label\":\"Vitamin C\",\"quantity\":0,\"unit\":\"mg\"}},\"totalDaily\":{\"ENERC_KCAL\":{\"label\":\"Energy\",\"quantity\":7.995,\"unit\":\"%\"},\"FAT\":{\"label\":\"Fat\",\"quantity\":12.306000137329102,\"unit\":\"%\"},\"FASAT\":{\"label\":\"Saturated\",\"quantity\":7.507499814033508,\"unit\":\"%\"},\"CHOCDF\":{\"label\":\"Carbs\",\"quantity\":7.000499801635742,\"unit\":\"%\"},\"FIBTG\":{\"label\":\"Fiber\",\"quantity\":4.055999851226807,\"unit\":\"%\"},\"PROCNT\":{\"label\":\"Protein\",\"quantity\":4.0014000892639165,\"unit\":\"%\"},\"CHOLE\":{\"label\":\"Cholesterol\",\"quantity\":6.63,\"unit\":\"%\"},\"NA\":{\"label\":\"Sodium\",\"quantity\":3.12,\"unit\":\"%\"},\"CA\":{\"label\":\"Calcium\",\"quantity\":1.989,\"unit\":\"%\"},\"FE\":{\"label\":\"Iron\",\"quantity\":1.9933333694934845,\"unit\":\"%\"},\"VITC\":{\"label\":\"Vitamin C\",\"quantity\":0,\"unit\":\"%\"}},\"ingredients\":[{\"parsed\":[{\"quantity\":1,\"measure\":\"serving\",\"food\":\"BON APPE, NUTELLA, GOURMET FUDGE BROWNIE\",\"foodId\":\"food_bzvzpk8bjqsr7wa9fimylaz2j78u\",\"foodContentsLabel\":\"SUGAR; NUTELLA (SUGAR; PALM OIL; HAZELNUTS; COCOA; SKIM MILK; WHEY; SOY LECITHIN; VANILLIN); ENRICHED BLEACHED FLOUR (WHEAT FLOUR; CHLORINE; NIACIN; BENZOYL PEROXIDE; REDUCED IRON; THIAMINE MONONITRATE; RIBOFLAVIN; FOLIC ACID); SOYBEAN AND/OR CANOLA OIL; EGGS; WATER; COCOA POWDER (ALKALIZED); INVERT SUGAR; SALT; ARTIFICIAL FLAVOR.\",\"weight\":39,\"retainedWeight\":39,\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":39},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_slice\",\"label\":\"Slice\",\"quantity\":0.05000000074505806}],\"measureURI\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"status\":\"OK\"}]}]}");
        FoodItemNutrientData foodItemNutrientData = model.getFoodItemNutrientData(foodApi.foodNutritionSearchRequest(food.getMeasureUri(), food.getFoodId()));
        String reportData = model.prepareNutritionReportData(foodItemNutrientData);
        sb.delete(0, sb.length());
        nutrientSearchCommand = new FoodItemNutrientSearchQuery(model, sb, food);
        nutrientSearchCommand.execute();
        assertEquals(reportData, sb.toString());
        sb.delete(0, sb.length());
        lenient().when(imgurApi.uploadNutritionReportToImgur(reportData)).thenReturn("https://i.imgur.com/z8ibd3u.png");
        uploadReportCommand = new UploadNutritionReportToImgurQuery(model, sb);
        uploadReportCommand.execute();
        assertEquals(sb.toString(), "https://i.imgur.com/z8ibd3u.png");
    }

    /**
     *
     */
    @Test
    public void testNutrientSearchReturnsErrorMessage() {
        FoodItemData fd = new FoodItemDataImpl();
        fd.setMeasureUri("blah");
        fd.setFoodId("blah!");
        nutrientSearchCommand = new FoodItemNutrientSearchQuery(model, sb, fd);
        lenient().when(foodApi.foodNutritionSearchRequest(anyString(), anyString())).thenReturn("error!");
        sb.append("app");
        nutrientSearchCommand.execute();
        assertEquals("error!", sb.toString());
    }

    @Test
    public void testNutrientSearchAndUploadReturnsImgurLinkAndUpdatesItemForDummyApi() {


        model = new ModelFacadeImpl(foodDummyApi, imgurDummyApi, db, jsonParser, reportParserImpl);
        foodSearchCommand = new FoodItemSearchQuery(model, sb);

        lenient().when(foodDummyApi.foodSearchRequest("null")).thenReturn("{\"text\":\"null\",\"parsed\":[]," +
                "\"hints\":[{\"food\":{\"foodId\":\"food_boiukwdakoy1sbbnwa1j1azs1az0\",\"label\":\"Frigo Cheese " +
                "Heads String Cheese Swirls - 8 CT\",\"nutrients\":{\"ENERC_KCAL\":291.6666666666667,\"PROCNT\":20.833333333333336,\"FIBTG\":12.5},\"brand\":\"null\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Pasteurized Part Skim Milk; Cream and Skim Milk; Cheese Cultures; Salt; Annatto Color; Enzymes.\",\"image\":\"https://www.edamam.com/food-img/be2/be295cf902e2edca068ab790a049cc18.jpg\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_container\",\"label\":\"Container\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]}]}");

        sb.append("null");
        foodSearchCommand.execute();
        FoodItemData food = model.getFoodItems().get(0);

        lenient().when(foodDummyApi.foodNutritionSearchRequest(food.getMeasureUri(), food.getFoodId())).thenReturn("{\"uri" +
                "\" : \"http://www.edamam.com/ontologies/edamam.owl#e42f9f17-95ab-4946-bcc5-f0d24d95589e\"," +
                "\"calories\" : 2916,\"totalWeight\" : 1000.0,\"dietLabels\" : [ ],\"healthLabels\" : [ \"FAT_FREE\", \"LOW_SUGAR\", \"KETO_FRIENDLY\", \"VEGETARIAN\", \"PESCATARIAN\", \"GLUTEN_FREE\", \"WHEAT_FREE\", \"EGG_FREE\", \"PEANUT_FREE\", \"TREE_NUT_FREE\", \"SOY_FREE\", \"FISH_FREE\", \"SHELLFISH_FREE\", \"PORK_FREE\", \"RED_MEAT_FREE\", \"CRUSTACEAN_FREE\", \"CELERY_FREE\", \"MUSTARD_FREE\", \"SESAME_FREE\", \"LUPINE_FREE\", \"MOLLUSK_FREE\", \"ALCOHOL_FREE\", \"NO_OIL_ADDED\", \"NO_SUGAR_ADDED\", \"KOSHER\" ],\"cautions\" : [ \"SULFITES\" ],\"totalNutrients\" : {\"ENERC_KCAL\" : {\"label\" : \"Energy\",\"quantity\" : 2916.666666666667,\"unit\" : \"kcal\"},\"FASAT\" : {\"label\" : \"Saturated\",\"quantity\" : 0.0,\"unit\" : \"g\"},\"FIBTG\" : {\"label\" : \"Fiber\",\"quantity\" : 125.0,\"unit\" : \"g\"},\"SUGAR\" : {\"label\" : \"Sugars\",\"quantity\" : 0.0,\"unit\" : \"g\"},\"SUGAR.added\" : {\"label\" : \"Sugars, added\",\"quantity\" : 416.66666666666674,\"unit\" : \"g\"},\"PROCNT\" : {\"label\" : \"Protein\",\"quantity\" : 208.33333333333337,\"unit\" : \"g\"},\"CHOLE\" : {\"label\" : \"Cholesterol\",\"quantity\" : 0.0,\"unit\" : \"mg\"},\"NA\" : {\"label\" : \"Sodium\",\"quantity\" : 7083.333333333334,\"unit\" : \"mg\"}},\"totalDaily\" : {\"ENERC_KCAL\" : {\"label\" : \"Energy\",\"quantity\" : 145.83333333333334,\"unit\" : \"%\"},\"FASAT\" : {\"label\" : \"Saturated\",\"quantity\" : 0.0,\"unit\" : \"%\"},\"FIBTG\" : {\"label\" : \"Fiber\",\"quantity\" : 500.0,\"unit\" : \"%\"},\"PROCNT\" : {\"label\" : \"Protein\",\"quantity\" : 416.66666666666674,\"unit\" : \"%\"},\"CHOLE\" : {\"label\" : \"Cholesterol\",\"quantity\" : 0.0,\"unit\" : \"%\"},\"NA\" : {\"label\" : \"Sodium\",\"quantity\" : 295.1388888888889,\"unit\" : \"%\"}},\"ingredients\" : [ {\"parsed\" : [ {\"quantity\" : 1.0,\"measure\" : \"kilogram\",\"food\" : \"Frigo Cheese Heads String Cheese Swirls - 8 CT\",\"foodId\" : \"food_boiukwdakoy1sbbnwa1j1azs1az0\",\"foodContentsLabel\" : \"Pasteurized Part Skim Milk; Cream and Skim Milk; Cheese Cultures; Salt; Annatto Color; Enzymes.\",\"weight\" : 1000.0,\"retainedWeight\" : 1000.0,\"servingSizes\" : [ {\"uri\" : \"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\" : \"Gram\",\"quantity\" : 24.0} ],\"servingsPerContainer\" : 1.0,\"measureURI\" : \"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"status\" : \"OK\"} ]} ]}\n");


        FoodItemNutrientData foodItemNutrientData = model.getFoodItemNutrientData(foodDummyApi.foodNutritionSearchRequest(food.getMeasureUri(), food.getFoodId()));
        String reportData = model.prepareNutritionReportData(foodItemNutrientData);

        sb.delete(0, sb.length());
        nutrientSearchCommand = new FoodItemNutrientSearchQuery(model, sb, food);
        nutrientSearchCommand.execute();
        assertEquals(reportData, sb.toString());

        lenient().when(imgurDummyApi.uploadNutritionReportToImgur(reportData)).thenReturn("https://i.imgur.com/z8ibd3u.png");
        sb.delete(0, sb.length());
        uploadReportCommand = new UploadNutritionReportToImgurQuery(model, sb);
        uploadReportCommand.execute();
        assertEquals(sb.toString(), "https://i.imgur.com/z8ibd3u.png");
    }

    @Test
    public void testFoodSearchCommandReturnsStrAndParsesList() {
        lenient().when(foodApi.foodSearchRequest("app")).thenReturn("{\"text\":\"app\",\"parsed\":[],\"hints\":[{\"food\":{\"foodId\":\"food_bzvzpk8bjqsr7wa9fimylaz2j78u\",\"label\":\"Bon Appe, Nutella, Gourmet Fudge Brownie\",\"nutrients\":{\"ENERC_KCAL\":410,\"PROCNT\":5.130000114440918,\"FAT\":20.510000228881836,\"CHOCDF\":53.849998474121094,\"FIBTG\":2.5999999046325684},\"brand\":\"Nutella\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"SUGAR; NUTELLA (SUGAR; PALM OIL; HAZELNUTS; COCOA; SKIM MILK; WHEY; SOY LECITHIN; VANILLIN); ENRICHED BLEACHED FLOUR (WHEAT FLOUR; CHLORINE; NIACIN; BENZOYL PEROXIDE; REDUCED IRON; THIAMINE MONONITRATE; RIBOFLAVIN; FOLIC ACID); SOYBEAN AND/OR CANOLA OIL; EGGS; WATER; COCOA POWDER (ALKALIZED); INVERT SUGAR; SALT; ARTIFICIAL FLAVOR.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_slice\",\"label\":\"Slice\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_bb0042jbjvdqatacn9c4cas5trmz\",\"label\":\"Gogo Squeez Applesauce Vty App/Cinn 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Cinnamon : Apple; Apple Puree Concentrate; Lemon Juice Concentrate; Cinnamon.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_aiholehb8m60fbbvql02mabvp8m0\",\"label\":\"Gogo Squeez Applesauce Vty App/Ban/Mang 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Banana : Apple; Banana; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Mango : Apple; Mango; Apple Puree Concentrate; Lemon Juice Concentrate.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_bbs5150aqik3g0ak8voa9ad6xrke\",\"label\":\"Gogo Squeez Applesauce Vty App/Pch/Gimme5 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Peach : Apple; Peach; Apple Puree Concentrate; Lemon Juice Concentrate. Gimme Five : Apple; Apple Puree Concentrate; Mango; Strawberry; Peach; Banana.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_b7nwtlybbcgs6dbrmtj1jafrz50q\",\"label\":\"Gogo Squeez Applesauce Vty App/Ban/Straw 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Banana : Apple; Banana; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Strawberry : Apple; Apple Puree Concentrate; Strawberry; Blackcurrant.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]}]}");

        String expectedResponse = "{\"text\":\"app\",\"parsed\":[]," +
                "\"hints\":[{\"food\":{\"foodId\":\"food_bzvzpk8bjqsr7wa9fimylaz2j78u\",\"label\":\"Bon Appe, Nutella, Gourmet Fudge Brownie\",\"nutrients\":{\"ENERC_KCAL\":410,\"PROCNT\":5.130000114440918,\"FAT\":20.510000228881836,\"CHOCDF\":53.849998474121094,\"FIBTG\":2.5999999046325684},\"brand\":\"Nutella\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"SUGAR; NUTELLA (SUGAR; PALM OIL; HAZELNUTS; COCOA; SKIM MILK; WHEY; SOY LECITHIN; VANILLIN); ENRICHED BLEACHED FLOUR (WHEAT FLOUR; CHLORINE; NIACIN; BENZOYL PEROXIDE; REDUCED IRON; THIAMINE MONONITRATE; RIBOFLAVIN; FOLIC ACID); SOYBEAN AND/OR CANOLA OIL; EGGS; WATER; COCOA POWDER (ALKALIZED); INVERT SUGAR; SALT; ARTIFICIAL FLAVOR.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_slice\",\"label\":\"Slice\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_bb0042jbjvdqatacn9c4cas5trmz\",\"label\":\"Gogo Squeez Applesauce Vty App/Cinn 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Cinnamon : Apple; Apple Puree Concentrate; Lemon Juice Concentrate; Cinnamon.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_aiholehb8m60fbbvql02mabvp8m0\",\"label\":\"Gogo Squeez Applesauce Vty App/Ban/Mang 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Banana : Apple; Banana; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Mango : Apple; Mango; Apple Puree Concentrate; Lemon Juice Concentrate.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_bbs5150aqik3g0ak8voa9ad6xrke\",\"label\":\"Gogo Squeez Applesauce Vty App/Pch/Gimme5 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Peach : Apple; Peach; Apple Puree Concentrate; Lemon Juice Concentrate. Gimme Five : Apple; Apple Puree Concentrate; Mango; Strawberry; Peach; Banana.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_unit\",\"label\":\"Whole\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_b7nwtlybbcgs6dbrmtj1jafrz50q\",\"label\":\"Gogo Squeez Applesauce Vty App/Ban/Straw 3.2oz 20pk\",\"nutrients\":{\"ENERC_KCAL\":77.77777777777779,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":17.77777777777778,\"FIBTG\":3.3333333333333335},\"brand\":\"GoGo Squeez\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Apple Apple : Apple; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Banana : Apple; Banana; Apple Puree Concentrate; Lemon Juice Concentrate. Apple Strawberry : Apple; Apple Puree Concentrate; Strawberry; Blackcurrant.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]}]}";

        sb.append("app");
        foodSearchCommand.execute();
        assertEquals(expectedResponse, sb.toString());

        List<FoodItemData> data = model.getFoodItems();

        assertEquals(5, data.size());

        for (FoodItemData fd : data) {
            assertEquals("N/A", fd.getImageLink());
        }

        FoodItemData foodItem1 = data.get(0);
        assertEquals("food_bzvzpk8bjqsr7wa9fimylaz2j78u", foodItem1.getFoodId());
        assertEquals("Bon Appe, Nutella, Gourmet Fudge Brownie", foodItem1.getFoodLabel());
        assertEquals("Packaged foods", foodItem1.getCategory());
        assertEquals("Nutella", foodItem1.getBrand());
        assertEquals("food", foodItem1.getCategoryLabel());
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_serving", foodItem1.getMeasureUri());
        assertEquals("Serving", foodItem1.getMeasure());
        assertEquals("Serving, Slice, Gram, Ounce, Pound, Kilogram", StringUtils.join(foodItem1.getMeasures(), ", "));

        FoodItemData foodItem2 = data.get(1);
        assertEquals("food_bb0042jbjvdqatacn9c4cas5trmz", foodItem2.getFoodId());
        assertEquals("Gogo Squeez Applesauce Vty App/Cinn 3.2oz 20pk", foodItem2.getFoodLabel());
        assertEquals("Packaged foods", foodItem2.getCategory());
        assertEquals("GoGo Squeez", foodItem2.getBrand());
        assertEquals("food", foodItem2.getCategoryLabel());
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_serving", foodItem2.getMeasureUri());
        assertEquals("Serving", foodItem2.getMeasure());
        assertEquals("Serving, Whole, Package, Gram, Ounce, Pound, Kilogram", StringUtils.join(foodItem2.getMeasures(), ", "));

        FoodItemData foodItem3 = data.get(2);
        assertEquals("food_aiholehb8m60fbbvql02mabvp8m0", foodItem3.getFoodId());
        assertEquals("Gogo Squeez Applesauce Vty App/Ban/Mang 3.2oz 20pk", foodItem3.getFoodLabel());
        assertEquals("Packaged foods", foodItem3.getCategory());
        assertEquals("GoGo Squeez", foodItem3.getBrand());
        assertEquals("food", foodItem3.getCategoryLabel());
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_serving", foodItem3.getMeasureUri());
        assertEquals("Serving", foodItem3.getMeasure());
        assertEquals("Serving, Whole, Package, Gram, Ounce, Pound, Kilogram",
                StringUtils.join(foodItem3.getMeasures(), ", "));

        FoodItemData foodItem4 = data.get(3);
        assertEquals("food_bbs5150aqik3g0ak8voa9ad6xrke", foodItem4.getFoodId());
        assertEquals("Gogo Squeez Applesauce Vty App/Pch/Gimme5 3.2oz 20pk", foodItem4.getFoodLabel());
        assertEquals("Packaged foods", foodItem4.getCategory());
        assertEquals("GoGo Squeez", foodItem4.getBrand());
        assertEquals("food", foodItem4.getCategoryLabel());
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_serving", foodItem4.getMeasureUri());
        assertEquals("Serving", foodItem4.getMeasure());
        assertEquals("Serving, Whole, Package, Gram, Ounce, Pound, Kilogram",
                StringUtils.join(foodItem4.getMeasures(), ", "));

        FoodItemData foodItem5 = data.get(4);
        assertEquals("food_b7nwtlybbcgs6dbrmtj1jafrz50q", foodItem5.getFoodId());
        assertEquals("Gogo Squeez Applesauce Vty App/Ban/Straw 3.2oz 20pk", foodItem5.getFoodLabel());
        assertEquals("Packaged foods", foodItem5.getCategory());
        assertEquals("GoGo Squeez", foodItem5.getBrand());
        assertEquals("food", foodItem5.getCategoryLabel());
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_serving", foodItem5.getMeasureUri());
        assertEquals("Serving", foodItem5.getMeasure());
        assertEquals("Serving, Gram, Ounce, Pound, Kilogram",
                StringUtils.join(foodItem5.getMeasures(), ", "));

    }

    @Test
    public void testFoodSearchCommandReactToErroneousFoodApiMessage() {
        lenient().when(foodApi.foodSearchRequest("app")).thenReturn("danger");
        sb.append("app");
        foodSearchCommand.execute();
        assertEquals("Your request could not be processed! Please try again and make sure you have a stable internet connection.", sb.toString());
        assertTrue(model.getFoodItems().isEmpty());
    }


    @Test
    public void testFoodSearchCommandReturnsStrAndParsesListForDummyApi() {

        model = new ModelFacadeImpl(foodDummyApi, imgurApi, db, jsonParser, reportParserImpl);
        foodSearchCommand = new FoodItemSearchQuery(model, sb);

        lenient().when(foodDummyApi.foodSearchRequest("null")).thenReturn("{\"text\":\"null\",\"parsed\":[]," +
                "\"hints\":[{\"food\":{\"foodId\":\"food_boiukwdakoy1sbbnwa1j1azs1az0\",\"label\":\"Frigo Cheese " +
                "Heads String Cheese Swirls - 8 CT\",\"nutrients\":{\"ENERC_KCAL\":291.6666666666667,\"PROCNT\":20.833333333333336,\"FIBTG\":12.5},\"brand\":\"null\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Pasteurized Part Skim Milk; Cream and Skim Milk; Cheese Cultures; Salt; Annatto Color; Enzymes.\",\"image\":\"https://www.edamam.com/food-img/be2/be295cf902e2edca068ab790a049cc18.jpg\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_container\",\"label\":\"Container\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]}]}");

        String expectedResponse = "{\"text\":\"null\",\"parsed\":[],\"hints\":[{\"food\":{\"foodId\":\"food_boiukwdakoy1sbbnwa1j1azs1az0\",\"label\":\"Frigo Cheese Heads String Cheese Swirls - 8 CT\",\"nutrients\":{\"ENERC_KCAL\":291.6666666666667,\"PROCNT\":20.833333333333336,\"FIBTG\":12.5},\"brand\":\"null\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Pasteurized Part Skim Milk; Cream and Skim Milk; Cheese Cultures; Salt; Annatto Color; Enzymes.\",\"image\":\"https://www.edamam.com/food-img/be2/be295cf902e2edca068ab790a049cc18.jpg\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_container\",\"label\":\"Container\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]}]}";

        sb.append("null");
        foodSearchCommand.execute();
        assertEquals(expectedResponse, sb.toString());

        assertEquals(1, model.getFoodItems().size());
        FoodItemData foodItem = model.getFoodItems().get(0);
        assertNotNull(foodItem);
        assertEquals("food_boiukwdakoy1sbbnwa1j1azs1az0", foodItem.getFoodId());
        assertEquals("Frigo Cheese Heads String Cheese Swirls - 8 CT", foodItem.getFoodLabel());
        assertEquals("Packaged foods", foodItem.getCategory());
        assertEquals("null", foodItem.getBrand());
        assertEquals("food", foodItem.getCategoryLabel());
        assertEquals("http://www.edamam.com/ontologies/edamam.owl#Measure_serving", foodItem.getMeasureUri());
        assertEquals("Serving", foodItem.getMeasure());
        assertEquals("https://www.edamam.com/food-img/be2/be295cf902e2edca068ab790a049cc18.jpg", foodItem.getImageLink());
        assertEquals(7, foodItem.getMeasures().size());
        assertEquals(3, foodItem.getBasicNutrients().size());

    }

    @Test
    public void testSettersAndGettersForFoodItemsInFacade() {
        List<FoodItemData> f = new ArrayList<>();
        FoodItemData f1 = new FoodItemDataImpl();
        FoodItemData f2 = new FoodItemDataImpl();
        f.add(f1);
        f.add(f2);
        model.setFoodItems(f);
        assertEquals(f, model.getFoodItems());
    }


}
