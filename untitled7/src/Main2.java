import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Main2 {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("cm.txt")))) {
            String cur = bufferedReader.readLine();
            while (cur != null) {
                set.add(cur);
                cur = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("m.txt")))) {
            String cur = bufferedReader.readLine();
            while (cur != null) {
                set.remove(cur);
                cur = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("reqm.txt")))) {
            String cur = bufferedReader.readLine();
            while (cur != null) {
                set.remove(cur);
                cur = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("resm.txt")))) {
            String cur = bufferedReader.readLine();
            while (cur != null) {
                set.remove(cur);
                cur = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(set);

    }

}
