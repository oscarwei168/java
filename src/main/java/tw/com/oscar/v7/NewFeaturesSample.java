package tw.com.oscar.v7;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author oscarwei
 * @since 2015/1/20
 */
public class NewFeaturesSample {

    private static class FirstException extends Exception {
    }

    private static class SecondException extends Exception {
    }

    public static void main(String[] args) {

        // Underscores in Numeric Literals
        long creditCardNumber = 1234_5678_9012_3456L;
        long socialSecurityNumber = 999_99_9999L;
        float pi = 3.14_15F;
        long hexBytes = 0xFF_EC_DE_5E;
        long hexWords = 0xCAFE_BABE;
        long maxLong = 0x7fff_ffff_ffff_ffffL;
        byte nybbles = 0b0010_0101;
        long bytes = 0b11010010_01101001_10010100_10010010;

    }

    private static void xxx(String outputFileName, String zipFileName) {
        Objects.requireNonNull(outputFileName);
        Objects.requireNonNull(zipFileName);
        java.nio.charset.Charset charset = java.nio.charset.Charset.forName("US-ASCII");
        java.nio.file.Path outputFilePath = java.nio.file.Paths.get(outputFileName);
        // try-with-resource statement demo
        try (
                java.util.zip.ZipFile zf = new java.util.zip.ZipFile(zipFileName);
                java.io.BufferedWriter writer = java.nio.file.Files.newBufferedWriter(outputFilePath, charset)
        ) {
            for (java.util.Enumeration entries = zf.entries(); entries.hasMoreElements(); ) {
                // Get the entry name and write it to the output file
                String newLine = System.getProperty("line.separator");
                String zipEntryName = ((java.util.zip.ZipEntry) entries.nextElement()).getName() + newLine;
                writer.write(zipEntryName, 0, zipEntryName.length());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            // Obtain Suppressed Exception demo
            Arrays.stream(ex.getSuppressed()).map(Throwable::getMessage).forEach(System
                    .out::println);
        }
    }

    private static void xxxx(String option) {
        Objects.requireNonNull(option); // new 'Objects' class
        // Strings in switch Statements demo
        switch (option) {
            case "abc":
                break;

            case "def":
                break;

            default:
                break;
        }
    }

    // Rethrowing Exceptions with More Inclusive Type Checking
    // Prior to Java SE 7 only can throws 'Exception'
    private static void rethrowException(String option) throws FirstException, SecondException {
        Objects.requireNonNull(option);
        try {
            if ("first".equalsIgnoreCase(option)) {
                throw new FirstException();
            } else if ("second".equals(option)) {
                throw new SecondException();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
