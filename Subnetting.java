import java.util.*;

public class Subnetting {

    public static String toBinaryString(String ip) {
        StringBuilder binary = new StringBuilder();
        String[] octets = ip.split("\\.");
        for (String octet : octets) {
            String binaryOctet = Integer.toBinaryString(Integer.parseInt(octet));
            while (binaryOctet.length() < 8) {
                binaryOctet = "0" + binaryOctet;
            }
            binary.append(binaryOctet);
        }
        return binary.toString();
    }

    public static String calculateNetworkAddress(String ip, String subnetMask) {
        String binaryIP = toBinaryString(ip);
        String binarySubnetMask = toBinaryString(subnetMask);
        StringBuilder networkAddressBinary = new StringBuilder();
        for (int i = 0; i < binaryIP.length(); i++) {
            if (binaryIP.charAt(i) == binarySubnetMask.charAt(i)) {
                networkAddressBinary.append(binaryIP.charAt(i));
            } else {
                networkAddressBinary.append("0");
            }
        }
        return binaryToDecimal(networkAddressBinary.toString());
    }

    public static String calculateBroadcastAddress(String ip, String subnetMask) {
        String binaryIP = toBinaryString(ip);
        String binarySubnetMask = toBinaryString(subnetMask);
        StringBuilder broadcastAddressBinary = new StringBuilder();
        for (int i = 0; i < binaryIP.length(); i++) {
            if (binarySubnetMask.charAt(i) == '1') {
                broadcastAddressBinary.append(binaryIP.charAt(i));
            } else {
                broadcastAddressBinary.append('1');
            }
        }
        return binaryToDecimal(broadcastAddressBinary.toString());
    }

    public static String binaryToDecimal(String binary) {
        StringBuilder decimal = new StringBuilder();
        for (int i = 0; i < binary.length(); i += 8) {
            String octet = binary.substring(i, i + 8);
            int decimalValue = Integer.parseInt(octet, 2);
            decimal.append(decimalValue).append(".");
        }
        return decimal.toString().substring(0, decimal.length() - 1);
    }

    public static int calculateNumberOfHosts(String subnetMask) {
        String binarySubnetMask = toBinaryString(subnetMask);
        int count = 0;
        for (char c : binarySubnetMask.toCharArray()) {
            if (c == '0') {
                count++;
            }
        }
        return (int) Math.pow(2, count) - 2;
    }

    public static void main(String[] args) {
        String ip = "192.168.1.1";
        String subnetMask = "255.255.255.0";
        
        String networkAddress = calculateNetworkAddress(ip, subnetMask);
        String broadcastAddress = calculateBroadcastAddress(ip, subnetMask);
        int numberOfHosts = calculateNumberOfHosts(subnetMask);
        
        System.out.println("IP Address: " + ip);
        System.out.println("Subnet Mask: " + subnetMask);
        System.out.println("Network Address: " + networkAddress);
        System.out.println("Broadcast Address: " + broadcastAddress);
        System.out.println("Number of Hosts: " + numberOfHosts);
    }
}
