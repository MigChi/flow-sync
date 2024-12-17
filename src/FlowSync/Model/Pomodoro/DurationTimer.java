package FlowSync.Model.Pomodoro;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import FlowSync.Model.Signal.Receivable;

/**
 * Implements a countdown timer based on a fixed duration.
 * <p>
 * The {@code DurationTimer} class provides functionality to start, pause, and reset a
 * timer asynchronously using the {@link Timer} class. It tracks its state (running/paused),
 * supports dynamic duration updates, and notifies a parent object when the countdown completes.
 */
public class DurationTimer implements TimerLogic {
  private Timer timer;            // Internal timer for scheduling tasks
  private Duration initialTime;   // The original duration set for the timer
  private long timeRemaining;     // Remaining time in seconds
  private TimerTask currentTask;  // The active countdown task
  private boolean isRunning;      // Tracks whether the timer is active
  private Receivable parent;      // Parent object to notify upon timer completion

  /**
   * Constructs a {@code DurationTimer} with the specified hours, minutes, and seconds.
   * <p>
   * Initializes the timer with the given duration and prepares it for operation.
   *
   * @param hours   The number of hours for the timer.
   * @param minutes The number of minutes for the timer.
   * @param seconds The number of seconds for the timer.
   * @throws IllegalArgumentException if any of the time values are negative.
   */
  public DurationTimer(int hours, int minutes, int seconds) {
    if (hours < 0 || minutes < 0 || seconds < 0) {
      throw new IllegalArgumentException("Time values must be non-negative.");
    }
    this.timer = new Timer();
    this.initialTime = Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    this.isRunning = false;
    this.timeRemaining = this.initialTime.getSeconds();
    this.currentTask = null;
    this.parent = null;
  }

  /**
   * Returns the time remaining on the timer in seconds.
   *
   * @return the time remaining, in seconds
   */
  @Override
  public long getTimeRemaining() {
    return this.timeRemaining;
  }

  /**
   * Assigns a parent object to receive notifications upon timer completion.
   *
   * @param parent The {@link Receivable} object to notify.
   */
  @Override
  public void setParent(Receivable parent) {
    this.parent = parent;
  }

  /**
   * Updates the duration of the timer.
   * <p>
   * Changes the timer's countdown duration. If the timer is running, the new duration
   * will take effect after it is reset.
   *
   * @param hours   The new number of hours.
   * @param minutes The new number of minutes.
   * @throws IllegalArgumentException if hours or minutes are negative.
   */
  @Override
  public void setDuration(int hours, int minutes) {
    if (hours < 0 || minutes < 0) {
      throw new IllegalArgumentException("Time values must be non-negative.");
    }
    this.initialTime = Duration.ofHours(hours).plusMinutes(minutes);
    this.timeRemaining = this.initialTime.getSeconds();
  }

  /**
   * Starts the timer countdown.
   * <p>
   * If the timer is not running and has a valid duration, it begins decrementing time
   * every second. Upon reaching zero, the timer notifies its parent.
   */
  @Override
  public synchronized void startTimer() {
    if (!this.isRunning && this.timeRemaining > 0 && this.parent != null) {
      this.isRunning = true;
      this.scheduleDuration();
    } else {
      System.out.println("Timer is already running/or parent is null");
    }
  }

  /**
   * Pauses the active timer.
   * <p>
   * Stops the current countdown task while preserving the remaining time.
   */
  @Override
  public synchronized void pauseTimer() {
    if (this.isRunning && this.parent != null) {
      System.out.println("Pausing timer.");
      this.isRunning = false;
      this.currentTask.cancel();
      this.currentTask = null;
    } else {
      System.out.println("Timer is already paused/or parent is null");
    }
  }

  /**
   * Resets the timer to its initial state.
   * <p>
   * Stops the current countdown task and restores the time to the initial duration.
   */
  @Override
  public synchronized void resetTimer() {
    if (this.isRunning) {
      this.pauseTimer();
    }
    System.out.println("Resetting timer.");
    this.timer.purge();
    this.timeRemaining = this.initialTime.getSeconds();
  }

  /**
   * Internal method that schedules the countdown task.
   * <p>
   * This method initializes a {@link TimerTask} to decrement the timer every second
   * and notifies the parent when the countdown finishes.
   */
  private synchronized void scheduleDuration() {
    if (this.currentTask != null) {
      this.currentTask.cancel();
    }

    System.out.println("Starting Timer:");
    Receivable parent = this.parent;
    this.currentTask = new TimerTask() {
      @Override
      public void run() {
        System.out.println(timeRemaining);
        timeRemaining--;
        if (timeRemaining <= 0) {
          System.out.println("Ending Timer.");
          parent.receive("finish");
          isRunning = false;
          cancel();
        }
      }
    };
    this.timer.schedule(this.currentTask, 0, 1000);
  }
}
