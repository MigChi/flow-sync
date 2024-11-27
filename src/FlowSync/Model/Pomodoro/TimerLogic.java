package FlowSync.Model.Pomodoro;


import FlowSync.Model.Signal.Receivable;

public interface TimerLogic {

  /**
   * Get the time remaining
   */
  long getTimeRemaining();

  /**
   * Sets the parent of this Timer to be an object that implements Receivable
   */
  void setParent(Receivable parent);

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
