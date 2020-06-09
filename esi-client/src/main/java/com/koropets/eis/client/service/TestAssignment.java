package com.koropets.eis.client.service;

import com.google.common.collect.ImmutableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class TestAssignment {

    private static final String SPLITTER = " ";
    private static final List<String> JUNK_SYMBOLS = Arrays.asList("?", "!", ",");

    public static void main(String[] args) {

    }

    public static int getCountForWord(File file, String word) {
        int count = 0;
        allWordsAfterFiltering()


        try(List<String> allLines = Files.readAllLines(file.toPath())) {
            for (String line: allLines) {
                List<String> words = Arrays.asList(line.split(SPLITTER));
                for (String w: words ) {
                    if (w.contains(word)) {
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return count;
    }

    private static List<String> allWordsAfterFiltering(File file) {

    }
}
