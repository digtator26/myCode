import pe.ImportEntry;
import pe.LibraryImports;
import pe.PeImage;
import pe.io.CadesFileStream;
import pe.io.CadesStreamReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static final Set<String> NETWORK_FUNCTIONS = new HashSet<>();

    static {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("NetworkFunctions.txt"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                NETWORK_FUNCTIONS.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> check(String PEFile) {
        List<String> result = new ArrayList<>();
        try {
            CadesStreamReader stream = new CadesFileStream(new File(PEFile));

            PeImage pe = PeImage.read(stream).getOkOrDefault(null);

            if (pe != null) {
                pe.imports.ifOk(libraryImports -> {
                    for (LibraryImports lib: libraryImports) {
                        for (ImportEntry entry : lib.entries) {
                            if (NETWORK_FUNCTIONS.contains(entry.name)) {
                                result.add(entry.name);
                            }
                        }
                    }
                });
            }
        } catch (FileNotFoundException e) {

        }
        return result;
    }

    public static void main(String[] args) {
        File dir = new File("C:\\");
        showFiles(dir.listFiles());
    }

    public static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                if (file.listFiles() != null) {
                    showFiles(file.listFiles());
                }
            } else {
                List<String> list = check(file.getAbsolutePath());
                if (!list.isEmpty()) {
                    System.out.println("Проверка файла " + file.getAbsolutePath() + ":");
                    System.out.println(list);
                    System.out.println("------------------------------");
                }
            }
        }
    }
}
