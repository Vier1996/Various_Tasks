package Laba_5;
import java.net.*;
import java.io.*;

public class Client {
    public static class SCPC extends Thread {
        private Socket sock;
        private BufferedReader reader, input;
        private BufferedWriter writer;
        static boolean Enter = false;

        SCPC(Socket s) throws IOException {
            this.sock = s;
            this.reader = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
            this.input = new BufferedReader(new InputStreamReader(System.in, "Cp1251"));
        }

        void Registry() throws IOException {
            System.out.println("Введите логин и пароль (через пробел)");

            this.writer.write(input.readLine() + '\n');
            this.writer.flush();
        }

        boolean Autorizaition() throws IOException {
            String OTP;
            System.out.println("Введите логин и пароль (через пробел)");
            this.writer.write(input.readLine() + '\n');
            this.writer.flush();

            OTP = this.reader.readLine();
            return OTP.equals("1");
        }

        void CheckOnline() throws IOException {
            System.out.println(this.reader.readLine());
        }

        void Send() throws IOException {

            String Send;
            System.out.println("Выберите пользователя.");
            this.writer.write(this.input.readLine() + "\n");
            this.writer.flush();

            System.out.println("Введите сообщение.");
            Send = this.input.readLine();
            this.writer.write(Send + "\n");
            this.writer.flush();

            System.out.println(this.reader.readLine());
        }

        void Disconect() throws IOException {
            this.reader.close();
            this.writer.close();
            this.sock.close();
        }

        void CheckBox() throws IOException {
            String MSG = this.reader.readLine();
            String[] MSG2 = MSG.split("_");
            for (String s : MSG2) {
                System.out.println(s);
            }
        }

        @Override
        public void run() {
            String Massage;
            boolean isOnline = true;

            while (isOnline) {

                if (Enter) {
                    System.out.println("1. Проверить польозвателей в онлайне.\n2. Отправить сообщение.\n3. Проверить сообщения.");
                    try {
                        Massage = this.input.readLine();
                        this.writer.write(Massage + "\n");
                        this.writer.flush();
                        switch (Massage) {
                            case "1":
                                CheckOnline();
                                break;
                            case "2":
                                Send();
                                break;
                            case "3":
                                CheckBox();
                                break;
                            case "0":
                                isOnline = false;
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!Enter) {
                    System.out.println("1. Авторизация.\n2. Регистрация.");
                    try {
                        Massage = this.input.readLine();
                        this.writer.write(Massage + '\n');
                        this.writer.flush(); //отправка 1
                        switch (Massage) {
                            case "1":
                                Enter = Autorizaition();
                                if(!Enter)
                                    System.out.println("Неправильно введен логин или пароль");
                                break;
                            case "2":
                                Registry();
                                break;
                            case "0":
                                isOnline = false;
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Disconect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) throws IOException {

        boolean isOnline = true;
        Socket clientSocket = new Socket("127.0.0.1", 8002);
        SCPC cl = new SCPC(clientSocket);
        cl.start();
    }
}