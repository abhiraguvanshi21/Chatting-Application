package chatting.application;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.*;
import java.io.*;

public class Client implements ActionListener {

    JTextField text;
    JButton send;
   static JLabel a1;
   static DataOutputStream dout;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();


    Client(){

        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(9,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                f.setVisible(false);
                super.mouseClicked(ae);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icon/mirzapur.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel pic = new JLabel(i6);
        pic.setBounds(40,10,50,50);
        p1.add(pic);


        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icon/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel logo = new JLabel(i9);
        logo.setBounds(300,20,30,30);
        p1.add(logo);


        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icon/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel pi = new JLabel(i12);
        pi.setBounds(360,20,35,30);
        p1.add(pi);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icon/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel p = new JLabel(i15);
        p.setBounds(420,22,10,25);
        p1.add(p);


        JLabel name = new JLabel("Ayush");
        name.setBounds(110,16,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("Open Sans",Font.BOLD,18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Open Sans",Font.BOLD,10));
        p1.add(status);

        send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
        send.addActionListener(this);
        f.add(send);


        a1 = new JLabel();
        a1.setBounds(5,75,440,570);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);

        f.setSize(450,700);
        f.setLocation(800,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);

    }

    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();
            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);

            vertical.add(right);
            vertical.add(Box.createVerticalStrut(12));
            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args){
        new Client();

        try{
            Socket s = new Socket("127.0.0.1",6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true){
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel mpanel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(mpanel,BorderLayout.LINE_START);
                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);
                f.validate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
