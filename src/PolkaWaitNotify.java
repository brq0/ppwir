import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Bartek on 04.01.2018.
 */
public class PolkaWaitNotify extends Polka {
    private LinkedList<Wiadomosc> listaWiadomosci;
    //lista wiadomosci ZNAJDUJACYCH sie na polce

    private LinkedList<Konsument> konsumenciKtorzyPrzeczytaliWiadomosc;
    //dla kazdej wiadomosci bedzie pozniej tworzona
    //nowa lista konsumentow KTORZY JA PRZECZYTALI

    private JLabel komunikat;
    final int POJEMNOSC_POLKI;
    private boolean konsumentCzeka, producentCzeka;
    private int iloscKonsumentow;


    public PolkaWaitNotify(int pojemnosc, JLabel komunikat, int iloscKonsumentow) {
        this.POJEMNOSC_POLKI = pojemnosc;
        this.komunikat = komunikat;
        this.iloscKonsumentow = iloscKonsumentow;
        listaWiadomosci = new LinkedList<>();
        konsumenciKtorzyPrzeczytaliWiadomosc = new LinkedList<>();
        konsumentCzeka = false;
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
    public synchronized void dodajWiadomosc(Wiadomosc wiadomosc){
        if(iloscWiadomosci() >= POJEMNOSC_POLKI){
            try {
                producentCzeka = true;
                komunikat.setText("PRODUCENT CZEKA. POLKA PELNA.");
                System.out.println("PRODUCENT CZEKA. POLKA PELNA.");
                wait();
            }catch (InterruptedException exc){}
        }
        listaWiadomosci.add(wiadomosc);
        add(wiadomosc);
        komunikat.setText("PRODUCENT WYPRODUKOWAL WIADOMOSC: "+wiadomosc);

        if(konsumentCzeka){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException exc){}
            konsumentCzeka = false;
            notifyAll();
        }

        validate();
        repaint();
    }

    @Override
    public synchronized void czytajWiadomosc(Konsument konsument){
        if(iloscWiadomosci() <= 0){
            try {
                //TUTAJ KAZDY KONSUMENT POWINIEN CZEKAC.
                //
                //
                ///////////////////////////////////////

                konsumentCzeka = true;
                komunikat.setText(konsument+ " KONSUMENCI CZEKAJA. BRAK WIADOMOSCI W POLCE.");
                wait();
            }catch (InterruptedException exc){}
        }




        //jezeli juz konsument przeczytal ta wiadomosc (znajduje sie w liscie osob ktorz przeczytali)
        // to czeka

        if(konsumenciKtorzyPrzeczytaliWiadomosc.contains(konsument)) {
            try {
                Wiadomosc wiadomosc = listaWiadomosci.getFirst();
                konsumentCzeka = true;
                komunikat.setText(konsument+ " CZEKA. PRZECZYTAL JUZ WIADOMOSC");
                System.out.println(konsument + " przeczytaj juz wiadomosc: ->"+wiadomosc +"<- CZEKA");

                //tutaj (MA) a przynajmniej teraz nie wybudza go producent ...
                //producent wybudzi jak wiadomosc zostanie przeczytana przez konsumentow wszystkich i zniknie.
                wait();
            }catch (InterruptedException exc){}
        }else {
            //kiedy przychodzi konsument ktory nie przeczytal wczesniej tej wiadomosci

            konsumenciKtorzyPrzeczytaliWiadomosc.add(konsument);
            //dodaje go do listy ktorzy przeczytali tak aby ponownie nie mogl jej przeczytac...
            Wiadomosc wiadomosc = listaWiadomosci.getFirst();

            //pobranie kropek oznaczajacych producentow aby zmienic kolor gdy konsument ja przeczytal
            JLabel[] kropkiKonsumentow = wiadomosc.pobierzKonsumentowKropki();
            kropkiKonsumentow[konsument.pobierzNumer()-1].setForeground(Color.GREEN);
            ////////////////////////////////////////////////////////////////////////////////////////

            komunikat.setText(konsument + " czyta wiadomosc: " + wiadomosc);
            System.out.println(konsument + " czyta wiadomosc: " + wiadomosc);


            if (konsumenciKtorzyPrzeczytaliWiadomosc.size() >= iloscKonsumentow) {
                System.out.println("Przeczytana ->"+wiadomosc+"<- ZNIKA.");
                listaWiadomosci.removeFirst();
                remove(wiadomosc);

                komunikat.setText("WSZYSCY KONSUMENCI PRZECZYTALI WIADOMOSC: " + wiadomosc + " ZOSTAJE ONA USUNIETA.");
                konsumenciKtorzyPrzeczytaliWiadomosc = new LinkedList<>();

                if(producentCzeka){
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException exc){}
                    producentCzeka = false;
                    notify();
                }
                if(konsumentCzeka){
                    konsumentCzeka = false;
                    notifyAll();
                }
            }
        }



        validate();
        repaint();

    }
}
