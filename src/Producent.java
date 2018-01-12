import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Bartek on 03.01.2018.
 */
public class Producent implements Runnable {
    private Polka polka;
    private int nr;
    private JLabel komunikat;


    public Producent(Polka polka, int nr) {
        this.polka = polka;
        this.nr = nr;

        new Thread(this).start();
    }

    @Override
    public void run() {
        for(int i=100; ; i++){
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(200,3000));
                polka.dodajWiadomosc(new Wiadomosc(i));
            }catch (InterruptedException exc){}

        }
    }
}
