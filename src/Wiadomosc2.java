import javax.swing.*;
import java.io.File;

/**
 * Created by Bartek on 03.01.2018.
 */
public class Wiadomosc2 extends JLabel {
    private int numer;
    private int iloscKonsumentow;

    public Wiadomosc2(int numer) {
        this.numer = numer;
        setText(String.valueOf(numer));
        setVerticalTextPosition(JLabel.CENTER);
        setHorizontalTextPosition(JLabel.CENTER);
        setIcon(new ImageIcon("."+ File.separator+"img"+File.separator+"wiadomosc3.png"));


    }
    public Wiadomosc2(int numer, int iloscKonsumentow) {
        this.numer = numer;
        this.iloscKonsumentow = iloscKonsumentow;
        setText(String.valueOf(numer));
        setVerticalTextPosition(JLabel.CENTER);
        setHorizontalTextPosition(JLabel.CENTER);
        setIcon(new ImageIcon("."+ File.separator+"img"+File.separator+"wiadomosc3.png"));
        add(new JLabel("co tam"));


    }

    @Override
    public String toString() {
        return String.valueOf(numer);
    }
}
