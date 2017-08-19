package ru.ilya;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class VFSImpl implements VFS{

    private String root;

    public VFSImpl(String root) {
        this.root = root;
    }

    private class FileIterator implements Iterator<String> {

        private Queue<File> files = new LinkedList<File>();

        public FileIterator(String path) {
            files.add(new File(root + path));
        }

        @Override
        public boolean hasNext() {
            return !files.isEmpty();
        }

        @Override
        public String next() {
            File file = files.peek();
            if(file.isDirectory()) {
                files.addAll(Arrays.asList(file.listFiles()));
            }
            return files.poll().getAbsolutePath();
        }
        public void remove() {
            files.remove();
        }
    }


    @Override
    public boolean isExist(String path) {
        File file = new File(root + path);
        return file.exists();
    }

    @Override
    public boolean isDirectory(String path) {
        File file = new File(root + path);
        return file.isDirectory();
    }

    @Override
    public String getAbsolutePath(String file) {
        return root+file;
    }

    @Override
    public byte[] getBytes(String path) {
        Path filePath = Paths.get(root + path);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            System.out.println("Can't read file: " + root + path);
        }
        return null;
    }

    @Override
    public String getUTF8Text(String file) {
        try {
            return new String(getBytes(file), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Wrong Encoding");
        }
        return null;
    }

    @Override
    public Iterator<String> getIterator(String startDir) {
        return new FileIterator(startDir);
    }
}
