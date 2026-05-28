package it.dosti.justit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {

    @SuppressWarnings({"java:S5042"})
    public void extractZip(Path target, String resource) {

        try (InputStream is = getClass().getResourceAsStream(resource);
             ZipInputStream zis = new ZipInputStream(is)) {

            Path safeTargetDir = target.toAbsolutePath().normalize();

            Files.createDirectories(safeTargetDir);

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                Path targetPath = safeTargetDir.resolve(entry.getName()).normalize();

                if(!targetPath.startsWith(safeTargetDir)) {
                    throw new SecurityException("Invalid zip entry: " + entry.getName());
                }

                if (entry.isDirectory()) {
                    Files.createDirectories(targetPath);
                } else {
                    Files.createDirectories(targetPath.getParent());

                    Files.copy(zis, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }

                zis.closeEntry();
            }

        } catch (IOException | SecurityException e) {
            JustItLogger.getInstance().error(e.getMessage(), e);
        }
    }
}