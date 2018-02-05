import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class PolkaSemaphore extends Polka {
    private LinkedList<Wiadomosc> listaWiadomosci;
    //lista wiadomosci ZNAJDUJACYCH sie na polce

    private LinkedList<Konsument> konsumenciKtorzyPrzeczytaliWiadomosc;
    //dla kazdej wiadomosci bedzie pozniej tworzona
    //nowa lista konsumentow KTORZY JA PRZECZYTALI

    private JLabel komunikat;
    final int POJEMNOSC_POLKI;
    private int iloscKonsumentow;
    private Semaphore miejscaSemaphore,                     //semafor miejsc na polce
            przeczytanaWiadomoscSemaphore,                  //semafor zwalniajacy producenta gdy wiadomosc zniknie
            konsumenciPojedynczejWiadomosciSemaphore;       //semafor pojedynczej wiadomosci rozny dla kazdej
                                                            //miejsc jst tyle ile konsumentow
                                                            //kazdy konsument pobiera jedno

    public PolkaSemaphore(int pojemnosc, JLabel komunikat, int iloscKonsumentow) {
        this.POJEMNOSC_POLKI = pojemnosc;
        this.komunikat = komunikat;
        this.iloscKonsumentow = iloscKonsumentow;
        listaWiadomosci = new LinkedList<>();
        konsumenciKtorzyPrzeczytaliWiadomosc = new LinkedList<>();

        miejscaSemaphore = new Semaphore(0);
        przeczytanaWiadomoscSemaphore = new Semaphore(0);
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
    public void czytajWiadomosc(Konsument konsument) {
        if(iloscWiadomosci() > 0) {
            Wiadomosc wiadomosc = listaWiadomosci.getFirst();
            if (!konsumenciKtorzyPrzeczytaliWiadomosc.contains(konsument)) {
                try {
                    konsumenciPojedynczejWiadomosciSemaphore.acquire();
                    konsumenciKtorzyPrzeczytaliWiadomosc.add(konsument);
                    //dodaje go do listy ktorzy przeczytali tak aby ponownie nie mogl jej przeczytac...


                    //pobranie kropek oznaczajacych producentow aby zmienic kolor gdy konsument ja przeczytal
                    JLabel[] kropkiKonsumentow = wiadomosc.pobierzKonsumentowKropki();
                    kropkiKonsumentow[konsument.pobierzNumer() - 1].setForeground(Color.GREEN);
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
                        //ponowna inicjalizacja semafora nowej wiadomosci oraz listy konsumentow ktorzy ja przeczytali

                        try {
                            miejscaSemaphore.acquire();
                            // pobranie miejsca azeby zwolnić polke (mozna tez po size listy...)
                            przeczytanaWiadomoscSemaphore.release();
                            // zwalnia miejsce, tak aby producent mogl wyprodukowac kolejna (bo czeka, bez tego by przepadly)


                        } catch (InterruptedException exc) {
                        }
                    }

                } catch (InterruptedException exc) {
                }
            }
            validate();
            repaint();
        }
    }

    @Override
    public void dodajWiadomosc(Producent producent, Wiadomosc wiadomosc) {
        if(miejscaSemaphore.availablePermits() < POJEMNOSC_POLKI){
            listaWiadomosci.add(wiadomosc);
            add(wiadomosc);
            miejscaSemaphore.release();
            komunikat.setText(producent+" WYPRODUKOWAL WIADOMOSC: "+wiadomosc);
            System.out.println(producent+" WYPRODUKOWAL WIADOMOSC: "+wiadomosc);

            validate();
            repaint();

        }else{
            //jezeli polka jest pelna
            komunikat.setText(producent +" CZEKA. POLKA PELNA.");
            System.out.println(producent +" CZEKA. POLKA PELNA.");

            try{
//              zatrzymanie producentow wiadomosci zeby byly tworzone pokolei numerami
//              nie zatrzymujac go po osiagnieciu max limit wiadomosci (pojemnosc polki)
//              bylyby inne (wieksze) numery, gdyz producent przyszedlby z kolejna i odszedl, po czym wiadomosc by przepadla

                if(przeczytanaWiadomoscSemaphore.availablePermits() > 0){
                    przeczytanaWiadomoscSemaphore.acquire(przeczytanaWiadomoscSemaphore.availablePermits());
                    //pobiera wszystko, bo konsumenci zwalniaja ten semafor za kazdym razem kiedy przeczytali wiadomosc
                }
                else przeczytanaWiadomoscSemaphore.acquire();


                //czeka az konsument przeczyta, jezeli to zrobi to ponownie proboje dodac
                dodajWiadomosc(producent, wiadomosc);
            }catch (InterruptedException exc){

            }
        }

    }
}
