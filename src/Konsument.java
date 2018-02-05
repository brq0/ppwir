import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

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
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
                polka.czytajWiadomosc(this);
            } catch (InterruptedException exc) {
            }

        }
    }

    public int pobierzNumer(){
        return nr;
    }

    @Override
    public String toString() {
        return "Konsument: "+nr;
    }
}