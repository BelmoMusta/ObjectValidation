package musta.belmo.validation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DELL on 24/01/2018.
 */
public class Main {


    static String readFile() throws Exception {
        String pathname = "C:\\Users\\DELL\\Desktop\\contatcts.txt";
        File f = new File(pathname);


        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();

        String sCurrentLine;

        while ((sCurrentLine = br.readLine()) != null) {
            sb.append(sCurrentLine);
            System.out.println(sCurrentLine);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String pathname = "C:\\Users\\DELL\\Desktop\\c.csv";
        Scanner sc = new Scanner(new File(pathname));
        StringBuilder sb = new StringBuilder();


        while (sc.hasNext()) {

            String next = sc.nextLine();
            String[] split = next.split(";");

            String num = split[0].replaceAll("^[0]", "+212");
            String name = split[1];


            sb.append("BEGIN:VCARD\n" +
                    "VERSION:2.1\n")
                    .append("N:;").append(name.replace(' ',';')).append(";;;").append('\n')
                    .append("FN:").append(name).append('\n')
                   .append("TEL;CELL:").append(num).append('\n')
                    .append("END:VCARD").append('\n');


        }
        FileWriter fw = new FileWriter(pathname + "__");
        fw.append(sb);
        fw.close();
        System.out.println(sb);

    }
}
