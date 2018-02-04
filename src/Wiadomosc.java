import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Bartek on 04.02.2018.
 */
public class Wiadomosc extends JPanel {
    private int numer;
    private int iloscKonsumentow;
    private Image img;
    private JLabel[] konsumenci;

    public Wiadomosc(int numer, int iloscKonsumentow) {
        //zrobione na JPANELU by dodać kropki
        this.numer = numer;
        this.iloscKonsumentow = iloscKonsumentow;
        JLabel nr = new JLabel(String.valueOf(numer));
        nr.setVerticalTextPosition(JLabel.CENTER);
        nr.setHorizontalTextPosition(JLabel.CENTER);
//        add(nr);

        img = new ImageIcon("."+ File.separator+"img"+File.separator+"wiadomosc3.png").getImage();

        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);

        konsumenci = new JLabel[iloscKonsumentow];
        for(int i=0; i<konsumenci.length; i++){
            konsumenci[i] = new JLabel("•");
            konsumenci[i].setForeground(Color.RED);
            add(konsumenci[i]);
        }
    }
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public JLabel[] pobierzKonsumentowKropki(){
        return konsumenci;
    }
    @Override
    public String toString() {
        return String.valueOf(numer);
    }
}
