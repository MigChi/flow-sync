package FlowSync.View;

/**
 * Represents a Pomodoro view which visualizes a timer counting down.
 */
public interface TimerView {

  /**
   * Displays the current time on the {@code PomodoroClock} in HH:MM format.
   */
  void display();

  /**
   * Displays the current time on the {@code PomodoroClock} in HH:MM:SS format
   */
  void displayHMS();
}
