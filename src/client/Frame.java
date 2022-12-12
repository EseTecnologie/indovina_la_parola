package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Frame extends JFrame {
    JPanel panel ;
    JPanel gioco ;
    JPanel classifica;
    client c;
    Frame(){
        super("INDOVINA LA PAROLA");
        client c= new client();
      panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                Image img= null;
                try {
                    img = ImageIO.read(new File("resources/sfondoLettere.jpg"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                g.drawImage(img,0,0,null);
            }
        };

         JLabel label = new JLabel("INDOVINA LA PAROLA");
         label.setForeground(Color.red);
         label.setFont(new Font("Comic sans", Font.BOLD, 32));
         Dimension size = label.getPreferredSize();
         label.setBounds(400-size.width/2,150-size.height/2, size.width, size.height);
         panel.add(label);

         JButton button = new JButton("Gioca");
         button.setSize(100,50);
         button.setFont(new Font("Comic sans", Font.BOLD, 20));
         button.setForeground(Color.white);
         button.setBackground(Color.red);
         button.setLocation(650/2-100/2,400);
         panel.add(button);

         JButton b =new JButton("Classifica");
         b.setSize(150,50);
         b.setFont(new Font("Comic sans", Font.BOLD, 20));
         b.setForeground(Color.white);
         b.setBackground(Color.red);
         b.setLocation(950/2-100/2,400);
         panel.add(b);

         panel.setLayout(null);

         panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        gioco = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                Image img= null;
                try {
                    img = ImageIO.read(new File("resources/palco.jpg"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                g.drawImage(img,0,0,null);
            }
        };
        JLabel lableGioco = new JLabel("M***");
        lableGioco.setForeground(Color.red);
        lableGioco.setFont(new Font("Comic sans", Font.BOLD, 32));
        lableGioco.setHorizontalAlignment(JLabel.CENTER);
        lableGioco.setBounds(300,200, 200, 30);

        JButton bGioco = new JButton("invia");
        bGioco.setSize(100,50);
        bGioco.setFont(new Font("Comic sans", Font.BOLD, 20));
        bGioco.setForeground(Color.white);
        bGioco.setBackground(Color.red);
        bGioco.setLocation(500,400);
        gioco.add(bGioco);

        JButton bstop = new JButton("stop");
        bstop.setSize(100,50);
        bstop.setFont(new Font("Comic sans", Font.BOLD, 20));
        bstop.setForeground(Color.white);
        bstop.setBackground(Color.red);
        bstop.setLocation(680,10);
        gioco.add(bstop);
        gioco.setLayout(null);
        gioco.add(lableGioco);
        gioco.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JTextField f = new JTextField();
        f.setSize(300,50);
        f.setLocation(200,400);
        gioco.add(f);


        classifica = new JPanel();
        JButton bhome= new JButton("home");
        bhome.setSize(100,50);
        bhome.setFont(new Font("Comic sans", Font.BOLD, 20));
        bhome.setForeground(Color.white);
        bhome.setBackground(Color.red);
        bhome.setLocation(350,400);
        classifica.add(bhome);

        JLabel lPodio=new JLabel();
        lPodio.setIcon(new ImageIcon("resources/podio1.jpg"));
        lPodio.setSize(100,100);
        lPodio.setLocation(350,10);
        classifica.add(lPodio);

        JLabel lPodio2=new JLabel();
        lPodio2.setIcon(new ImageIcon("resources/podio2.jpg"));
        lPodio2.setSize(100,100);
        lPodio2.setLocation(200,35);
        classifica.add(lPodio2);

        JLabel lPodio3=new JLabel();
        lPodio3.setIcon(new ImageIcon("resources/podio3.jpg"));
        lPodio3.setSize(100,100);
        lPodio3.setLocation(500,60);
        classifica.add(lPodio3);

        JLabel lPrimoposto = new JLabel("Marco");
        lPrimoposto.setForeground(Color.red);
        lPrimoposto.setFont(new Font("Comic sans", Font.BOLD, 32));
        lPrimoposto.setBounds(325,120, 150, 50);
        lPrimoposto.setHorizontalAlignment(JLabel.CENTER);
        classifica.add(lPrimoposto);

        JLabel lSecondoPosto = new JLabel("Luca");
        lSecondoPosto.setForeground(Color.red);
        lSecondoPosto.setFont(new Font("Comic sans", Font.BOLD, 32));
        lSecondoPosto.setBounds(175,145, 150, 50);
        lSecondoPosto.setHorizontalAlignment(JLabel.CENTER);

        classifica.add(lSecondoPosto);

        JLabel lTerzosecondo = new JLabel("Andrea");
        lTerzosecondo.setForeground(Color.red);
        lTerzosecondo.setFont(new Font("Comic sans", Font.BOLD, 32));
        lTerzosecondo.setBounds(475,170, 150, 50);
        lTerzosecondo.setHorizontalAlignment(JLabel.CENTER);


        classifica.add(lTerzosecondo);

        JLabel lElenco = new JLabel("elenco");
        lElenco.setForeground(Color.red);
        lElenco.setFont(new Font("Comic sans", Font.PLAIN, 16));
        Dimension size2 = lElenco.getPreferredSize();
        lElenco.setVerticalAlignment(JLabel.TOP);
        lElenco.setBounds(40,250,800, 300);
        classifica.add(lElenco);

        classifica.setLayout(null);
        classifica.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));



         getContentPane().add(panel);
         setSize(800,500);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setResizable(false);
         setVisible(true);

         button.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e){
                 getContentPane().removeAll();
                 getContentPane().add(gioco);
                 revalidate();
                 getContentPane().repaint();
            }

         });

         b.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e){
                 getContentPane().removeAll();
                 getContentPane().add(classifica);
                 revalidate();
                 getContentPane().repaint();
             }

         });

        bstop.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                getContentPane().add(panel);
                revalidate();
                getContentPane().repaint();
            }

        });

        bhome.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                getContentPane().add(panel);
                revalidate();
                getContentPane().repaint();
            }

        });

        bGioco.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(f.getText().trim()!=""){
                    String msg=f.getText().trim();
                    c.writeMessageThread(msg);

                    String result=c.readMessageThread();
                    
                }
            }

        });
    }


}
