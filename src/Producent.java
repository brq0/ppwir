import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class Producent implements Runnable {
    private Polka polka;
    private int nr;
    private JLabel komunikat;
    private int iloscKonsumentow;

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
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000,3000));
                synchronized (polka) {
                    polka.dodajWiadomosc(this, new Wiadomosc(NumerWiadomosci.pobierzNumer(), iloscKonsumentow, polka.pobierzPojemnoscPolki()));
                }
                }catch (InterruptedException exc){}

        }
    }

    @Override
    public String toString() {
        return "Producent: "+nr;
    }
}
