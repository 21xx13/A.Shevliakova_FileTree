package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {

    public static void main(String[] args) throws IOException {
        fileVisitorOutput(Paths.get("src/main/resources/"));
        System.out.println();
        recursionFileOutput(new File("src/main/resources/"));
        System.out.println();
        catalogNamesOutput(new File("src/main/resources/"), 0);
    }

    //Способ 1.1 - реализация через встроенный метод walkFileTree
    public static void fileVisitorOutput(Path rootDir) throws IOException {
        Files.walkFileTree(rootDir, new SimpleFileVisitor<Path>() {
            @Override //переопределение метода для вывода пути файла в консоль во время обхода
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                System.out.println(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    //Способ 1.2 - обход через рекурсию. Вывод такой же, как и в предыдущем методе
    public static void recursionFileOutput(File directory) {
        File[] files = directory.listFiles();
        if (files == null) throw new NullPointerException();
        for (File file : files) {
            if (file.isDirectory()) //если текущий элемент каталог - идём на уровень ниже
                recursionFileOutput(file);
            else System.out.println(file); //иначе - печатаем элемент
        }
    }

    //Способ 2.1 - вывод названий файлов и каталогов с учётом вложенности
    public static void catalogNamesOutput(File directory, int level) {
        File[] files = directory.listFiles();
        if (files == null) throw new NullPointerException();
        for (File file : files) {
            //выводим название текущего элемента с количеством отступов, соответствующим уровню вложенности
            System.out.println(new String(new char[level]).replace("\0", "\t") + file.getName());
            if (file.isDirectory()) //если текущий элемент каталог - идём на уровень ниже
                catalogNamesOutput(file, level + 1);
        }
    }
}
