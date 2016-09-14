package votegui.countapp;

public abstract class MyButtonListener{
	public static boolean canPressSecure = true;
	public static boolean canPressCount = true;
	public abstract void workerJob();
	public abstract void done();
}
