/**
 * Created by Bartek on 05.02.2018.
 */
public class NumerWiadomosci {
    private static int nr = 0;
    public synchronized static int pobierzNumer(){
        nr ++;
        return nr;
    }
}
