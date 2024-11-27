package FlowSync.Model.Pomodoro;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import FlowSync.Model.Signal.Receivable;

/**
 * The {@code DurationTimer} class implements a timer based on a specified duration.
 * It allows starting, pausing, resetting, and retrieving the time remaining.
 *
 * <p>This timer runs asynchronously using the {@link Timer} class and supports
 * initialization with hours, minutes, and seconds. The timer tracks its state
 * (running or paused) and allows modifications to the duration.
 *
 * <p><b>Usage:</b>
 * <pre>{@code
 * DurationTimer timer = new DurationTimer(1, 30, 0); // 1 hour 30 minutes
 * timer.startTimer(); // Starts the timer
 * }</pre>
 */
public class DurationTimer implements TimerLogic {
  private Timer timer;
  private Duration initialTime;
  private long timeRemaining;
  private TimerTask currentTask;
  private boolean isRunning;
  private Receivable parent;

  /**
   * Constructs a {@code DurationTimer} with the specified hours, minutes, and seconds.
   *
   * @param hours   the number of hours for the timer
   * @param minutes the number of minutes for the timer
   * @param seconds the number of seconds for the timer
   * @throws IllegalArgumentException if any argument is negative
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
  public long getTimeRemaining() {
    return this.timeRemaining;
  }

  /**
   * Sets the parent of this Timer to be an object of our choice
   */
  @Override
  public void setParent(Receivable parent) {
    this.parent = parent;
  }

  /**
   * Sets a new duration for the timer. This will not affect a currently running timer.
   *
   * @param hours   the number of hours to set
   * @param minutes the number of minutes to set
   * @throws IllegalArgumentException if any argument is negative
   */
  public void setDuration(int hours, int minutes) {
    if (hours < 0 || minutes < 0) {
      throw new IllegalArgumentException("Time values must be non-negative.");
    }
    this.initialTime = Duration.ofHours(hours).plusMinutes(minutes);
    this.timeRemaining = this.initialTime.getSeconds();
  }

  /**
   * Starts the timer asynchronously. If the timer is already running, it does nothing.
   */
  public synchronized void startTimer() {
    if (!this.isRunning && this.timeRemaining > 0 && this.parent != null) {
      this.isRunning = true;
      this.scheduleDuration();
    } else {
      System.out.println("Timer is already running/or parent is null");
    }
  }

  /**
   * Pauses the timer if it is running. If the timer is already paused, it does nothing.
   */
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
   * Resets the timer to its initial duration. If the timer is running, it will be paused first.
   */
  public synchronized void resetTimer() {
    if (this.isRunning) {
      this.pauseTimer();
    }
    System.out.println("Resetting timer.");
    this.timer.purge();
    this.timeRemaining = this.initialTime.getSeconds();
  }

  /**
   * Schedules the timer's countdown task to execute every second.
   * If a task is already active, it will be canceled before scheduling a new one.
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
