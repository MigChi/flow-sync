package FlowSync.Model.Pomodoro;
import java.util.HashMap;
import FlowSync.Model.Signal.Receivable;

/**
 * Represents the basic features of a pomodoro clock such as timers for
 * a work interval and a break interval (short or long breaks), the ability to
 * set and keep track of work cycles, the ability to start, stop, reset, and modify the
 * timers, and lastly, a notification system that lets users know when it's time for a break, or
 * time to get back tow work.
 * The timers are simply a duration of time.
 *
 */

public class PomodoroBase implements PomodoroClock, Receivable {
  private int workCycles;
  private boolean isWorkTime;
  private final HashMap<String, TimerLogic> timers = new HashMap<>();
  private String currentTimer;
  private int currentCycle = 0;

  //Default constructor
  public PomodoroBase(int numberOfWorkCycles) {
    resetPomodoro(numberOfWorkCycles);
  }

  public void startTimer() { //implement timer switching logic
    TimerLogic timer = this.timers.get(currentTimer);
    timer.startTimer();
  }

  public void pauseTimer() {
    TimerLogic timer = this.timers.get(currentTimer);
    timer.pauseTimer();
  }

  public void editTimer(String timerName, int hours, int minutes) {
    this.timers.get(timerName).setDuration(hours, minutes);
  }

  public void skipTimer(String timerName) {
    this.timers.get(currentTimer).resetTimer();
    this.currentTimer = timerName;
  }

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

  public void editWorkCycles(int numberOfWorkCycles) {
    workCycles = numberOfWorkCycles;
  }

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
   * This method gets the value of the current timer.
   */
  @Override
  public long getCurrentTime() {
    TimerLogic timer = this.timers.get(currentTimer);

    return timer.getTimeRemaining();
  }

  /**
   * Invoked by another class.
   *
   * @param message optional message, can be null
   */
  @Override
  public void receive(String message) {

  }
}
