import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class OknoPoczatkowe extends JFrame{

    JPanel  centerPanel, northPanel;
    JLabel information, consuments, producents, size, semaphore, waitNotify;
    JButton next;
    JTextField producentsQuantity, consumentsQuantity, bufforSize;
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
        next = new JButton("Dalej...");

        next.addActionListener(new buttonPressed(this));

        add(next, BorderLayout.SOUTH);
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

        producentsQuantity = new JTextField();
        consumentsQuantity = new JTextField();
        bufforSize = new JTextField();

        semaphoreSelected = new JRadioButton();
        semaphoreSelected.setSelected(true);
        semaphoreSelected.addActionListener(new radioButtonChange('s'));

        waitNotifySelected = new JRadioButton();
        waitNotifySelected.setSelected(false);
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
                                        .addComponent(consumentsQuantity)
                                        .addComponent(producentsQuantity)
                                        .addComponent(bufforSize)
                                        .addComponent(semaphoreSelected)
                                        .addComponent(waitNotifySelected)
                        )

        );

        layout.setVerticalGroup(

                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(consuments)
                                        .addComponent(consumentsQuantity)
                        )
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(producents)
                                        .addComponent(producentsQuantity)
                        )
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(size)
                                        .addComponent(bufforSize)
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

    private void initNorthPanel() {
    /*
       Dodanie etykiety do górnej części ramki
    */
        northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        information = new JLabel("Menu konfiguracji uruchomienia programu");
        northPanel.add(information);
        add(northPanel, BorderLayout.NORTH);
    }

    private class buttonPressed implements ActionListener {

        JFrame frame;

        public buttonPressed(JFrame frame){
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Click");
            Integer howManyConsuments, howManyProducents, howBigBuffor;

            howManyConsuments = Integer.valueOf(consumentsQuantity.getText());
            howManyProducents = Integer.valueOf(producentsQuantity.getText());
            howBigBuffor = Integer.valueOf(bufforSize.getText());

            frame.dispose();

            new OknoProgramu(howBigBuffor, howManyProducents, howManyConsuments);

        }
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



