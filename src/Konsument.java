import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Bartek on 04.01.2018.
 */
public class Konsument  implements Runnable {
    private Polka polka;
    private int nr;


    public Konsument(Polka polka, int nr) {
        this.polka = polka;
        this.nr = nr;

        new Thread(this).start();
    }

    @Override
    public void run() {
        for (int i = 100; ; i++) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500, 5000));
                polka.czytajWiadomosc(this);
            } catch (InterruptedException exc) {
            }

        }
    }

    @Override
    public String toString() {
        return "Konsument: "+nr;
    }
}