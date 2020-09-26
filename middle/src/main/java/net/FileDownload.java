package net;

import java.io.*;
import java.net.URL;

public class FileDownload {
    public void fileDownload(String url, String maxSpeed, String directory) {
        int speedKb = Integer.parseInt(maxSpeed) * 1024;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(directory)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int timePause;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Thread.sleep(1000);
                if (bytesRead > speedKb) {
                    timePause = (bytesRead - speedKb) / speedKb;
                    Thread.sleep(timePause);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileDownload fileDownload = new FileDownload();
        fileDownload.fileDownload(args[0], args[1], "test.txt");
    }
}
