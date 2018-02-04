import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;


public class OknoPoczatkowe extends JFrame{

    JPanel  centerPanel, northPanel;
    JLabel information, consuments, producents, size, semaphore, waitNotify;
    JButton nextPrzycisk;
    JTextField iloscProducentow, iloscKonsumentow, pojemnoscPolki;
    JRadioButton semaphoreSelected, waitNotifySelected;


    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;

    public OknoPoczatkowe(){

        super("Zadanie nr. 10");
        setSize(width/2, height/2);
        setLocation(width/4, height/4);
        setDefaultCloseOperation(3);        // 3 = WindowConstants.EXIT_ON_CLOSE
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("img" + File.separator +"pwirICO.jpg"));

        //Dodanie paneli do ramki korzystająć z menadżera BorderLayout
        initNorthPanel();
        initCenterPanel();
        initSouthPanel();

        setVisible(true);
        pack();

    }

    private void initSouthPanel() {
    /*
        Dodanie przycisku do dolnej części ramki
     */
        nextPrzycisk = new JButton("Dalej...");

        nextPrzycisk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sprawdzPoprawnoscDanych()){
                    System.out.println("go");
                    new OknoProgramu(Integer.valueOf(pojemnoscPolki.getText()), Integer.valueOf(iloscProducentow.getText()), Integer.valueOf(iloscKonsumentow.getText()));
                }else{
                    System.out.println("Wprowadz poprawne dane lub zastosuj sie do instrukcji");
                    JOptionPane.showMessageDialog(null,"Wprowadz poprawne dane lub zastosuj sie do instrukcji",
                            "ERROR", JOptionPane.INFORMATION_MESSAGE);
                    iloscKonsumentow.setText("");
                    iloscProducentow.setText("");
                    pojemnoscPolki.setText("");
                }
            }
        });

        add(nextPrzycisk, BorderLayout.SOUTH);
    }

    private boolean sprawdzPoprawnoscDanych() {
         if((semaphoreSelected.isSelected() || waitNotifySelected.isSelected()) &&
            (!iloscProducentow.getText().equals("") && Integer.valueOf(iloscProducentow.getText()) >= 1)  &&
            (!iloscKonsumentow.getText().equals("") && Integer.valueOf(iloscKonsumentow.getText()) >= 1 && Integer.valueOf(iloscKonsumentow.getText()) < 71) &&
            (!pojemnoscPolki.getText().equals("") && Integer.valueOf(pojemnoscPolki.getText()) >= 1)){
                return true;
        }
        else return false;
    }

    private void initNorthPanel() {
    /*
       Dodanie etykiety do górnej części ramki
    */
        northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        information = new JLabel("Menu konfiguracji uruchomienia programu");
        northPanel.add(information);
        add(northPanel, BorderLayout.NORTH);
    }

    private void initCenterPanel() {
    /*
        Rozmieszczenie elementów w środkowej części ramki
     */
        consuments = new JLabel("Ilosć konsumentów");
        producents = new JLabel("Ilosć producentów");
        size =  new JLabel("Pojemność półki");
        semaphore = new JLabel("Użycie semafora");
        waitNotify = new JLabel("Użycie wait, notify");

        iloscProducentow = new JTextField("");
        iloscKonsumentow = new JTextField("");
        pojemnoscPolki = new JTextField("");

        semaphoreSelected = new JRadioButton();
        semaphoreSelected.setSelected(false);
        semaphoreSelected.addActionListener(new radioButtonChange('s'));

        waitNotifySelected = new JRadioButton();
        waitNotifySelected.setSelected(true);
        waitNotifySelected.addActionListener(new radioButtonChange('w'));

        centerPanel = new JPanel();
        GroupLayout layout = new GroupLayout(centerPanel);

        centerPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(

                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(consuments)
                                        .addComponent(producents)
                                        .addComponent(size)
                                        .addComponent(semaphore)
                                        .addComponent(waitNotify)
                        )
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(iloscKonsumentow)
                                        .addComponent(iloscProducentow)
                                        .addComponent(pojemnoscPolki)
                                        .addComponent(semaphoreSelected)
                                        .addComponent(waitNotifySelected)
                        )

        );

        layout.setVerticalGroup(

                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(consuments)
                                        .addComponent(iloscKonsumentow)
                        )
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(producents)
                                        .addComponent(iloscProducentow)
                        )
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(size)
                                        .addComponent(pojemnoscPolki)
                        )
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(semaphore)
                                        .addComponent(semaphoreSelected)
                        )
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(waitNotify)
                                        .addComponent(waitNotifySelected)
                        )

        );

        add(centerPanel, BorderLayout.CENTER);
    }

    private class radioButtonChange implements ActionListener{
        char whichOne;

        public radioButtonChange(char whichOne){
            this.whichOne = whichOne;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(whichOne == 's'){
                semaphoreSelected.setSelected(true);
                waitNotifySelected.setSelected(false);
            } else {
                semaphoreSelected.setSelected(false);
                waitNotifySelected.setSelected(true);
            }

        }
    }

}



