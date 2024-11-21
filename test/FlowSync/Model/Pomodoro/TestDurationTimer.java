package FlowSync.Model.Pomodoro;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;


public class TestDurationTimer {
  TimerLogic durationTest;

  @Before
  public void setUp() {
    durationTest = new DurationTimer(0,0, 5);
  }

  @Test
  public void testStartTimer() throws InterruptedException {
    setUp();

    //test that the timer starts
    durationTest.startTimer();
    Thread.sleep(5000);
    Assert.assertEquals(0, durationTest.getTimeRemaining());
  }

  @Test
  public void testPauseTimer() throws InterruptedException {
    setUp();

    //test that the timer is fully paused
    durationTest.startTimer();
    Thread.sleep(2000);
    durationTest.pauseTimer();
    long timeAfterPausing = durationTest.getTimeRemaining();
    Thread.sleep(2000);
    Assert.assertEquals(timeAfterPausing, durationTest.getTimeRemaining());
  }

  @Test
  public void testResumeTimer() throws InterruptedException {
    setUp();

    //test that the timer is fully paused
    durationTest.startTimer();
    Thread.sleep(2000);
    durationTest.pauseTimer();
    Thread.sleep(2000);
    durationTest.startTimer();
    Thread.sleep(3000);
    Assert.assertEquals(0, durationTest.getTimeRemaining());
  }

  @Test
  public void testResetTimer() throws InterruptedException {
    setUp();

    //test that the timer is fully paused
    durationTest.startTimer();
    Thread.sleep(2000);
    durationTest.pauseTimer();
    Thread.sleep(2000);
    durationTest.startTimer();
    Thread.sleep(3000);
    durationTest.resetTimer();
    Assert.assertEquals(5, durationTest.getTimeRemaining());
  }
}
