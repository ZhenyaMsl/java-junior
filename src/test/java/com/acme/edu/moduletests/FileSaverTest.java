package com.acme.edu.moduletests;

import com.acme.edu.saver.FileSaver;
import com.acme.edu.saver.Saver;
import com.acme.edu.saver.SavingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class FileSaverTest {
    private Saver saver;
    private String logFileName = "testLog.txt";

    @Before
    public void setUpSystemOut() {
        saver = new FileSaver(logFileName);
    }

    @After
    public void tearDownLogFile() {
        (new File(logFileName)).delete();
    }

    @Test @Ignore
    public void shouldSaveStringToFile() throws SavingException, IOException {
        String message = "test string";

        saver.save(message);

        Files.lines(Paths.get(logFileName))
            .forEach(s -> assertEquals(s, message));
    }

    @Test
    public void shouldSaveTwice() throws SavingException, IOException {
        String message = "test string1";
        String unionMessage = "test string1test string2";
        try (PrintWriter printWriter =
                new PrintWriter(
                        new OutputStreamWriter(
                                new BufferedOutputStream(
                                        new FileOutputStream(
                                                logFileName,
                                                true))))) {
            printWriter.println("test string2");
            saver.save(message);
        } catch (IOException e) {
            throw new SavingException("Error while working with file: " + e.getMessage());
        }

        Files.lines(Paths.get(logFileName))
                .forEach(s -> assertThat(unionMessage).contains(s));

        System.out.println(Runtime.getRuntime().freeMemory());

        System.out.println((new File(".")).getFreeSpace());
    }
}
