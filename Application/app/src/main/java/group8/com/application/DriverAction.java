// author Johannes Flood 2015-03-13 12:40, did not receive any help from Hampus

public interface DriverAction implements Runnable{
	Object controller;
	int score = 50;
	int started; // unix timestamp of when the app started messurement
	Type type;

	public DriverActions(Object controller, Type type);
	private void subtractPoints(int points);
	private void addPoints(int points);
	public int getScore();
	public void reset();

	public enum Type{
		BRAKING, SPEED, DISTRACTION, FEUL;
	}
}