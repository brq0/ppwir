import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

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
    private Semaphore miejscaSemaphore, konsumenciPojedynczejWiadomosciSemaphore;

    public PolkaSemaphore(int pojemnosc, JLabel komunikat, int iloscKonsumentow) {
        this.POJEMNOSC_POLKI = pojemnosc;
        this.komunikat = komunikat;
        this.iloscKonsumentow = iloscKonsumentow;
        listaWiadomosci = new LinkedList<>();
        konsumenciKtorzyPrzeczytaliWiadomosc = new LinkedList<>();
        miejscaSemaphore = new Semaphore(0);
        konsumenciPojedynczejWiadomosciSemaphore = new Semaphore(iloscKonsumentow);

        setLayout(new FlowLayout((FlowLayout.RIGHT)));

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
    public synchronized void czytajWiadomosc(Konsument konsument) {
        System.out.println(konsument);
        if(miejscaSemaphore.availablePermits() > 0){

        }else{

        }

        Wiadomosc wiadomosc = listaWiadomosci.getFirst();
        if(!konsumenciKtorzyPrzeczytaliWiadomosc.contains(wiadomosc)) {
            try {
                konsumenciPojedynczejWiadomosciSemaphore.acquire();
                konsumenciKtorzyPrzeczytaliWiadomosc.add(konsument);
                //dodaje go do listy ktorzy przeczytali tak aby ponownie nie mogl jej przeczytac...


                //pobranie kropek oznaczajacych producentow aby zmienic kolor gdy konsument ja przeczytal
                JLabel[] kropkiKonsumentow = wiadomosc.pobierzKonsumentowKropki();
                kropkiKonsumentow[konsument.pobierzNumer()-1].setForeground(Color.GREEN);
                ////////////////////////////////////////////////////////////////////////////////////////

                komunikat.setText(konsument + " czyta wiadomosc: " + wiadomosc);
                System.out.println(konsument + " czyta wiadomosc: " + wiadomosc);


                //jezeli kazdy przeczytal
                if (konsumenciPojedynczejWiadomosciSemaphore.availablePermits() <= 0) {
                    System.out.println("Przeczytana ->" + wiadomosc + "<- ZNIKA.");
                    listaWiadomosci.removeFirst();
                    remove(wiadomosc);

                    komunikat.setText("WSZYSCY KONSUMENCI PRZECZYTALI WIADOMOSC: " + wiadomosc + " ZOSTAJE ONA USUNIETA.");
                    konsumenciKtorzyPrzeczytaliWiadomosc = new LinkedList<>();
                    konsumenciPojedynczejWiadomosciSemaphore = new Semaphore(iloscKonsumentow);

                    try {
                        miejscaSemaphore.acquire();
                        // pobranie miejsca
                    } catch (InterruptedException exc) {
                    }
                }

            } catch (InterruptedException exc) {

            }
        }
    }

    @Override
    public synchronized void dodajWiadomosc(Wiadomosc wiadomosc) {
        System.out.println(wiadomosc);

        if(miejscaSemaphore.availablePermits() < POJEMNOSC_POLKI){
            listaWiadomosci.add(wiadomosc);
            add(wiadomosc);
            miejscaSemaphore.release();
            komunikat.setText("PRODUCENT WYPRODUKOWAL WIADOMOSC: "+wiadomosc);

            validate();
            repaint();

        }else{
            // tutaj musi poczekać
            //bo produkuje wiadomosci ktore przepadaja i dopiero jak przeczytaja konsumenci zeby zniknela
            // pojawia sie nastepna z roznym numerem

        }

    }
}
