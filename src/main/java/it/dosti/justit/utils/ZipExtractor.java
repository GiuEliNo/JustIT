package it.dosti.justit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {

    @SuppressWarnings("java:S5042")
    public void extractZip(Path target, String resource) {

        try (InputStream is = getClass().getResourceAsStream(resource);
             ZipInputStream zis = new ZipInputStream(is)) {
            Files.createDirectories(target);

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                Path targetPath = target.resolve(entry.getName()).normalize();

                if (entry.isDirectory()) {
                    Files.createDirectories(targetPath);
                } else {
                    Files.createDirectories(targetPath.getParent());

                    Files.copy(zis, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }

                zis.closeEntry();
            }

        } catch (IOException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
}