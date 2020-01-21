package Laba_4;
import java.io.*;
import java.net.*;

public class Client {

    public static class LIBRC {
        private Socket sock;
        private BufferedReader reader, input;
        private BufferedWriter writer;

        LIBRC(Socket s) throws IOException {
            this.sock = s;
            this.reader = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.sock.getOutputStream()));
            this.input = new BufferedReader (new InputStreamReader (System.in, "Cp1251"));
        }

        void Check() throws IOException {
            String[] mess = (this.reader.readLine()).split("/");
            for (String s : mess) {
                System.out.println(s);
            }
        }

        void AddBook () throws IOException {
            String Book = "";
            System.out.println("Введите название книги");
            Book += this.input.readLine();
            System.out.println("Введите количество страниц");
            Book += " " + this.input.readLine();
            System.out.println("Введите жанр");
            Book += " " + this.input.readLine();
            System.out.println("Введите автора");
            Book += " " + this.input.readLine();

            this.writer.write(Book + "\n");
            this.writer.flush();
        }

        void DelBook() throws IOException {
            System.out.println("Введите название книги");
            this.writer.write(input.readLine() + "\n");
            this.writer.flush();
        }

        void SORT() throws IOException {
            System.out.println("по: 1 - Названию, 2 - кол-ву стр, 3 - Жанру, 4 - Фамилии автора");
            this.writer.write(this.input.readLine() + "\n");
            this.writer.flush();

        }

        void Poisk() throws IOException {
            System.out.println("Введите название книги.");

            this.writer.write(this.input.readLine() + "\n");
            this.writer.flush();

            System.out.println(this.reader.readLine());
        }

        void Disconect() throws IOException {
            this.reader.close();
            this.writer.close();
            this.sock.close();
        }

        void Run() {
            String Massage;
            boolean isOnline = true;

            while(isOnline) {
                System.out.println("\n1. Проверить библиотеку.\n2. Добавить книгу.\n3. Удалить книгу.\n4. Отсортировать.\n5. Поиск.\n0. Ввыход\n");
                try {
                    Massage = this.input.readLine();
                    this.writer.write(Massage + "\n");
                    this.writer.flush();
                    switch (Massage) {
                        case "1":
                            Check();
                            break;
                        case "2":
                            AddBook();
                            break;
                        case "3":
                            DelBook();
                            break;
                        case "4":
                            SORT();
                            break;
                        case "5":
                            Poisk();
                            break;
                        case "0":
                            isOnline = false;
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
        Socket clientSocket = new Socket("127.0.0.1", 8001);
        LIBRC cl = new LIBRC(clientSocket);
        cl.Run();
    }
}