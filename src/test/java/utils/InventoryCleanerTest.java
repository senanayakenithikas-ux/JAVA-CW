package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryCleanerTest {

    @TempDir
    Path tempDir;

    @Test
    @DisplayName("main method should parse and clean legacy inventory file correctly")
    void main() throws IOException {
        File dataDir = new File(".data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        File legacyFile = new File(".data/inventory_legacy.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(legacyFile))) {
            writer.println("P101|Brake Pad|Brembo|Rs. 50.00|10|brakes|Jan 01, 2026|brake.png");
            writer.println("P102,Spark Plug,NGK,15.00,20,ENGINE,02/01/2026,plug.png");
        }

        InventoryCleaner.main(new String[]{});

        File cleanFile = new File(".data/inventory_clean.txt");
        assertTrue(cleanFile.exists());

        List<String> lines = Files.readAllLines(cleanFile.toPath());
        assertFalse(lines.isEmpty());
        assertEquals("P101,Brake Pad,Brembo,50.0,10,Brakes,01-01-2026,brake.png,10", lines.get(0));
        assertEquals("P102,Spark Plug,NGK,15.0,20,Engine,02-01-2026,plug.png,10", lines.get(1));
    }
}