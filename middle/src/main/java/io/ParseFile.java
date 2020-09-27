package io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    private String getSomeContent(Predicate<Integer> predicate) {
        StringBuilder output = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            int data;
            do {
                data = i.read();
                if (predicate.test(data)) {
                    output.append((char) data);
                }
            }
            while (data > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getContent() {
        return getSomeContent(data -> data > 0);
    }

    public synchronized String getContentWithoutUnicode() {
        return getSomeContent(data -> data < 0x80);
    }

    public synchronized void saveContent(String content) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException("File to write does not exist");
        }
        try (OutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
