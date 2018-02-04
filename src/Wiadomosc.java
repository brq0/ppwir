import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Bartek on 04.02.2018.
 */
public class Wiadomosc extends JPanel {
    private int numer;
    private int iloscKonsumentow;
    private int pojemnoscPolki;
    private Image img;
    private JLabel[] konsumenci;

    public Wiadomosc(int numer, int iloscKonsumentow, int pojemnoscPolki) {
        //zrobione na JPANELU by dodać kropki
        this.numer = numer;
        this.iloscKonsumentow = iloscKonsumentow;
        this.pojemnoscPolki = pojemnoscPolki;

        JLabel nr = new JLabel(String.valueOf(numer));
        nr.setVerticalTextPosition(JLabel.CENTER);
        nr.setHorizontalTextPosition(JLabel.CENTER);
//        add(nr);

        //rozne wielkosci wiadomosci dla danej polki
        if(pojemnoscPolki>=41){
            //max 70 - max 10 konsumentow na tej wielkosci wiadomosci
            img = fitimage(new ImageIcon("."+ File.separator+"img"+File.separator+"wiadomosc3.png").getImage()
                    , 73, 54);

        }
        else if(pojemnoscPolki >= 21){
            //MAX 40 - max konsumentow 24
            img = fitimage(new ImageIcon("."+ File.separator+"img"+File.separator+"wiadomosc3.png").getImage()
                    , 110, 81);
        }
        else if(pojemnoscPolki >= 13){
            //max 20 bedzie widoczne, max 50 konsumentow na tej wielkosci wiadomosci
            img = fitimage(new ImageIcon("."+ File.separator+"img"+File.separator+"wiadomosc3.png").getImage()
                    , 146, 108);
        }else if(pojemnoscPolki <= 12){
            //max 12, max konsumentow 80
            img = fitimage(new ImageIcon("."+ File.separator+"img"+File.separator+"wiadomosc3.png").getImage()
                    , 182, 135);
        }


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

    private Image fitimage(Image img , int w , int h)
    {
        //powieksza obrazek oraz zaokragla rogi
        int widthTemp = w;
        if(w >= 182) widthTemp = widthTemp;
        if(w >= 146) widthTemp = widthTemp - 5;

        BufferedImage bim=new BufferedImage(widthTemp,h,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2=bim.createGraphics();

        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(qualityHints);

        g2.setClip(new RoundRectangle2D.Double(0,0,w,h,w/4,h/4));

        g2.drawImage(img,0,0,w,h,null);


        g2.dispose();
        return bim;

    }

    public JLabel[] pobierzKonsumentowKropki(){
        return konsumenci;
    }
    @Override
    public String toString() {
        return String.valueOf(numer);
    }
}
