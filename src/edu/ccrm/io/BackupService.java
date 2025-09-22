package edu.ccrm.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class BackupService {
    public Path backup(Path sourceDir) throws IOException {
        if (!Files.exists(sourceDir)) throw new IllegalArgumentException("Source directory does not exist: " + sourceDir);
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path dest = sourceDir.resolveSibling("backups").resolve("backup_" + ts);
        Files.createDirectories(dest);
        try (Stream<Path> stream = Files.walk(sourceDir)) {
            stream.forEach(src -> {
                try {
                    Path relative = sourceDir.relativize(src);
                    Path target = dest.resolve(relative);
                    if (Files.isDirectory(src)) {
                        Files.createDirectories(target);
                    } else {
                        Files.copy(src, target);
                    }
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        }
        return dest;
    }

    public long computeRecursiveSize(Path path) throws IOException {
        if (!Files.exists(path)) return 0L;
        try (Stream<Path> stream = Files.walk(path)) {
            return stream.filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try { return Files.size(p); }
                        catch (IOException e) { return 0L; }
                    }).sum();
        }
    }
}
