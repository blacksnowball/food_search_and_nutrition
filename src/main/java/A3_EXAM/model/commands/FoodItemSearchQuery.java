package A3_EXAM.model.commands;

import A3_EXAM.model.facade.ModelFacade;


/**
 * Concrete implementation of a query extending a command. Encapsualtes a method call for getting matching food items
 * based on a user query and caching the results.
 */
public class FoodItemSearchQuery extends Query {

    /**
     * Instantiates a new Query object encapsulating a method call.
     * @param modelFacade The model facade to make calls on
     * @param sb The StringBuilder object used to inform what calls to make and to help with yielding some return values
     */
    public FoodItemSearchQuery(ModelFacade modelFacade, StringBuilder sb) {
        super(modelFacade, sb);
    }

    /**
     * Gets matching items from the Edamam API that match with the provided search query of the user and caches it, to be made available for
     * data processing
     * Preconditions: None
     * Postconditions: The supplied list of food items will be updated to contain matching food items based on an API call that correspond to the
     * search term given by the user which will also be cached in the database, for use in the GUI
     */
    @Override
    public void execute() {
        String userQuery = sb.toString();
        sb.delete(0, sb.length());
        sb.append(modelFacade.foodSearchRequest(userQuery));

        if (sb.toString().contains("{")) {
            modelFacade.cacheMatchingItemResults(userQuery, sb.toString());
        }

        modelFacade.getFoodItems().addAll(modelFacade.getFoodItemDataObjects(sb.toString()));

    }

}