import javax.swing.*;

/**
 * Created by Bartek on 05.02.2018.
 */
public abstract class Polka extends JPanel{
    public abstract int iloscWiadomosci();
    public abstract int pobierzPojemnoscPolki();
    public abstract void czytajWiadomosc(Konsument konsument);
    public abstract void dodajWiadomosc(Producent producent, Wiadomosc wiadomosc);
}
