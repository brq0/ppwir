import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Bartek on 03.01.2018.
 */
public class Producent implements Runnable {
    private Polka polka;
    private int nr;
    private JLabel komunikat;
    private int iloscKonsumentow;


    public Producent(Polka polka, int nr) {
        this.polka = polka;
        this.nr = nr;
        iloscKonsumentow = 0;

        new Thread(this).start();
    }
    public Producent(Polka polka, int nr, int iloscKonsumentow) {
        this.polka = polka;
        this.nr = nr;
        this.iloscKonsumentow = iloscKonsumentow;

        new Thread(this).start();
    }

    @Override
    public void run() {
        for(int i=1; ; i++){
            try{
                Thread.sleep(ThreadLocalRandom.current().nextInt(200,500));
                polka.dodajWiadomosc(new Wiadomosc(i,iloscKonsumentow, polka.pobierzPojemnoscPolki()));
            }catch (InterruptedException exc){}

        }
    }
}
