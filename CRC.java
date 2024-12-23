public class CRC {
    private static final int CRC_POLYNOMIAL = 0xEDB88320;
    private static final int CRC_MASK = 0xFFFFFFFF;
    private int[] crcTable = new int[256];

    public CRC() {
        generateCRCTable();
    }

    private void generateCRCTable() {
        for (int i = 0; i < 256; i++) {
            int crc = i;
            for (int j = 8; j > 0; j--) {
                if ((crc & 1) == 1) {
                    crc = (crc >>> 1) ^ CRC_POLYNOMIAL;
                } else {
                    crc = crc >>> 1;
                }
            }
            crcTable[i] = crc;
        }
    }

    public int calculateCRC(byte[] data) {
        int crc = CRC_MASK;
        for (byte b : data) {
            int tableIndex = ((crc & 0xFF) ^ (b & 0xFF)) & 0xFF;
            crc = (crc >>> 8) ^ crcTable[tableIndex];
        }
        return crc ^ CRC_MASK;
    }

    public int calculateCRC(String input) {
        return calculateCRC(input.getBytes());
    }

    public int calculateCRCForIntArray(int[] data) {
        int crc = CRC_MASK;
        for (int value : data) {
            byte[] byteArray = intToByteArray(value);
            for (byte b : byteArray) {
                int tableIndex = ((crc & 0xFF) ^ (b & 0xFF)) & 0xFF;
                crc = (crc >>> 8) ^ crcTable[tableIndex];
            }
        }
        return crc ^ CRC_MASK;
    }

    public static String bytesToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] intToByteArray(int value) {
        return new byte[] {
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value
        };
    }

    public boolean validateCRC(String input, int expectedChecksum) {
        int actualChecksum = calculateCRC(input);
        return actualChecksum == expectedChecksum;
    }

    public boolean validateCRC(byte[] input, int expectedChecksum) {
        int actualChecksum = calculateCRC(input);
        return actualChecksum == expectedChecksum;
    }

    public void demonstrateCRC() {
        String sampleString = "Hello, CRC!";
        int checksum1 = calculateCRC(sampleString);
        System.out.println("CRC32 checksum for string '" + sampleString + "' is: " + Integer.toHexString(checksum1));

        byte[] sampleBytes = sampleString.getBytes();
        int checksum2 = calculateCRC(sampleBytes);
        System.out.println("CRC32 checksum for byte array is: " + Integer.toHexString(checksum2));

        int[] sampleIntArray = new int[] {1, 2, 3, 4, 5};
        int checksum3 = calculateCRCForIntArray(sampleIntArray);
        System.out.println("CRC32 checksum for int array is: " + Integer.toHexString(checksum3));
    }

    public static void main(String[] args) {
        CRC crc = new CRC();
        crc.demonstrateCRC();

        String testString = "Hello, CRC!";
        int expectedChecksum = 0x1C291CA3;
        boolean isValid = crc.validateCRC(testString, expectedChecksum);
        System.out.println("CRC validation for string: " + (isValid ? "Valid" : "Invalid"));

        byte[] testBytes = testString.getBytes();
        isValid = crc.validateCRC(testBytes, expectedChecksum);
        System.out.println("CRC validation for byte array: " + (isValid ? "Valid" : "Invalid"));
    }
}
