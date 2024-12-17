package FlowSync.Model.Pomodoro;
import java.util.HashMap;
import FlowSync.Model.Signal.Receivable;

/**
 * Implements a functional Pomodoro timer system with work and break intervals.
 * <p>
 * {@code PomodoroBase} manages three timers (work, short break, long break),
 * supports tracking work cycles, and allows control over timers via operations such as
 * start, pause, reset, skip, and edit.
 */
public class PomodoroBase implements PomodoroClock, Receivable {
  private int workCycles;     // Number of work intervals in a session
  private boolean isWorkTime; // Tracks if the current timer is for work or break
  private final HashMap<String, TimerLogic> timers = new HashMap<>(); // Timers storage
  private String currentTimer;  // The currently active timer
  private int currentCycle = 0; // Tracks the current work cycle

  /**
   * Constructs a new {@code PomodoroBase} instance and initializes timers.
   *
   * @param numberOfWorkCycles The initial number of work cycles for the session.
   */
  public PomodoroBase(int numberOfWorkCycles) {
    resetPomodoro(numberOfWorkCycles);
  }

  /**
   * Starts the currently active timer.
   */
  @Override
  public void startTimer() {
    TimerLogic timer = this.timers.get(currentTimer);
    timer.startTimer();
  }

  /**
   * Pauses the currently active timer.
   */
  @Override
  public void pauseTimer() {
    TimerLogic timer = this.timers.get(currentTimer);
    timer.pauseTimer();
  }

  /**
   * Updates the duration of a specified timer.
   *
   * @param timerName Name of the timer to update.
   * @param hours     New hour duration.
   * @param minutes   New minute duration.
   */
  @Override
  public void editTimer(String timerName, int hours, int minutes) {
    this.timers.get(timerName).setDuration(hours, minutes);
  }

  /**
   * Skips the active timer and activates the specified timer.
   *
   * @param timerName Name of the timer to switch to.
   */
  @Override
  public void skipTimer(String timerName) {
    this.timers.get(currentTimer).resetTimer();
    this.currentTimer = timerName;
  }

  /**
   * Switches the active timer based on the current cycle state.
   * <p>
   * Alternates between work intervals and short/long breaks.
   */
  @Override
  public void switchActiveTimer() {
    this.isWorkTime =! this.isWorkTime;
    if (!this.isWorkTime) {
      if (this.currentCycle % 2 == 0) {
        this.currentTimer = "long break";
      } else {
        this.currentTimer = "short break";
      }
    } else {
      this.currentTimer = "work";
    }
  }

  /**
   * Sets the total number of work cycles.
   *
   * @param numberOfWorkCycles The updated number of work cycles.
   */
  @Override
  public void editWorkCycles(int numberOfWorkCycles) {
    workCycles = numberOfWorkCycles;
  }

  /**
   * Resets the Pomodoro system to its initial configuration.
   *
   * @param numberOfWorkCycles The total number of work cycles to initialize.
   */
  @Override
  public void resetPomodoro(int numberOfWorkCycles) {
    TimerLogic work = new DurationTimer(0, 25, 0);
    TimerLogic short_break = new DurationTimer(0, 5, 0);
    TimerLogic long_break = new DurationTimer(0,15, 0);
    work.setParent(this);
    short_break.setParent(this);
    long_break.setParent(this);
    this.timers.put("work", work);
    this.timers.put("short break", short_break);
    this.timers.put("long break", long_break);
    this.isWorkTime = true;
    this.currentTimer = "work";
    this.workCycles = numberOfWorkCycles;
  }

  /**
   * Retrieves the time remaining for the active timer.
   *
   * @return Time remaining in seconds.
   */
  @Override
  public long getCurrentTime() {
    TimerLogic timer = this.timers.get(currentTimer);

    return timer.getTimeRemaining();
  }

  /**
   * Handles incoming messages. This method can be used to process events
   * such as timer completion notifications.
   *
   * @param message The received message (optional).
   */
  @Override
  public void receive(String message) {

  }
}
