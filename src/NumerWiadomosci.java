
public class NumerWiadomosci {
    private static int nr = 100;
    public synchronized static int pobierzNumer(){
        nr ++;
        return nr;
    }
}
