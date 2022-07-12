package cz.cvut.fel.pjv;


/**
 * Class represents timer
 */
public class MyTimer extends Thread {
    private int seconds = 0;
    private boolean pause = false;

    public void run(){
        while (!pause){
            seconds++;
            try {
                sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void setPause(){
        pause = true;
    }

    public int getTime(){
        setPause();
        return seconds;
    }
}
