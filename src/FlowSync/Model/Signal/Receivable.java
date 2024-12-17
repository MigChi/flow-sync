package FlowSync.Model.Signal;

/**
 * Defines a contract for objects capable of receiving messages.
 * <p>
 * This interface enables communication between components in the system.
 * Any class implementing {@code Receivable} can define behavior for handling
 * incoming messages from other objects or subsystems.
 */
public interface Receivable {

  /**
   * Processes an incoming message.
   * <p>
   * Classes implementing this method can interpret or act upon the received
   * message. The {@code message} parameter is optional and may be {@code null}.
   *
   * @param message A string containing the message to process, or {@code null} if no message is provided.
   */
  void receive(String message);

}
