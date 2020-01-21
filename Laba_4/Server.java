package Laba_4;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    static int Num = 0;
    public static class LIBRS extends Thread {
        private Socket sock;
        private boolean isOnline = false;
        int number;
        private BufferedReader reader, readerF;
        private BufferedWriter writer, writerF;
        private File file;

        LIBRS(Socket in, int N) throws IOException {
            this.sock = in;
            this.number = N;
            this.isOnline = true;
            this.reader = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
            this.file = new File("C://Users//Макс//Desktop//Lib.txt");
            this.writerF = new BufferedWriter(new FileWriter(file, true));
            this.readerF = new BufferedReader(new FileReader(file));
            Adding();
        }

        ArrayList<String> Adding() throws IOException {
            File file1 = new File("C://Users//Макс//Desktop//Lib.txt");
            BufferedReader RF = new BufferedReader(new FileReader(file1));

            ArrayList<String> TT = new ArrayList<>();
            String mess = "";
            while ((mess = RF.readLine()) != null) {
                TT.add(mess);
            }

            RF.close();

            return TT;
        }

        void CheckLib() throws IOException {
            ArrayList<String> Pool = new ArrayList<String>(Adding());
            String Lib = "";
            for (String s : Pool) {
                Lib += s + "/";
            }
            System.out.println("Я отправил клиенту: " + Lib);
            this.writer.write(Lib + "\n");
            this.writer.flush();
        }

        void AddBoook() throws IOException {
            String Book = this.reader.readLine();
            System.out.println("Я получил от клиента: " + Book);
            this.writerF.write(Book + "\n");
            this.writerF.flush();

        }

        void DelBook() throws IOException {
            String Naz = reader.readLine(), BOOKS = "";
            System.out.println("Я получил от клиента: " + Naz);
            ArrayList<String> Pool = new ArrayList<String>(Adding());

            File file1 = new File("C://Users//Макс//Desktop//Lib.txt");
            BufferedWriter writerFF = new BufferedWriter(new FileWriter(file1, false));

            for (int i = 0; i < Pool.size(); i++) {
                String[] MS = Pool.get(i).split(" ");
                if (MS[0].equals(Naz))
                    Pool.remove(i);

            }
            for (String s : Pool) {
                BOOKS += s + "\n";
            }

            writerFF.write(BOOKS);
            writerFF.close();
        }

        void SORT() throws IOException {
            String choose_ = reader.readLine();

            System.out.println("Я получил от клиента: " + choose_);
            switch (choose_) {
                case "1":
                    Sorting(0, false);
                    break;
                case "2":
                    Sorting(1, true);
                    break;
                case "3":
                    Sorting(2, false);
                    break;
                case "4":
                    Sorting(3, false);
                    break;
            }

        }

        void Sorting(int Pos, boolean T) throws IOException {
            ArrayList<String> Pool = new ArrayList<String>(Adding());
            if (T) {
                for (int i = 0; i < Pool.size() - 1; i++) {
                    for (int j = i + 1; j < Pool.size(); j++) {
                        String str1[] = (Pool.get(i)).split(" ");
                        String str2[] = (Pool.get(j)).split(" ");
                        int t1 = Integer.parseInt(str1[Pos]), t2 = Integer.parseInt(str2[Pos]);
                        if (t1 > t2) {
                            String str = Pool.get(i);
                            Pool.set(i, Pool.get(j));
                            Pool.set(j, str);
                        }

                    }
                }
            } else
                Collections.sort(Pool, new SampleComparator(Pos));

            PrintWriter pw = new PrintWriter("C://Users//Макс//Desktop//Lib.txt");
            pw.close();
            for (String Book : Pool) {
                this.writerF.write(Book + "\n");
                this.writerF.flush();
            }
        }

        void Poisk() throws IOException {
            ArrayList<String> Pool = new ArrayList<String>(Adding());
            String Input_data = this.reader.readLine(), Output_data = "";
            System.out.println("Я получил от клиента: " + Input_data);
            for (String s : Pool) {
                String[] INF = s.split(" ");
                if (INF[0].equals(Input_data)) {
                    Output_data += s;
                    break;
                }
            }
            System.out.println("Я отправил клиенту: Ваша информаця о книге: " + Output_data);
            this.writer.write("Ваша информаця о книге: " + Output_data + "\n");
            this.writer.flush();
        }

        void Disconect() throws IOException {
            this.writer.close();
            this.reader.close();
            this.sock.close();
            this.writerF.close();
        }

        @Override
        public void run() {
            try {
                String Massage;
                boolean Enter = false;
                while(isOnline) {

                    Massage = reader.readLine();
                    System.out.println("Я получил от клиента: " + Massage);
                    switch (Massage) {
                        case "1": System.out.println("Клиент #" + number + " выбрал проверку библиотеки."); CheckLib(); break;
                        case "2": System.out.println("Клиент #" + number + " выбрал добавить книгу."); AddBoook(); break;
                        case "3": System.out.println("Клиент #" + number + " выбрал удалить книгу."); DelBook(); break;
                        case "4": System.out.println("Клиент #" + number + " выбрал сортировку."); SORT(); break;
                        case "5": System.out.println("Клиент #" + number + " выбрал поиск."); Poisk(); break;
                        case "0": System.out.println("Клиент #" + number + " решил отключиться."); isOnline = false; break;
                    }
                }
                Disconect();
            } catch (Exception ex) {
                System.out.println(ex);
            } finally {
                System.out.println("Клиент #" + number + " вышел из сети. " + '\n');
                System.out.println("Клиентов онлайн " + (--Num) + '\n');
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serversocket = new ServerSocket(8001);
        System.out.println("Cервер запущен...");
        boolean servOnline = true;
        while(servOnline){
            Socket clientsocket = serversocket.accept();
            System.out.println("Порт клиента : " + clientsocket.getPort());
            System.out.println("/IP адресс клиента: " + clientsocket.getLocalAddress());
            System.out.println("Подключен клиент " + (++Num) + '\n');
            LIBRS nCl = new LIBRS(clientsocket, Num);
            nCl.start();
        }
        serversocket.close();
        System.out.println("Cервер отключен...");
    }

}
class SampleComparator implements Comparator<String> {
    int Pos;
    public
    @Override
    int compare(String o1, String o2) {
        String array1[] = o1.split(" ");
        String array2[] = o2.split(" ");
        return array1[Pos].compareTo(array2[Pos]);
    }
    SampleComparator(int pos) {
        Pos = pos;
    }
}