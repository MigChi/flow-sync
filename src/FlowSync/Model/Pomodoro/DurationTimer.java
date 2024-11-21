package FlowSync.Model.Pomodoro;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

public class DurationTimer implements TimerLogic {
  private Timer timer;
  private Duration initialTime;
  private long timeRemaining;
  private TimerTask currentTask;
  private boolean isRunning;

  //make sure I add conditions for how to initialize a timer.
  public DurationTimer(int hours, int minutes, int seconds) {
    this.timer = new Timer();
    this.initialTime = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    this.isRunning = false;
    this.timeRemaining = this.initialTime.getSeconds();
    this.currentTask = null;
  }

  public long getTimeRemaining() {
    return this.timeRemaining;
  }

  public boolean isCompleted() {return this.timeRemaining == 0;}

  //set new duration amount
  public void setDuration(int hours, int minutes) {
    this.initialTime = Duration.ofHours(hours).plusMinutes(minutes);
    this.timeRemaining = this.initialTime.getSeconds();
  }

  //start the duration timer
  public synchronized void startTimer() {
    if (!this.isRunning && this.timeRemaining > 0) {
      this.isRunning = true;
      this.scheduleDuration();
    } else {
      System.out.println("Timer is already running");
    }
  }

  //pause the duration timer
  public synchronized void pauseTimer() {
    if (this.isRunning) {
      System.out.println("Pausing timer.");
      this.isRunning = false;
      this.currentTask.cancel();
      this.currentTask = null;
    } else {
      System.out.println("Timer is already paused.");
    }
  }

  //stop the timer completely
  public synchronized void resetTimer() {
    if (this.isRunning) {
      this.pauseTimer();
    }
    //check currentTask is null and canceled.
    System.out.println("Resetting timer.");
    this.timer.purge();
    this.timeRemaining = this.initialTime.getSeconds();
  }

  private synchronized void scheduleDuration() {
    if (this.currentTask != null) { //makes sure there isn't another active task
      this.currentTask.cancel();
    }

    System.out.println("Starting Timer:");
    this.currentTask = new TimerTask() {
      @Override
      public void run() {
        System.out.println(timeRemaining);
        timeRemaining--;
        if (timeRemaining <= 0) {
          System.out.println("Ending Timer.");
          isRunning = false;
          cancel();
        }
      }
    };
    this.timer.schedule(this.currentTask, 0, 1000);
  }
}
