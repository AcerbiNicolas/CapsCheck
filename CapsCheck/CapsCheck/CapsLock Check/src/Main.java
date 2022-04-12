import java.awt.Dimension;
import java.awt.Taskbar;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Main {
    private static boolean running = true;
    private static boolean isActive = false;
    static JFrame  green;




    public static void main(String[] args) throws NativeHookException {


        Toolkit kit = Toolkit.getDefaultToolkit();

        isActive = kit.getLockingKeyState(KeyEvent.VK_CAPS_LOCK);   //Check caps when application is run

        Dimension screenSize = kit.getScreenSize();
        int width =(int) screenSize.getWidth();
        int heigt =(int) screenSize.getHeight();

        JFrame information = new JFrame();
        information.setBounds(width / 8, heigt / 8, width / 8, heigt / 8);
        information.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        information.setVisible(true);



        Logger l = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        l.setLevel(Level.OFF);


        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(listener);

        while(running){
            try {
                changeWindowOptions(information);
                Thread.sleep(300);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        GlobalScreen.unregisterNativeHook();

    }


    public static void changeWindowOptions(JFrame info){
        Taskbar taskbar = Taskbar.getTaskbar();
        taskbar.setWindowProgressValue(info, 100);

        if(isActive){
            info.setTitle("CapsLock is: ON");
            taskbar.setWindowProgressState(info, Taskbar.State.ERROR);
        }else{
            info.setTitle("CapsLock is: OFF");
            taskbar.setWindowProgressState(info, Taskbar.State.OFF);
        }
    }

    private static NativeKeyListener listener = new NativeKeyListener() {
        @Override
        public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

        }

        @Override
        public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {


        }

        @Override
        public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_CAPS_LOCK){

                if (isActive){
                    isActive = false;
                }else{
                    isActive = true;
                }
            }
        }
    };
}
