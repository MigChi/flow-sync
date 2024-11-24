package FlowSync.View;

import FlowSync.Model.Pomodoro.PomodoroClock;

/**
 * An implementation of {@code TimerView} for a {@code PomodoroBase}.
 */
public class PomodoroClockView implements TimerView {

  private PomodoroClock model;

  public PomodoroClockView(PomodoroClock model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model for PomodoroClockView can't be null");
    }
  }

  @Override
  public void display() {
//    if (this.model.getActiveTimer().isCompleted()) {
//      System.out.print("\Timer: " + this.model.getActiveTimer + " seconds");
//    }
//
  }

  @Override
  public void displayHMS() {

  }
}
