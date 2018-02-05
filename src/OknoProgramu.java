import javafx.scene.layout.BorderPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class OknoProgramu extends JFrame {
    private JPanel srodekEkranu, dolEkranuKomunikaty;
    private Polka polkaNaWiadomosci;
    private JLabel komunikatDol;


    public OknoProgramu(int pojemnoscBufora, int liczbaProducentow, int liczbaKonsumentow, boolean semaphore){
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
        komunikatDol = new JLabel("");
        dolEkranuKomunikaty.add(komunikatDol);


        if(semaphore){
            polkaNaWiadomosci = new PolkaSemaphore(pojemnoscBufora, komunikatDol, liczbaKonsumentow);
        }else {
            polkaNaWiadomosci = new PolkaWaitNotify(pojemnoscBufora, komunikatDol, liczbaKonsumentow);
        }

        /** DODANIE / PODZIELENIE EKRANU NA 2 CZESCI - SRODEK I KOMUNIKATY*/
        add(polkaNaWiadomosci, BorderLayout.CENTER);
        add(dolEkranuKomunikaty, BorderLayout.SOUTH);



        for(int i=1; i<=liczbaProducentow; i++){
            new Producent(polkaNaWiadomosci, i, liczbaKonsumentow);
        }
        for(int i=1; i<=liczbaKonsumentow; i++){
            new Konsument(polkaNaWiadomosci, i);
        }

    }
}
