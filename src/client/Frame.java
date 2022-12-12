package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class Frame extends JFrame {
    JPanel panel ;
    JPanel gioco ;
    JPanel classifica;
    client c;
    Frame(){
        super("INDOVINA LA PAROLA");

      panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                Image img= null;
                try {
                    img = ImageIO.read(new File("resources/LOGO.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                g.drawImage(img,400-250,225-151,null);
            }
        };
      writeHome();
      panel.setBackground(Color.white);


        gioco = new JPanel(){
            @Override
            public void paintComponent(Graphics g) {
                Image img= null;
                try {
                    img = ImageIO.read(new File("resources/palco.jpg"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Image img2= null;
                try {
                    img2 = ImageIO.read(new File("resources/LOGO.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                g.drawImage(img,0,0,null);
                g.drawImage(img2,0,0,130,100,null);
            }
        };


        classifica = new JPanel();

         getContentPane().add(panel);
         setSize(800,500);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setResizable(false);
         setVisible(true);
        c= new client();

        String result=c.readMessageThread();

        String parola=c.readMessageThread();

    }
    public void writeGioco(String parola){
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

        JButton bstop = new JButton("Logout");
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

        JLabel win = new JLabel("");
        win.setForeground(Color.orange);
        win.setFont(new Font("Comic sans", Font.BOLD, 48));
        win.setHorizontalAlignment(JLabel.CENTER);
        win.setBounds(300,280, 200, 30);
        gioco.add(win);

        String[] s=parola.split(";");
        lableGioco.setText(s[1]);
        bGioco.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(f.getText().trim()!="") {
                    String msg = f.getText().trim();
                    c.writeMessageThread(msg);
                    String result = c.readMessageThread();
                    String[] s = result.split(";");
                    lableGioco.setText(s[1]);
                    if(s[0].trim().equals("win")){
                        win.setText("WIN");
                        f.setEnabled(false);
                        gioco.remove(bGioco);
                        revalidate();
                        repaint();
                    }
                }

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
    }

    public void writeClassifica(String classi){
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

        JLabel lTerzoposto = new JLabel("Andrea");
        lTerzoposto.setForeground(Color.red);
        lTerzoposto.setFont(new Font("Comic sans", Font.BOLD, 32));
        lTerzoposto.setBounds(475,170, 150, 50);
        lTerzoposto.setHorizontalAlignment(JLabel.CENTER);

        classifica.add(lTerzoposto);

        JTextArea textArea = new JTextArea ("Test");
        textArea.setFont(new Font("Comic sans", Font.PLAIN, 16));
        textArea.setForeground(Color.red);
        JScrollPane scroll = new JScrollPane (textArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setSize(300,150);
        scroll.setLocation(400-150,240);
        classifica.add(scroll);

        classifica.setLayout(null);
        classifica.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] s=classi.split(";");
        for (int i=0;i<s.length;i++){
            if(i==0){
                lPrimoposto.setText(s[i]);
            }else if(i==1){
                lSecondoPosto.setText(s[i]);
            }else if(i==2){
                lTerzoposto.setText(s[i]);
            }else{
                textArea.setText(textArea.getText()+"\n"+s[i]);
            }
        }

        bhome.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                getContentPane().add(panel);
                revalidate();
                getContentPane().repaint();
            }

        });
    }

    public void writeHome(){
        JLabel Nome = new JLabel("Username:");
        Nome.setForeground(Color.red);
        Nome.setFont(new Font("Comic sans", Font.BOLD, 16));
        Nome.setBounds(10,10, 200, 30);
        panel.add(Nome);

        JTextField fUser = new JTextField();
        fUser.setSize(100,30);
        fUser.setLocation(120,10);
        panel.add(fUser);

        JButton BUsername = new JButton("salva");
        BUsername.setSize(85,30);
        BUsername.setFont(new Font("Comic sans", Font.BOLD, 16));
        BUsername.setForeground(Color.white);
        BUsername.setBackground(Color.red);
        BUsername.setLocation(230,10);
        panel.add(BUsername);


        JButton bPageGioco = new JButton("Gioca");
        bPageGioco.setSize(100,50);
        bPageGioco.setFont(new Font("Comic sans", Font.BOLD, 20));
        bPageGioco.setForeground(Color.white);
        bPageGioco.setBackground(Color.red);
        bPageGioco.setLocation(650/2-100/2,400);
        panel.add(bPageGioco);

        JButton bClassifica =new JButton("Classifica");
        bClassifica.setSize(150,50);
        bClassifica.setFont(new Font("Comic sans", Font.BOLD, 20));
        bClassifica.setForeground(Color.white);
        bClassifica.setBackground(Color.red);
        bClassifica.setLocation(950/2-100/2,400);
        panel.add(bClassifica);

        panel.setLayout(null);

        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        bPageGioco.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                 String msg="#parola";
                 c.writeMessageThread(msg);
                 String parola=c.readMessageThread();
                gioco.removeAll();
                writeGioco(parola);
                getContentPane().add(gioco);
                revalidate();
                getContentPane().repaint();


            }

        });

        bClassifica.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                classifica.removeAll();
                 String msg="#classifica";
                 c.writeMessageThread(msg);
                 String result=c.readMessageThread();
                writeClassifica(result);
                getContentPane().add(classifica);
                revalidate();
                getContentPane().repaint();

            }

        });

        BUsername.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(fUser.getText().trim()!="") {
                    String msg = fUser.getText().trim();
                    panel.remove(fUser);
                    panel.remove(BUsername);
                    Nome.setText("Username:"+msg);
                    revalidate();
                    repaint();
                    //c.writeMessageThread("#Username:"+msg);
                }
            }
        });
    }
}
