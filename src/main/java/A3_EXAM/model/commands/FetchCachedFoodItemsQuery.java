package A3_EXAM.model.commands;
import A3_EXAM.model.facade.ModelFacade;

/**
 * Concrete implementation of a query extending a command. Encapsualtes a method call for fetching cached data
 * about food items matching a query as a standalone object.
 */
public class FetchCachedFoodItemsQuery extends Query {

    /**
     * Instantiates a new Query object encapsulating a method call.
     * @param modelFacade The model facade to make calls on
     * @param sb The StringBuilder object used to inform what calls to make and to help with yielding some return values
     */
    public FetchCachedFoodItemsQuery(ModelFacade modelFacade, StringBuilder sb) {
        super(modelFacade, sb);
    }

    /**
     * Gets matching items stored as cached data from the database that match with the provided search query of the user to be made available
     * for data processing
     * Preconditions: None
     * Postconditions: The supplied list of food items will be updated to contain matching food items from cached data that correspond to the search
     * term given by the user, for use in the GUI
     */
    @Override
    public void execute() {
        modelFacade.getFoodItems().addAll(modelFacade.fetchMatchingFoodItemResults(sb.toString()));
    }
}
