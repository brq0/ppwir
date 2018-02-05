import javax.swing.*;

public abstract class Polka extends JPanel{
    public abstract int iloscWiadomosci();
    public abstract int pobierzPojemnoscPolki();
    public abstract void czytajWiadomosc(Konsument konsument);
    public abstract void dodajWiadomosc(Producent producent, Wiadomosc wiadomosc);
}
