import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Bartek on 05.02.2018.
 */
public class PolkaSemaphore extends Polka {
    private LinkedList<Wiadomosc> listaWiadomosci;
    //lista wiadomosci ZNAJDUJACYCH sie na polce

    private LinkedList<Konsument> konsumenciKtorzyPrzeczytaliWiadomosc;
    //dla kazdej wiadomosci bedzie pozniej tworzona
    //nowa lista konsumentow KTORZY JA PRZECZYTALI

    private JLabel komunikat;
    final int POJEMNOSC_POLKI;
    private int iloscKonsumentow;

    public PolkaSemaphore(int pojemnosc, JLabel komunikat, int iloscKonsumentow) {
        this.POJEMNOSC_POLKI = pojemnosc;
        this.komunikat = komunikat;
        this.iloscKonsumentow = iloscKonsumentow;
        listaWiadomosci = new LinkedList<>();
        konsumenciKtorzyPrzeczytaliWiadomosc = new LinkedList<>();

        setLayout(new FlowLayout((FlowLayout.RIGHT)));
        if(pojemnosc<11) setPreferredSize(new Dimension(800,60));
        else setPreferredSize(new Dimension(800,120));
//        setBackground(Color.BLUE);

    }

    @Override
    public int iloscWiadomosci() {
        return listaWiadomosci.size();
    }

    @Override
    public int pobierzPojemnoscPolki() {
        return POJEMNOSC_POLKI;
    }

    @Override
    public void czytajWiadomosc(Konsument konsument) {
        System.out.println(konsument);
    }

    @Override
    public void dodajWiadomosc(Wiadomosc wiadomosc) {
        System.out.println(wiadomosc);
    }
}
