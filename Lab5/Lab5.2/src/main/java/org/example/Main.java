import java.util.*;
import java.io.*;
import java.security.SecureRandom;

public class Main {
    static final int KEY_BITS = 20;
    static final int KEY_SPACE = 1 << KEY_BITS;
    static final int BLOCK_SIZE = 8;

    static byte[] encryptBlock(int key, byte[] block) {
        if (block.length != BLOCK_SIZE) throw new IllegalArgumentException("Block must be 8 bytes");
        byte[] out = new byte[BLOCK_SIZE];
        long state = key & 0xFFFFF;
        for (int i = 0; i < BLOCK_SIZE; i++) {
            state = (state * 1103515245L + 12345) & 0xFFFFFFFFL;
            out[i] = (byte)(block[i] ^ ((state >>> 16) & 0xFF));
        }
        return out;
    }

    static long bytesToLong(byte[] b) {
        long v = 0;
        for (int i = 0; i < BLOCK_SIZE; i++) v = (v << 8) | (b[i] & 0xFFL);
        return v;
    }

    static byte[] hexToBytes(String hex) {
        hex = hex.trim();
        if (hex.startsWith("0x") || hex.startsWith("0X")) hex = hex.substring(2);
        if (hex.length() != BLOCK_SIZE * 2) throw new IllegalArgumentException("Expected " + (BLOCK_SIZE*2) + " hex chars");
        byte[] b = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) b[i] = (byte) Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        return b;
    }

    static String bytesToHex(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length*2);
        for (byte x : b) sb.append(String.format("%02X", x & 0xFF));
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {

        SecureRandom rnd = new SecureRandom();
        int K11 = rnd.nextInt(KEY_SPACE);
        int K21 = rnd.nextInt(KEY_SPACE);
        byte[] plain1 = new byte[BLOCK_SIZE];
        rnd.nextBytes(plain1);

        byte[] c1 = encryptBlock(K11, plain1);
        byte[] cipher1 = encryptBlock(K21, c1);

        System.out.printf("K1=0x%05X K2=0x%05X%n", K11, K21);
        System.out.println("Входная строка: " + bytesToHex(plain1));
        System.out.println("Зашифрованная строка: " + bytesToHex(cipher1));



        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите входную строку (16 hex chars, 8 байт):");
        String phex = br.readLine();
        System.out.println("Введите зашифрованную строку (16 hex chars, 8 байт):");
        String chex = br.readLine();

        byte[] plain = hexToBytes(phex);
        byte[] cipher = hexToBytes(chex);

        long t0 = System.nanoTime();

        HashMap<Long, ArrayList<Integer>> map = new HashMap<>(KEY_SPACE);
        for (int k1 = 0; k1 < KEY_SPACE; k1++) {
            long mid = bytesToLong(encryptBlock(k1, plain));
            map.computeIfAbsent(mid, x -> new ArrayList<>()).add(k1);
        }

        ArrayList<int[]> solutions = new ArrayList<>();
        for (int k2 = 0; k2 < KEY_SPACE; k2++) {
            long mid2 = bytesToLong(encryptBlock(k2, cipher));
            ArrayList<Integer> lst = map.get(mid2);
            if (lst != null) for (int k1 : lst) solutions.add(new int[]{k1, k2});
        }

        long t1 = System.nanoTime();
        System.out.printf("Входная строка=%s%nЗашифрованная строка=%s%n", bytesToHex(plain), bytesToHex(cipher));
        System.out.printf("Найдено %d ключ(а) за %.3f s%n", solutions.size(), (t1 - t0) / 1e9);
        for (int[] p : solutions) System.out.printf("K1=0x%05X K2=0x%05X%n", p[0], p[1]);
    }
}
