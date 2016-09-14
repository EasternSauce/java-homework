package listener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class Main{
	public static void main(String[] args){
		try{
			GlobalScreen.registerNativeHook();
		}catch(NativeHookException e){
			System.out.println("Native hook exception!");
		}
		
		Listener example = new Listener();
		GlobalScreen.addNativeKeyListener(example);
		GlobalScreen.addNativeMouseListener(example);
		GlobalScreen.addNativeMouseMotionListener(example);
	}
}
