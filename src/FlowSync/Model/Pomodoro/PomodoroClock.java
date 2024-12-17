package FlowSync.Model.Pomodoro;

/**
 * Specifies the core behavior and features of a Pomodoro timer system.
 * <p>
 * The {@code PomodoroClock} interface provides methods for managing and controlling
 * multiple timers, switching between work and break intervals, and tracking progress
 * across work cycles.
 */
public interface PomodoroClock {

  /**
   * Starts the currently active timer.
   * <p>
   * If the timer is already running, no action is taken.
   */
  void startTimer();

  /**
   * Pauses the currently active timer.
   * <p>
   * If the timer is already paused, no action is taken.
   */
  void pauseTimer();

  /**
   * Modifies the duration of a specified timer.
   * <p>
   * Allows dynamic adjustment of timers, such as work intervals or break periods.
   *
   * @param timerName The name of the timer to modify (e.g., "work", "short break").
   * @param hours     The number of hours to set for the timer.
   * @param minutes   The number of minutes to set for the timer.
   * @throws IllegalArgumentException if hours or minutes are negative.
   */
  void editTimer(String timerName, int hours, int minutes);

  /**
   * Switches the currently active timer to the next interval.
   * <p>
   * This method alternates between work intervals and break periods based
   * on the current cycle.
   */
  void switchActiveTimer();

  /**
   * Updates the total number of work cycles in the Pomodoro session.
   * <p>
   * A work cycle consists of one work interval followed by a short or long break.
   *
   * @param numberOfWorkCycles The total number of work cycles to set.
   */
  void editWorkCycles(int numberOfWorkCycles);

  /**
   * Resets the Pomodoro system to its initial state.
   * <p>
   * All timers are reset, and work cycles are reinitialized to the specified value.
   *
   * @param numberOfWorkCycles The initial number of work cycles for the session.
   */
  void resetPomodoro(int numberOfWorkCycles);

  /**
   * Retrieves the remaining time for the currently active timer.
   *
   * @return The time remaining, in seconds.
   */
  long getCurrentTime();

  /**
   * Immediately skips the active timer and activates the specified timer.
   * <p>
   * This method allows the user to bypass the current interval and switch
   * to a different timer (e.g., from "work" to "short break").
   *
   * @param timerName The name of the timer to switch to.
   */
  void skipTimer(String timerName);
}
