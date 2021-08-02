package A3_EXAM.model.commands;

/**
 * This interfaces provides a contract for command objects to perform some action based on encapsulating a method
 * call as a request
 */
public interface Command {

    /**
     * Executes some action using a model facade based on a method call made by the controller. This action will be governed by
     * what method the standalone concrete class encapsulates and represents.
     * Preconditions: None
     * Postconditions: None
     */
    void execute();
}

