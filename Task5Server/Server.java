package Task5Server;
import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class Server {
    private static ArrayList<String> Users = new ArrayList<>();
    private static boolean ServOnline = true;

    public static class SCP extends Thread {
        private Socket sock;
        private String NAME;
        private boolean isOnline = false;
        int number;
        private BufferedReader reader, readerF, readerFM;
        private BufferedWriter writer;
        private FileWriter writerF, writerFM;

        SCP(Socket in, int N) throws IOException {
            this.sock = in;
            this.number = N;
            this.isOnline = true;
            this.reader = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
            File file = new File("C://Users//Макс//Desktop//Logins.txt"), fileM = new File("C://Users//Макс//Desktop//Messg.txt");;
            this.writerF = new FileWriter(file, true);
            this.readerF = new BufferedReader(new FileReader(file));
            this.writerFM = new FileWriter(fileM, true);
            this.readerFM = new BufferedReader(new FileReader(fileM));
        }

        void Disconect() throws IOException {
            this.writer.close();
            this.reader.close();
            this.sock.close();
            this.writerF.close();
            this.writerFM.close();
            Users.remove(number);
        }

        boolean Autor() throws IOException {
            String OUTPUT, mess="", INFO[];
            OUTPUT = this.reader.readLine();

            while((mess = this.readerF.readLine())!= null) {
                if(mess.equals(OUTPUT)) {
                    INFO = OUTPUT.split(" ");
                    OUTPUT = Integer.toString(this.sock.getPort());
                    Users.add(this.number = Users.size(),INFO[0] + " " + OUTPUT);
                    this.NAME = INFO[0];
                    this.writer.write("1" + "\n");
                    this.writer.flush();
                    return true;
                }
            }

            this.writer.write("0" + "\n");
            this.writer.flush();
            return false;
        }

        void Registry() throws IOException {
            String OUTPUT;
            this.writer.write("Введите логин и пароль через пробел" + '\n');
            this.writer.flush();

            OUTPUT = this.reader.readLine();

            writerF.write(OUTPUT + "\n");
            writerF.flush();
        }

        void CheckOnline() throws IOException {

            String ONUSr = "Пользователи в онлайне:";
            int Raz = Users.size();

            for (String user : Users) {
                String[] Dev = user.split(" ");
                ONUSr += " || " + Dev[0];
            }
            this.writer.write(ONUSr + "\n");
            this.writer.flush();
        }

        void Send() throws IOException {

            String Dest = this.reader.readLine(), SendMsg = this.reader.readLine();

            for (String user : Users) {
                String[] Dlr = user.split(" ");
                if (Dlr[1].equals(Integer.toString(this.sock.getPort())))
                    NAME = Dlr[0];
            }

            this.writerFM.write(Dest + " " + SendMsg + " " + NAME + " " +"\n");
            this.writerFM.flush();

            this.writer.write("Отправленно!" + "\n");
            this.writer.flush();
        }

        void CheckBox() throws IOException {
            String mess="", deliver = "", MSG[];

            while((mess = this.readerFM.readLine())!= null) {
                MSG = mess.split(" ");
                if (MSG[0].equals(NAME)) {
                    deliver += "От: " + MSG[MSG.length - 1] + "| Сообщение:";
                    for (int i = 1; i < MSG.length - 1; i++)
                        deliver += " " + MSG[i];
                    deliver += " _";
                }
            }

            this.writer.write(deliver + "\n");
            this.writer.flush();
        }

        @Override
        public void run() {
                try {
                    String Massage;
                    boolean Enter = false;

                    while(isOnline) {

                        Massage = reader.readLine();
                        if(Enter) {
                            switch (Massage) {
                                case "1": System.out.println("Клиент #" + number + " выбрал проверку онлайна."); CheckOnline(); break;
                                case "2": System.out.println("Клиент #" + number + " выбрал отправку сообщения."); Send(); break;
                                case "3": System.out.println("Клиент #" + number + " выбрал проверку почты."); CheckBox(); break;
                                case "0": System.out.println("Клиент #" + number + " решил отключиться."); isOnline = false; break;
                            }
                        }
                        else {
                            switch (Massage) {
                                case "1": System.out.println("Клиент #" + number + " решил авторизоваться."); Enter = Autor();
                                        if(Enter) {
                                            System.out.println("Клиент #" + number + " авторизоавлся. (LOGIN:" + this.NAME + ")");
                                        }break;
                                case "2": System.out.println("Клиент #" + number + " решил зарегистрироваться."); Registry();
                                            System.out.println("Клиент #" + number + " ---Зарегистрирован."); break;
                                case "0": System.out.println("Клиент #" + number + " решил отключиться."); isOnline = false; break;
                            }
                        }

                    }
                    Disconect();
                } catch (Exception ex) {
                    System.out.println(ex);
                } finally {
                    System.out.println("Клиент #" + number + " вышел из сети. " + '\n');
                }
        }

    }

    public static void main(String[] args) throws IOException {
        int count = 0;
        ServerSocket serversocket = new ServerSocket(8002);
        System.out.println("Cервер запущен...");
        while(ServOnline){
            Socket clientsocket = serversocket.accept();
            System.out.println("Порт клиента: " + clientsocket.getPort());
            System.out.println("Подключен клиент " + (count) + '\n');
            SCP nCl = new SCP(clientsocket, count);
            nCl.start();
            count++;
        }
        serversocket.close();
        System.out.println("Cервер отключен...");
    }
}
