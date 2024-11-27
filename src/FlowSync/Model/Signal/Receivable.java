package FlowSync.Model.Signal;

/**
 * Represents an Object that can receive messages. This allows other classes to invoke a method
 * within another
 */
public interface Receivable {

  /**
   * Invoked by another class.
   * @param message optional message, can be null
   */
  void receive(String message);

}
