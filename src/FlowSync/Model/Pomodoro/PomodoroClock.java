package FlowSync.Model.Pomodoro;

/**
 * This class represents the functional behavior of a pomodoro clock:
 * - start active timer
 * - stop active timer
 * - edit a timer
 * - switch active timer
 * - edit a timer
 * - restart the pomodoro
 * - edit work cycles
 *  */

public interface PomodoroClock {

  /**
   * This method starts the current timer.
   *
   */
  void startTimer();

  /**
   * This method stops the current timer.
   */
  void pauseTimer();

  /**
   * This method resumes the current timer.
   */
  void resumeTimer();

  /**
   * This method edits the duration of a specified timer.
   *
   * @param timerName
   * @param hours
   * @param minutes
   */
  void editTimer(String timerName, int hours, int minutes);


  void switchActiveTimer();

  /**
   * This method edits the number of work cycles.
   *
   * @param numberOfWorkCycles, the number of work cycles to complete.
   */
  void editWorkCycles(int numberOfWorkCycles);

  /**
   * This method resets the pomodoro to its original state.
   *
   * @param numberOfWorkCycles,the number of work cycles to complete.
   */
  void resetPomodoro(int numberOfWorkCycles);
}
