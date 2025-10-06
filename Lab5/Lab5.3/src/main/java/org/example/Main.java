import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class Main {

    public static void main(String[] args) {
        // Путь к папке, которую хотим архивировать
        String sourceFolder = "D:\\LabPPK2\\Lab5\\Lab5.3";
        // Путь и имя выходного zip-файла
        String zipFilePath = "D:\\LabPPK2\\Lab5\\archive.zip";

        try {
            zipFolder(sourceFolder, zipFilePath);
            System.out.println("Архивация завершена успешно!");
        } catch (IOException e) {
            System.err.println("Ошибка при архивации: " + e.getMessage());
        }
    }

    public static void zipFolder(String sourceFolderPath, String zipFilePath) throws IOException {
        Path zipFile = Files.createFile(Paths.get(zipFilePath));
        Path sourceDirPath = Paths.get(sourceFolderPath);

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            Files.walk(sourceDirPath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                        try {
                            zos.putNextEntry(zipEntry);
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            System.err.println("Ошибка при добавлении файла в архив: " + path);
                        }
                    });
        }
    }
}
