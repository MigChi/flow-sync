package FlowSync.Model.Pomodoro;


public interface TimerLogic {
  long getTimeRemaining();

  boolean isCompleted();

  /**
   * set new duration amount
   */
  void setDuration(int hours, int minutes);

  /**
   * start the duration timer
   */
  void startTimer();

  /**
   * pause the duration timer
   */
  void pauseTimer();

  /**
   * stop the timer completely
   */
  void resetTimer();
}
