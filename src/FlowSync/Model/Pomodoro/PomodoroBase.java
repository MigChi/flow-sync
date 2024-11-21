package FlowSync.Model.Pomodoro;
import java.util.HashMap;

/**
 * Represents the basic features of a pomodoro clock such as timers for
 * a work interval and a break interval (short or long breaks), the ability to
 * set and keep track of work cycles, the ability to start, stop, reset, and modify the
 * timers, and lastly, a notification system that lets users know when it's time for a break, or
 * time to get back tow work.
 * The timers are simply a duration of time.
 *
 */

public class PomodoroBase implements PomodoroClock {
  private int workCycles;
  private boolean isWorkTime;
  private TimerLogic work;
  private TimerLogic short_break;
  private TimerLogic long_break;
  private final HashMap<String, TimerLogic> timers = new HashMap<>();

  //Default constructor
  public PomodoroBase(int numberOfWorkCycles) {
    resetPomodoro(numberOfWorkCycles);
  }

  public void startTimer() { //implement timer switching logic

  }

  public void pauseTimer() {
  }

  public void resumeTimer() {
  }

  public void editTimer(String timerName, int hours, int minutes) {
    switch (timerName) {
      case "work":
        this.work.setDuration(hours, minutes);
        break;
      case "short break":
        this.short_break.setDuration(hours, minutes);
        break;
      case "long break":
        this.long_break.setDuration(hours, minutes);
        break;
      default:
        //Throw an error here
        break;
    }
  }

  public void switchActiveTimer() {
    isWorkTime = !isWorkTime;
  }

  public void editWorkCycles(int numberOfWorkCycles) {
    workCycles = numberOfWorkCycles;
  }

  public void resetPomodoro(int numberOfWorkCycles) {
    this.work = new DurationTimer(0, 25, 0);
    this.short_break = new DurationTimer(0, 5, 0);
    this.long_break = new DurationTimer(0,15, 0);
    this.timers.put("work", this.work);
    this.timers.put("short break", this.short_break);
    this.timers.put("long break", this.long_break);
    this.isWorkTime = true;
    this.workCycles = numberOfWorkCycles;
  }

  //some method for keeping track of the current time
}
