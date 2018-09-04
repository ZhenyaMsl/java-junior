package com.acme.edu.saver;

import java.io.*;

public class FileSaver implements Saver {
    String logFileName = "log.txt";

    public FileSaver(String logFileName) {
        super();
        this.logFileName = logFileName;
    }

    @Override
    public void output(String message) throws SavingException {
        File file = new File(".", logFileName);
        try (PrintWriter printWriter =
                     new PrintWriter(
                             new OutputStreamWriter(
                                     new BufferedOutputStream(
                                             new FileOutputStream(
                                                     file,
                                                     true))))) {

            printWriter.println(message);

        } catch (IOException e) {
            throw new SavingException("Error while working with file: " + e.getMessage());
        }
    }

}
