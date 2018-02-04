import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Bartek on 03.01.2018.
 */
public class OknoProgramu extends JFrame {
    private JPanel srodekEkranu, dolEkranuKomunikaty;
    private Polka polkaNaWiadomosci;
    private JLabel komunikatDol;


    public OknoProgramu(int pojemnoscBufora, int liczbaProducentow, int liczbaKonsumentow){
        setTitle("PPWIR");
        setVisible(true);
        setSize(830,500);
        setLocation(200,100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("img" + File.separator +"pwirICO.jpg"));



        /** SRODEK EKRANU  -  TAM BEDA KONSUMENCI*/
        srodekEkranu = new JPanel();
        srodekEkranu.setPreferredSize(new Dimension(800,380));


        /** KOMUNIKATY ODNOSNIE TEGO CO SIE DZIEJE AKTUALNIE*/
        dolEkranuKomunikaty = new JPanel();
        dolEkranuKomunikaty.setPreferredSize(new Dimension(800,20));
        dolEkranuKomunikaty.setBackground(Color.GREEN);
        komunikatDol = new JLabel("GIT DZIALA");
        dolEkranuKomunikaty.add(komunikatDol);


        /** MIEJSCE NA POLKE TAM GDZIE BEDZIE SIE ZNAJODWAC POLKA Z WIADOMOSCIAMI
         * MAKSYMALNIE 20 MIEJSC ZEBY WYGLADALO JAKOS...
         * */
        polkaNaWiadomosci = new Polka(pojemnoscBufora, komunikatDol, liczbaKonsumentow);

        /** DODANIE / PODZIELENIE EKRANU NA 3 CZESCI*/
        add(polkaNaWiadomosci, BorderLayout.CENTER);
//        add(srodekEkranu, BorderLayout.CENTER);
        add(dolEkranuKomunikaty, BorderLayout.SOUTH);


        new Producent(polkaNaWiadomosci, 1, liczbaKonsumentow);
        for(int i=1; i<=liczbaKonsumentow; i++){
            new Konsument(polkaNaWiadomosci, i);
        }

    }
}
