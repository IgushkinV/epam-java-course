package igushkin.homeworks.lesson10.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileSystemObject {

    private List<FileSystemObject> children;
    private boolean directory;
    private Path path;
    public FileSystemObject(Path path) {
        this.path = path;
    }

    public List<FileSystemObject> getChildren() {
        return children;
    }

    public void setIsDirectory (boolean directory) {
        this.directory = directory;
    }

    public static void recurse(FileSystemObject parent) {
        try {
            parent.children = Files.list(parent.path)
                    .map(FileSystemObject::new)
                    .peek(pathObj -> {
                        if (Files.isDirectory(pathObj.path)) {
                            pathObj.setIsDirectory(true);
                            recurse(pathObj);
                        } else {
                            pathObj.setIsDirectory(false);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
