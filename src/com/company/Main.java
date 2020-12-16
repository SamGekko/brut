package com.company;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Scanner;

public class Main {


    /*  1115dd800feaacefdf481f1f9070374a2a81e27880f187396db67958b207cbad  - zyzzx
    3a7bd3e2360a3d29eea436fcfb7e44c735d117c42d1c1835420b6b9942dd4f1b - apple
    74e1bb62f8dabb8125a58852b63bdf6eaef667cb56ac7f7cdba6d7305c50a22f - mmmmm
    */

    public static String hashing(String pass) throws Exception // метод с хэшом
    {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] textInBytes = messageDigest.digest(pass.getBytes(StandardCharsets.UTF_8));
        StringBuilder strb = new StringBuilder();
        byte[] b = textInBytes;
        for (int i = 0; i < textInBytes.length; i++) {
            byte c = textInBytes[i];
            strb.append(String.format("%02x", c));
        }
        return strb.toString();
    }

    public static String hash = "";

    static MyThread Threadf_j, Threadk_o, Threadp_t, Threadu_z; // объявление потоков (всего их 5 основной и 4 побочных))

    public static void main(String[] args) throws Exception
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите хэш: ");
        hash = in.nextLine();

        Threadf_j = new MyThread(5, hash);  // f - j
        Threadk_o = new MyThread(10, hash); // k - o
        Threadp_t = new MyThread(15, hash); // p - t
        Threadu_z = new MyThread(20, hash); // u - z
        Threadf_j.start();
        Threadk_o.start();
        Threadp_t.start();
        Threadu_z.start();

        char[] mas_p = new char[5]; // массив в котором будем собирать пароль

        for (int i = 0; i < 5; i++) // цикл перебора
        {
            mas_p[0] = (char) ('a' + i);

            for (int j = 0; j < 26; j++) {
                mas_p[1] = (char) ('a' + j);

                for (int k = 0; k < 26; k++) {
                    mas_p[2] = (char) ('a' + k);

                    for (int t = 0; t < 26; t++) {
                        mas_p[3] = (char) ('a' + t);

                        for (int y = 0; y < 26; y++) {
                            mas_p[4] = (char) ('a' + y);
                            String password = String.valueOf(mas_p); //собираем пароль в строку
                            String pass_hash = hashing(password); // получаем хэш собранного пароля


                            if (hash.equals(pass_hash)) //сравнение
                            {
                                System.out.println("Пароль: " + password +"\n Хэш пароля: " + hash);
                                Threadf_j.interrupt();
                                Threadk_o.interrupt();
                                Threadp_t.interrupt();
                                Threadu_z.interrupt();
                                System.exit(1);//завершение программы
                            }
                        }
                    }
                }
            }
        }
    }


    static class MyThread extends Thread
    {
        private int startChar;
        String hash;

        public MyThread(int startChar, String hash) throws Exception {
            this.startChar = startChar;
            this.hash = hash;
        }

        @Override
        public void run()
        {
            int N = 5; // в потоке перебираем по 5 букв
            char[] mas = new char[5];
            if (startChar == 20) N = 6; // т.к в англ алфавите 26 букв, а перебираем по 5, то последний поток должен перебирать 6 букв

            for (int i = 0; i < N; i++) //цикл перебора вторичных потоков
            {
                mas[0] = (char) ('a' + i + startChar);
                for (int j = 0; j < 26; j++) {
                    mas[1] = (char) ('a' + j);
                    for (int k = 0; k < 26; k++) {
                        mas[2] = (char) ('a' + k);
                        for (int l = 0; l < 26; l++) {
                            mas[3] = (char) ('a' + l);
                            for (int m = 0; m < 26; m++) {
                                mas[4] = (char) ('a' + m);
                                String pass = String.valueOf(mas);
                                String hashp = null;
                                try {
                                    hashp = hashing(pass);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (hashp.equals(hash)) {
                                    System.out.println("Пароль: " + pass +"\n Хэш пароля: " +hash);
                                    System.exit(1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
