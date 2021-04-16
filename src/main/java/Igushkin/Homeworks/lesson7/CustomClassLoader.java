package Igushkin.Homeworks.lesson7;

import java.io.FileInputStream;
import java.io.IOException;

public class CustomClassLoader extends ClassLoader {
    private String path;
    private final String fileExtension = ".class";

    public CustomClassLoader(String path) {
        this.path = path;
    }

//    public Class loadClass(String className) throws ClassNotFoundException {
//        return super.loadClass(className, true);
//    }

    protected Class findClass(String className) {
        String fullName = this.path + className + this.fileExtension;
        byte[] bytes = null;

        try {
            bytes = this.loadClassFromFile(fullName);
        } catch (IOException var5) {
            var5.printStackTrace();
        }
        return this.defineClass(className, bytes, 0, bytes.length);
    }

    private byte[] loadClassFromFile(String fullFileName) throws IOException {
        FileInputStream inputStream = new FileInputStream(fullFileName);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer, 0, size);
        return buffer;
    }
}
