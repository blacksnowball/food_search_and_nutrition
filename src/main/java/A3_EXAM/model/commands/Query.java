package A3_EXAM.model.commands;
import A3_EXAM.model.facade.ModelFacade;

/**
 * This abstract class provides a template for creating standalone objects that encapsulates method calls related to
 * communicating with the API and database through the model facade
 */
public abstract class Query implements Command {

    protected final ModelFacade modelFacade;
    protected final StringBuilder sb;

    /**
     * Instantiates a new Query object encapsulating a method call.
     * Preconditions: None
     * Postconditiosn: None
     * @param modelFacade The model facade to make calls on
     * @param sb The StringBuilder object used to inform what calls to make and to help with yielding some return values
     */
    public Query(ModelFacade modelFacade, StringBuilder sb) {
        this.modelFacade = modelFacade;
        this.sb = sb;
    }


}
