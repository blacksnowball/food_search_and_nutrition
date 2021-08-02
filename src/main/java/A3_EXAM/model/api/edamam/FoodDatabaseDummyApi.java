package A3_EXAM.model.api.edamam;

/**
 * A concrete implementation of the FoodDatabseAPITemplate representing 'offline' access
 * to the Edamam food API.
 */
public class FoodDatabaseDummyApi extends FoodDatabaseApiTemplate {

    public FoodDatabaseDummyApi(String id, String key) {
        super(id, key);
    }

    /**
     * Emulates a call to the Edamam food API for matching items of a food query. Returns a string with a fixed
     * value, resulting in the matching items being the same regardless of the query given
     * Preconditions: None
     * Postconditions: None
     * @param query The search term used in making a query for matching food items
     * @return A string containing a fixed outcome of a query for matching items of a query as a JSON string
     */
    @Override
    public String foodSearchRequest(String query) {
        return String.format("{\"text\":\"%s\",\"parsed\":[]," +
                "\"hints\":[{\"food\":{\"foodId\":\"food_bc5dzpwbyzxcs7bgwka4va8lzajp\",\"label\":\"Bestrecipes Test " +
                "Kitchen Recipes\",\"nutrients\":{\"ENERC_KCAL\":121.8754269088463,\"PROCNT\":1.8775544280294882," +
                "\"FAT\":5.877685341302945,\"CHOCDF\":15.885042566763591,\"FIBTG\":2.1062216232630897}," +
                "\"category\":\"Generic meals\",\"categoryLabel\":\"meal\",\"foodContentsLabel\":\"mayonnaise; green " +
                "onion; salt; pepper; potatoes\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam" +
                ".owl#Measure_unit\",\"label\":\"Whole\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam" +
                ".owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam" +
                ".owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\"" +
                ",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"}" +
                ",{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":" +
                "{\"foodId\":\"food_bk6goo7bkb4utbajbrx1lav6e0vm\",\"label\":\"Doritos - Jacked Test Flavor - 2 Blue\",\"nutrients\"" +
                ":{\"ENERC_KCAL\":500,\"PROCNT\":7.142857142857143,\"FAT\":32.142857142857146,\"CHOCDF\":57.142857142857146,\"" +
                "FIBTG\":3.5714285714285716},\"brand\":\"Doritos\",\"category\":\"Packaged foods\",\"categoryLabel\":\"food\"" +
                ",\"foodContentsLabel\":\"Potatoes; Vegetable Oil ( Peanut Oil; Corn Oil; Sunflower Oil ); Salt; Monterey Jack;" +
                " Blue And Cheddar Cheese ( Pasteurized Milk; Cheese Cultures; Salt; Enzymes ); Maltodextrin; Partially " +
                "Hydrogenated Soybean Oil; Whey; Spices; Nonfat Dry Milk; Citric Acid; Natural Flavor; Disodium Inosinate" +
                " And Guanylate.\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\"," +
                "\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_chip\",\"label\":\"Ch" +
                "ip\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"" +
                "uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http:/" +
                "/www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam" +
                ".com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontolo" +
                "gies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_ak1wzakaewqa7" +
                "lbgb6r3iadtykp5\",\"label\":\"Lamb, New Zealand, Imported, Testes, Raw\",\"nutrients\":{\"ENERC_KCAL\":6" +
                "8,\"PROCNT\":11.4,\"FAT\":2.38,\"CHOCDF\":0.14,\"FIBTG\":0},\"category\":\"Generic foods\",\"categoryLab" +
                "el\":\"food\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"la" +
                "bel\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounc" +
                "e\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"ur" +
                "i\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food" +
                "\":{\"foodId\":\"food_agj0tq8a3bcw7abjn0g1pbkylwfh\",\"label\":\"Doritos - Jacked Test Flavor - 3 Yell" +
                "ow\",\"nutrients\":{\"ENERC_KCAL\":500,\"PROCNT\":7.142857142857143,\"FAT\":28.571428571428573,\"CHOCD" +
                "F\":57.142857142857146,\"FIBTG\":3.5714285714285716},\"brand\":\"Doritos\",\"category\":\"Packaged foo" +
                "ds\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Potatoes; Vegetable Oil ( Peanut Oil; Corn Oil" +
                "; Sunflower Oil ); Salt; Monterey Jack; Blue And Cheddar Cheese ( Pasteurized Milk; Cheese Cultures; S" +
                "alt; Enzymes ); Maltodextrin; Partially Hydrogenated Soybean Oil; Whey; Spices; Nonfat Dry Milk; Citri" +
                "c Acid; Natural Flavor; Disodium Inosinate And Guanylate.\"},\"measures\":[{\"uri\":\"http://www.edama" +
                "m.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/o" +
                "ntologies/edamam.owl#Measure_chip\",\"label\":\"Chip\"},{\"uri\":\"http://www.edamam.com/ontologies/ed" +
                "amam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.ow" +
                "l#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce" +
                "\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\"" +
                ":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogr" +
                "am\"}]},{\"food\":{\"foodId\":\"food_bqfr6d0b5zbddha01ldp9a1lqdv3\",\"label\":\"Lamb, New Zealand, Imp" +
                "orted, Testes, Cooked, Soaked and Fried\",\"nutrients\":{\"ENERC_KCAL\":125,\"PROCNT\":21.01,\"FAT\":4." +
                "56,\"CHOCDF\":0,\"FIBTG\":0},\"category\":\"Generic foods\",\"categoryLabel\":\"food\"},\"measures\":[" +
                "{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"" +
                "http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www" +
                ".edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.co" +
                "m/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"}]},{\"food\":{\"foodId\":\"food_aczj" +
                "d0ubsgtbpoa5b64y8bdcfosv\",\"label\":\"Legal Gear Lgp 1 - t Hydroxy - Test 8.11 fl Oz\",\"nutrients\":" +
                "{\"ENERC_KCAL\":0,\"PROCNT\":0,\"FAT\":0,\"CHOCDF\":0},\"brand\":\"Legal Gear\",\"category\":\"Package" +
                "d foods\",\"categoryLabel\":\"food\",\"foodContentsLabel\":\"Isopropyl Alcohol; Water; Acetic Acid; Pr" +
                "opylene Glycol; D - Limone; Isopropyl Myristate; Chitosan; 4 - Androstenediol; 1 - Andrsotene - 17b - " +
                "Ol - 3 - One; 4 - Androstene4; 17b - Ol - 3 - One\",\"image\":\"https://www.edamam.com/food-img/31b/31b" +
                "81d4b8ec849e53858b690a3c53d55.jpg\"},\"measures\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam." +
                "owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Mea" +
                "sure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gr" +
                "am\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_ounce\",\"label\"" +
                ":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_pound\",\"label\":\"Pound\"}" +
                ",{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_kilogram\",\"label\":\"Kilogram\"},{\"u" +
                "ri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_milliliter\",\"label\":\"Milliliter\"}]},{\"" +
                "food\":{\"foodId\":\"food_b0cv847au8oooobk5e6l0bxef5lh\",\"label\":\"Met - rx hi - Test Amino Liquid " +
                "ild Cherry 16 fl Oz\",\"nutrients\":{\"ENERC_KCAL\":202.88413621448817,\"PROCNT\":50.72103405362204,\"" +
                "FAT\":0,\"CHOCDF\":0},\"brand\":\"MET-Rx USA, Inc.\",\"category\":\"Packaged foods\",\"categoryLabel\":" +
                "\"food\",\"foodContentsLabel\":\"Purified Water; Hydrolyzed Gelatin ( Collagen ); Citric Acid; Artific" +
                "ial Flavor; Sodium Benzoate ( Preservative ); Potassium Sorbate ( Preservative ).\",\"image\":\"https:" +
                "//www.edamam.com/food-img/718/718029b9a42a7dc3a913b5873c54cc49.jpg\"},\"measures\":[{\"uri\":\"http:/" +
                "/www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"label\":\"Serving\"},{\"uri\":\"http://www.e" +
                "damam.com/ontologies/edamam.owl#Measure_package\",\"label\":\"Package\"},{\"uri\":\"http://www.edamam" +
                ".com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\"},{\"uri\":\"http://www.edamam.com/ontolo" +
                "gies/edamam.owl#Measure_ounce\",\"label\":\"Ounce\"},{\"uri\":\"http://www.edamam.com/ontologies/edam" +
                "am.owl#Measure_pound\",\"label\":\"Pound\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Meas" +
                "ure_kilogram\",\"label\":\"Kilogram\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_t" +
                "ablespoon\",\"label\":\"Tablespoon\"},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_mi" +
                "lliliter\",\"label\":\"Milliliter\"}]}]}", query);
    }


    /**
     * Emulates a query to the Edamam food API for the nutrition data of a food item. Returns a fixed string, resulting in the nutrition data being
     * the same regardless of the measure uri and food id supplied of a selected food item.
     * Preconditions: None
     * Postconditions: None
     * @param measureUri The measure uri of a food item to be searched, forming part of an API call
     * @param foodId The food id of a food item to be searched, forming part of an API call
     * @return  A string containing a fixed outcome of a query for matching items of a query as a JSON string
     */
    @Override
    public String foodNutritionSearchRequest(String measureUri, String foodId) {
        return "{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#533fe5a5-f091-4311-925c-8c837be06b82\",\"calories\":140,\"totalWeight\":28,\"dietLabels\":[],\"healthLabels\":[\"VEGETARIAN\",\"PESCATARIAN\",\"GLUTEN_FREE\",\"WHEAT_FREE\",\"EGG_FREE\",\"TREE_NUT_FREE\",\"FISH_FREE\",\"SHELLFISH_FREE\",\"PORK_FREE\",\"RED_MEAT_FREE\",\"CRUSTACEAN_FREE\",\"CELERY_FREE\",\"MUSTARD_FREE\",\"SESAME_FREE\",\"LUPINE_FREE\",\"MOLLUSK_FREE\",\"ALCOHOL_FREE\",\"KOSHER\"],\"cautions\":[\"SULFITES\"],\"totalNutrients\":{\"ENERC_KCAL\":{\"label\":\"Energy\",\"quantity\":140,\"unit\":\"kcal\"},\"FAT\":{\"label\":\"Fat\",\"quantity\":9.000000000000002,\"unit\":\"g\"},\"FASAT\":{\"label\":\"Saturated\",\"quantity\":1.5000000000000002,\"unit\":\"g\"},\"CHOCDF\":{\"label\":\"Carbs\",\"quantity\":16.000000000000004,\"unit\":\"g\"},\"FIBTG\":{\"label\":\"Fiber\",\"quantity\":1.0000000000000002,\"unit\":\"g\"},\"SUGAR\":{\"label\":\"Sugars\",\"quantity\":1.0000000000000002,\"unit\":\"g\"},\"PROCNT\":{\"label\":\"Protein\",\"quantity\":2.0000000000000004,\"unit\":\"g\"},\"CHOLE\":{\"label\":\"Cholesterol\",\"quantity\":0,\"unit\":\"mg\"},\"NA\":{\"label\":\"Sodium\",\"quantity\":240.00000000000006,\"unit\":\"mg\"},\"CA\":{\"label\":\"Calcium\",\"quantity\":20.000000000000004,\"unit\":\"mg\"},\"FE\":{\"label\":\"Iron\",\"quantity\":0.3600000143051148,\"unit\":\"mg\"},\"VITC\":{\"label\":\"Vitamin C\",\"quantity\":4.800000190734864,\"unit\":\"mg\"}},\"totalDaily\":{\"ENERC_KCAL\":{\"label\":\"Energy\",\"quantity\":7,\"unit\":\"%\"},\"FAT\":{\"label\":\"Fat\",\"quantity\":13.84615384615385,\"unit\":\"%\"},\"FASAT\":{\"label\":\"Saturated\",\"quantity\":7.500000000000002,\"unit\":\"%\"},\"CHOCDF\":{\"label\":\"Carbs\",\"quantity\":5.333333333333335,\"unit\":\"%\"},\"FIBTG\":{\"label\":\"Fiber\",\"quantity\":4.000000000000001,\"unit\":\"%\"},\"PROCNT\":{\"label\":\"Protein\",\"quantity\":4.000000000000001,\"unit\":\"%\"},\"CHOLE\":{\"label\":\"Cholesterol\",\"quantity\":0,\"unit\":\"%\"},\"NA\":{\"label\":\"Sodium\",\"quantity\":10.000000000000004,\"unit\":\"%\"},\"CA\":{\"label\":\"Calcium\",\"quantity\":2.0000000000000004,\"unit\":\"%\"},\"FE\":{\"label\":\"Iron\",\"quantity\":2.0000000794728603,\"unit\":\"%\"},\"VITC\":{\"label\":\"Vitamin C\",\"quantity\":5.3333335452609605,\"unit\":\"%\"}},\"ingredients\":[{\"parsed\":[{\"quantity\":1,\"measure\":\"serving\",\"food\":\"Doritos - Jacked Test Flavor - 2 Blue\",\"foodId\":\"food_bk6goo7bkb4utbajbrx1lav6e0vm\",\"foodContentsLabel\":\"Potatoes; Vegetable Oil ( Peanut Oil; Corn Oil; Sunflower Oil ); Salt; Monterey Jack; Blue And Cheddar Cheese ( Pasteurized Milk; Cheese Cultures; Salt; Enzymes ); Maltodextrin; Partially Hydrogenated Soybean Oil; Whey; Spices; Nonfat Dry Milk; Citric Acid; Natural Flavor; Disodium Inosinate And Guanylate.\",\"weight\":28,\"retainedWeight\":28,\"servingSizes\":[{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_gram\",\"label\":\"Gram\",\"quantity\":28},{\"uri\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_chip\",\"label\":\"Chip\",\"quantity\":13}],\"servingsPerContainer\":1,\"measureURI\":\"http://www.edamam.com/ontologies/edamam.owl#Measure_serving\",\"status\":\"OK\"}]}]}";
    }
}
