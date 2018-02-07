import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class PolkaWaitNotify extends Polka {
    private LinkedList<Wiadomosc> listaWiadomosci;
    //lista wiadomosci ZNAJDUJACYCH sie na polce

    private LinkedList<Konsument> konsumenciKtorzyPrzeczytaliWiadomosc;
    //dla kazdej wiadomosci bedzie pozniej tworzona
    //nowa lista konsumentow KTORZY JA PRZECZYTALI

    private JLabel komunikat;
    final int POJEMNOSC_POLKI;
    private boolean producentCzeka;
    private int iloscKonsumentow;


    public PolkaWaitNotify(int pojemnosc, JLabel komunikat, int iloscKonsumentow) {
        this.POJEMNOSC_POLKI = pojemnosc;
        this.komunikat = komunikat;
        this.iloscKonsumentow = iloscKonsumentow;
        listaWiadomosci = new LinkedList<>();
        konsumenciKtorzyPrzeczytaliWiadomosc = new LinkedList<>();
        producentCzeka = false;

        setLayout(new FlowLayout((FlowLayout.RIGHT)));

    }

    @Override
    public int iloscWiadomosci(){
        return listaWiadomosci.size();
    }

    @Override
    public int pobierzPojemnoscPolki(){
        return POJEMNOSC_POLKI;
    }

    @Override
    public synchronized void dodajWiadomosc(Producent producent, Wiadomosc wiadomosc){
        synchronized (this) {
            if (iloscWiadomosci() >= POJEMNOSC_POLKI) {
                try {
                    producentCzeka = true;
                    komunikat.setText(producent + " CZEKA. POLKA PELNA.");
                    System.out.println(producent + " CZEKA. POLKA PELNA.");
                    wait();
                } catch (InterruptedException exc) {
                }
            }

            listaWiadomosci.add(wiadomosc);
            add(wiadomosc);
            komunikat.setText(producent + " WYPRODUKOWAL WIADOMOSC: " + wiadomosc);
            System.out.println(producent + " WYPRODUKOWAL WIADOMOSC: " + wiadomosc);


            validate();
            repaint();
        }
    }

    @Override
    public synchronized void czytajWiadomosc(Konsument konsument){
        if(iloscWiadomosci() > 0) {
            //sprawdza czy juz konsument przeczytal ta wiadomosc (znajduje sie w liscie osob ktorz przeczytali)
            if (!konsumenciKtorzyPrzeczytaliWiadomosc.contains(konsument)) {
                //kiedy przychodzi konsument ktory nie przeczytal wczesniej tej wiadomosci

                konsumenciKtorzyPrzeczytaliWiadomosc.add(konsument);
                //dodaje go do listy ktorzy przeczytali tak aby ponownie nie mogl jej przeczytac...
                Wiadomosc wiadomosc = listaWiadomosci.getFirst();

                //pobranie kropek oznaczajacych producentow aby zmienic kolor gdy konsument ja przeczytal
                JLabel[] kropkiKonsumentow = wiadomosc.pobierzKonsumentowKropki();
                kropkiKonsumentow[konsument.pobierzNumer() - 1].setForeground(Color.GREEN);
                ////////////////////////////////////////////////////////////////////////////////////////

                komunikat.setText(konsument + " czyta wiadomosc: " + wiadomosc);
                System.out.println(konsument + " czyta wiadomosc: " + wiadomosc);


                if (konsumenciKtorzyPrzeczytaliWiadomosc.size() >= iloscKonsumentow) {
                    System.out.println("Przeczytana ->" + wiadomosc + "<- ZNIKA.");
                    listaWiadomosci.removeFirst();
                    remove(wiadomosc);

                    komunikat.setText("WSZYSCY KONSUMENCI PRZECZYTALI WIADOMOSC: " + wiadomosc + " ZOSTAJE ONA USUNIETA.");
                    konsumenciKtorzyPrzeczytaliWiadomosc = new LinkedList<>();

                    if (producentCzeka) {
                        producentCzeka = false;
                        notify();
                    }
                }
            }


            validate();
            repaint();
        }
    }
}
