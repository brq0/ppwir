
public class NumerWiadomosci {
    private static int nr = 0;
    public synchronized static int pobierzNumer(){
        nr ++;
        return nr;
    }
}
