package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RecursionUtils {
    public static long directorySizeRecursive(Path path) throws IOException {
        if (!Files.exists(path)) return 0L;
        return Files.walk(path)
                .filter(Files::isRegularFile)
                .mapToLong(p -> {
                    try { return Files.size(p); }
                    catch (IOException e) { return 0L; }
                }).sum();
    }
}
