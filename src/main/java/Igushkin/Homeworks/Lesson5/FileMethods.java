package Igushkin.Homeworks.Lesson5;

import java.io.*;
import java.util.ArrayList;

/**
 * Содержит в себе общие методы для работы с файлами.
 */
public class FileMethods {

    /**
     * Проверяет, существует ли указанный файл.
     * @param fileName полное имя файла
     * @return true или false
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * Считывает строки из файла в ArrayList<Strings>
     * @param fileName
     * @return ArrayList<Strings>
     * @throws IOException
     */
    public static ArrayList<String> readFileToList (String fileName) throws IOException {
        ArrayList<String> linesFromFile = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                linesFromFile.add(line);
            }
        }
        return linesFromFile;
    }

    /**
     * Записывает строковые значения из ArrayList<String> в указанный файл, каждое с новой строки.
     * @param lines список строк для записи в файл
     * @param filename файл для записи строк
     * @throws IOException
     */
    public static void writeArrayListToFile (ArrayList<String> lines, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
            writer.flush();
        }
    }

    public static void printFile (String fileName) throws IOException {
        ArrayList<String> linesFromFile = FileMethods.readFileToList(fileName);
        for (String line : linesFromFile) {
            System.out.println(line);
        }
    }

    public static void printLineFormFile (int lineNumber, String fileName) throws IOException {
        ArrayList<String> linesFromFile = FileMethods.readFileToList(fileName);
        if (lineNumber > linesFromFile.size()){
            throw new WrongLineNumberException("Номер строки превышает количество строк в файле!");
        } else {
            System.out.println(linesFromFile.get(lineNumber));
        }
    }

    /**
     * Записывает строку в файл. Использовать, когда номер строки меньше количества строк в файле.
     * @param lineNumber номер строки, на которую нужно записать
     * @param fileName имя файла для записи
     * @param lineToWrite строка, которую нужно записать
     * @throws IOException
     */
    public static void writeInsideFile (int lineNumber, String fileName, String lineToWrite) throws IOException {
        ArrayList<String> linesFromFile = FileMethods.readFileToList(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < linesFromFile.size(); i++) {
                if (lineNumber == i) {
                    writer.write(lineToWrite + "\n");
                }
                writer.write(linesFromFile.get(i) + "\n");
            }
            writer.flush();
        }
    }

    /**
     * Записывает текст в файл на указанную строку. Использовать, если номер строки больше количества строк в файле.
     * Добавляет пустые строки при необходимости.
     * @param lineNumber номер строки для записи. Должен быть больше количества строк в файле.
     * @param fileName имя файла для записи
     * @param lineToWrite текст для записи в файл
     * @throws IOException
     */
    public static void writeAfterFile (int lineNumber, String fileName, String lineToWrite) throws IOException {
        ArrayList<String> linesFromFile = FileMethods.readFileToList(fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String lineFromFile : linesFromFile) {
                writer.write(lineFromFile + "\n");
            }
            for (int i = linesFromFile.size() + 1; i <= lineNumber; i++) {
                writer.newLine();
            }
            writer.write(lineToWrite + "\n");
            writer.flush();
        }
    }

    /**
     * Записывает строку в конец файла.
     * @param string строка для записи
     * @param fileName файл для записи
     * @throws IOException
     */
    public static void writeStringToEnd (String fileName, String string) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) {
            writer.write(string + "\n");
            writer.flush();
        }
    }
}
