import java.io.*;
import java.util.*;

public class input_output {


    public void read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("numbers.txt"));
//        PrintWriter pw = new PrintWriter(new FileWriter("output.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.print(line);
        }


    }



    public static void lineCounter() throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));
        int lines = 0;
        while (in.hasNextLine()) {
            in.nextLine();
            lines++;
        }
        System.out.println(lines);
        in.close();
    }


    public static void hashmapReader() throws FileNotFoundException {
        HashMap<String, String> hm = new HashMap<>();
        Scanner in = new Scanner(new File("input.txt"));

        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] parts = line.split("\\s+");
            hm.put(parts[0], parts[1]);

        }
    }

    public static void searchWord() throws FileNotFoundException {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter filename: ");
        String filename = in.nextLine();

        System.out.print("Enter word to search: ");
        String word = in.nextLine();

        Scanner fileReader = new Scanner(new File(filename));
        int count = 0;

        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            if (line.contains(word)) {
                System.out.println(line);
                count++;
            }
        }

        System.out.println("Found in " + count + " lines!");

        in.close();
        fileReader.close();
    }


    public static void main(String[] args) throws FileNotFoundException {
        searchWord();
    }


}
