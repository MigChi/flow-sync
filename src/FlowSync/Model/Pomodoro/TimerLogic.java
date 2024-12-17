package FlowSync.Model.Pomodoro;

import FlowSync.Model.Signal.Receivable;

/**
 * Defines the behavior for a timer component.
 * <p>
 * The {@code TimerLogic} interface specifies core operations required for managing
 * timers, including starting, pausing, resetting, and retrieving the remaining time.
 * Timers implementing this interface can notify a parent object (via {@link Receivable})
 * when their countdown completes.
 */
public interface TimerLogic {

  /**
   * Retrieves the time remaining on the timer.
   * <p>
   * This method provides the countdown time in seconds.
   *
   * @return The time remaining in seconds.
   */
  long getTimeRemaining();

  /**
   * Assigns a parent object that will receive notifications upon timer completion.
   *
   * @param parent An object implementing {@link Receivable} to be notified when the timer finishes.
   */
  void setParent(Receivable parent);

  /**
   * Updates the duration of the timer.
   * <p>
   * This method allows the timer's countdown duration to be modified dynamically.
   * Changes will only take effect after the timer is reset or restarted.
   *
   * @param hours   The number of hours for the new duration.
   * @param minutes The number of minutes for the new duration.
   * @throws IllegalArgumentException if hours or minutes are negative.
   */
  void setDuration(int hours, int minutes);

  /**
   * Starts the timer's countdown.
   * <p>
   * The timer begins decrementing time asynchronously and notifies its parent
   * when the countdown reaches zero. If the timer is already running, no action is taken.
   */
  void startTimer();

  /**
   * Pauses the timer's countdown.
   * <p>
   * This method halts the countdown without resetting the timer. It preserves the
   * remaining time, allowing the countdown to resume later.
   */
  void pauseTimer();

  /**
   * Resets the timer to its initial state.
   * <p>
   * This method clears the current countdown, restoring the timer to its original duration.
   * If the timer is running, it will be paused first.
   */
  void resetTimer();
}
