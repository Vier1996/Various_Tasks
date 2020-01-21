package com.example.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class GUI {
    private static String[] data2 = { "January", "Febuary", "March", "April", "May", "June", "Jule", "August",
            "September", "October", "November", "December"};
    static class Panel extends JFrame{

        private JRadioButton radio1 = new JRadioButton("Мужской");
        private JRadioButton radio2 = new JRadioButton("Женский");
        private JCheckBox check = new JCheckBox("Политика конфедициальности", false);

        JButton button, buttonFile;
        JTextField text1, text2, text3, text4;
        JLabel label1, label2, label3, label4, label5, label6, BG;
        JList list;
        JComboBox comboBox = new JComboBox(data2), comboBox1 = new JComboBox(), comboBox2 = new JComboBox();
        Panel(){
            super("VK.com");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 600);
            setLocation(400, 100);

            int red = 39;
            int green = 152;
            int blue = 232;

            float[] hsb = Color.RGBtoHSB(39, 152, 232, null);
            getContentPane().setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));

            button = new JButton("Зарегистрироватся");
            buttonFile = new JButton("Прочесть данные\n из файла.");
            text1 = new JTextField();
            text2 = new JTextField();
            text3 = new JTextField();
            text4 = new JTextField();

            label1 = new JLabel("Ваше имя:");
            label2 = new JLabel("Ваша фамилия:");
            label3 = new JLabel("Пол.");
            label4 = new JLabel("Номер телефона");
            label5 = new JLabel("Электронный адрес");
            label6 = new JLabel("Дата рождения.");

            setLayout(null);
            button.setBounds(220, 500, 150, 50);
            buttonFile.setBounds(380, 500, 210, 50);

            label1.setSize(70, 30);
            label1.setLocation(120,0);
            text1.setSize(200, 50);
            text1.setLocation(50,30);

            label2.setSize(100, 30);
            label2.setLocation(390,0);
            text2.setSize(200, 50);
            text2.setLocation(340,30);

            label4.setSize(130, 30);
            label4.setLocation(100,100);
            text3.setSize(200, 50);
            text3.setLocation(50,130);

            label5.setSize(150, 30);
            label5.setLocation(380,100);
            text4.setSize(200, 50);
            text4.setLocation(340,130);


            label3.setSize(100, 50);
            label3.setLocation(130,190);

            list = new JList(data2);


            ButtonGroup group = new ButtonGroup();
            group.add(radio1);
            group.add(radio2);

            radio1.setSize(80, 30);
            radio1.setLocation(60, 230);
            radio1.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
            radio2.setSize(80, 30);
            radio2.setLocation(150, 230);
            radio2.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));

            label6.setSize(100, 50);
            label6.setLocation(365, 200);

            list.setSize(70, 220);
            list.setLocation(380, 250);

            for(int i = 1; i <= 31; i++)
            {
                comboBox1.addItem(Integer.toString(i));
            }
            comboBox.setSelectedItem(null);
            comboBox.setBounds(360,250, 100, 31);
            comboBox1.setSelectedItem(null);
            comboBox1.setBounds(260,250, 100, 31);

            for(int i = 1; i <= 35; i++)
            {
                comboBox2.addItem(Integer.toString(2020 - i));
            }
            comboBox2.setSelectedItem(null);
            comboBox2.setBounds(460,250, 100, 31);

            check.setBounds(50, 450, 215, 22);
            check.setBackground(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));

            add(radio1);
            add(radio2);

            add(button);
            add(buttonFile);
            add(label1);
            add(text1);
            add(label2);
            add(text2);
            add(label4);
            add(text3);
            add(label5);
            add(text4);
            add(label3);

            add(label6);
            add(comboBox);
            add(comboBox1);
            add(comboBox2);
            add(check);

            list.addMouseListener(new ButtonActionLisener());
            button.addActionListener(new ButtonEventListner());
            buttonFile.addActionListener(new ButtonEventListner1());
        }

        String Shifr(String data){
        String outp = "";
        char[] Pool = data.toCharArray();
        for(int i = 0; i < Pool.length; i++){
            outp += Character.toString((char)((int)Pool[i] + 15));
        }
            return outp;
        }

        String DeShifr(String data){
            String outp = "";
            char[] Pool = data.toCharArray();
            for(int i = 0; i < Pool.length; i++){
                outp += Character.toString((char)((int)Pool[i] - 15));
            }
            return  outp;
        }

        class ButtonEventListner implements  ActionListener {
            public void actionPerformed(ActionEvent e){
                File file = new File("C://Users//Макс//Desktop//Output.txt");
                boolean P;
                try {
                    String Gender = "";
                    if(radio1.isSelected())
                        Gender += "Мужской";
                    else
                        Gender += "Женский";

                    P = check.isSelected();

                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

                    if(P){
                        writer.append(Shifr(text1.getText()) + " " + Shifr(text2.getText()) + " " + Shifr(text3.getText()) +
                                " " + Shifr(text4.getText()) + " " + Shifr(Gender) + " " + Shifr(comboBox1.getSelectedItem().toString()) + " "
                                + Shifr(comboBox.getSelectedItem().toString()) + " " + Shifr(comboBox2.getSelectedItem().toString()) + " Y" + '\n');
                    }
                    else{
                        writer.append(text1.getText() + " " + text2.getText() + " " + text3.getText() +
                                " " + text4.getText() + " " + Gender + " " + comboBox1.getSelectedItem() + " "
                                + comboBox.getSelectedItem() + " " + comboBox2.getSelectedItem() + " N" + '\n');
                    }
                    writer.flush();
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                String Massage = "                     Зарегистрирован(a)";
                JOptionPane.showMessageDialog(null, Massage, "Output", JOptionPane.PLAIN_MESSAGE);
            }
        }

        class ButtonEventListner1 implements  ActionListener {
            public void actionPerformed(ActionEvent e) {
                boolean DeSh;
                String output = "";
                String[] MassOup;
                String Massage = "";
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("C://Users//Макс//Desktop//Output.txt"));
                    output = reader.readLine();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                MassOup = output.split(" ");
                if(MassOup[8].equals("Y")){
                    Massage += "Имя: " + DeShifr(MassOup[0]) + "\nФамилия: " + DeShifr(MassOup[1]) + "\nНомер телефона: " + DeShifr(MassOup[2]) +
                            "\nЭлектронный адрес: " + DeShifr(MassOup[3]) + "\nПол: " + DeShifr(MassOup[4]) + "\nДата рождения: " +
                            DeShifr(MassOup[5]) + " " + DeShifr(MassOup[6]) + " " + DeShifr(MassOup[7]);
                }
                else {
                    Massage += "Имя: " + MassOup[0] + "\nФамилия: " + MassOup[1] + "\nНомер телефона: " + MassOup[2] +
                            "\nЭлектронный адрес: " + MassOup[3] + "\nПол: " + MassOup[4] + "\nДата рождения: " +
                            MassOup[5] + " " + MassOup[6] + " " + MassOup[7];
                }

                JOptionPane.showMessageDialog(null, Massage, "Output", JOptionPane.PLAIN_MESSAGE);
            }
        }

        public class ButtonActionLisener implements MouseListener {
            //  Если пользователь нажал и отпустил одну из кнопок, вызывается метод mouseClicked.
            public void mouseClicked(MouseEvent e) {
                list =  (JList) e.getSource();
                Object text =  list.getSelectedValue();
                text1.setText((String)text);
            }
            // mouseEntered - данный метод будет вызываться системой у слушателя каждый раз, когда курсор мыши будет оказываться над компонентом.
            public void mouseEntered(MouseEvent e) {
                list = (JList) e.getSource();
                text1.setText("метод mouseEntered()");
            }
            // mouseExited – данный метод срабатывает, когда убираем курсор мыши с компонента. 
            public void mouseExited(MouseEvent e) {
                list = (JList) e.getSource();
                text1.setText("метод mouseExited()");
            }
                            // Навели на компонент, зажали кнопку — система вызвала mousePressed. 
            public void mousePressed(MouseEvent e) {
                list = (JList) e.getSource();
                text1.setText("метод mousePressed()");
            }
            // Отпускаем кнопку — система вызвала mouseReleased.
            public void mouseReleased(MouseEvent e) {
                list = (JList) e.getSource();
                text1.setText("метод mouseReleased()");
            }
        }

    }

    public static void main(String[] args) {

        Panel GrUsIn = new Panel();
        GrUsIn.setVisible(true);

    }
}
