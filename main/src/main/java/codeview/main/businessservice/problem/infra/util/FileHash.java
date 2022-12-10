package codeview.main.businessservice.problem.infra.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileHash {

    public static String makeFileHashSha256(String filename) throws IOException, NoSuchAlgorithmException {

        String sha = "";
        int buff = 16384;

        RandomAccessFile file = new RandomAccessFile(filename, "r");
        MessageDigest hashSum = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[buff];
        byte[] partialHash = null;
        long read = 0;

        //해시 계산
        long offset = file.length();
        int unitSize;
        while (read < offset) {
            unitSize = (int) (((offset - read) >= buff) ? buff : (offset - read));
            file.read(buffer, 0, unitSize);
            hashSum.update(buffer, 0, unitSize);
            read += unitSize;
        }

        file.close();

        partialHash = new byte[hashSum.getDigestLength()];
        partialHash = hashSum.digest();

        return hexEncode(partialHash);

    }

    private static String hexEncode(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
