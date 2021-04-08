package Igushkin.Homeworks.Lesson5;

import java.io.*;
import java.lang.reflect.Field;
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

    /**
     * Записывает переданную строку в файл на указанную позицию, сдвигая существующие в файле строки.
     * @param lineNumber позиция для записи
     * @param stringToWrite строка для записи
     * @param fileName файл для записи
     * @throws IOException
     */
    public static void stringWriter (int lineNumber, String stringToWrite, String fileName) throws IOException {
        ArrayList<String> linesFromFile = FileMethods.readFileToList(fileName);
        //2 варианта записи
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            //1 вариант - запись между существующих строк. Отсчет строк с 0.
            if (lineNumber < linesFromFile.size()) {
                for (int i = 0; i < linesFromFile.size(); i++) {
                    if (lineNumber == i) {
                        writer.write(stringToWrite + "\n");
                    }
                    writer.write(linesFromFile.get(i) + "\n");
                }
                //2 вариант - запись после существующих строк. Добавляет пустые при необходимости. Отсчет строк с 0.
            } else {
                for (String line : linesFromFile) {
                    writer.write(line + "\n");
                }
                for (int i = linesFromFile.size() + 1; i <= lineNumber; i++) {
                    writer.newLine();
                }
                writer.write(stringToWrite + "\n");
            }
            writer.flush();
        }
    }

    /**
     * Записывает строку в конец файла.
     * @param string строка для записи
     * @param fileName файл для записи
     * @throws IOException
     */
    public static void writeStringToEnd (String string, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) {
            writer.write(string + "\n");
        }
    }
}
