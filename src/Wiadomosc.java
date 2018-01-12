import javax.swing.*;
import java.io.File;

/**
 * Created by Bartek on 03.01.2018.
 */
public class Wiadomosc extends JLabel {
    private int numer;

    public Wiadomosc(int numer) {
        this.numer = numer;
        setText(String.valueOf(numer));
        setVerticalTextPosition(JLabel.CENTER);
        setHorizontalTextPosition(JLabel.CENTER);
        setIcon(new ImageIcon("."+ File.separator+"img"+File.separator+"wiadomosc3.png"));


    }

    @Override
    public String toString() {
        return String.valueOf(numer);
    }
}
