package com.ssafy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encryptor {
  public static String getSalt() {
    SecureRandom sr = new SecureRandom();
    byte[] salt = new byte[20];

    sr.nextBytes(salt);

    StringBuffer sb = new StringBuffer();
    for (byte b : salt) {
      sb.append(String.format("%02x", b));
    }

    return sb.toString();
  }

  public static String getEncryptedText(String text, String salt) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      md.update((text + salt).getBytes());
      byte[] encrpBytes = md.digest();

      StringBuffer sb = new StringBuffer();
      for (byte b : encrpBytes) {
        sb.append(String.format("%02x", b));
      }

      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String getEncryptedTextWithNoSalt(String text) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD2");

      md.update(text.getBytes());
      byte[] encrpBytes = md.digest();

      StringBuffer sb = new StringBuffer();
      for (byte b : encrpBytes) {
        sb.append(String.format("%02x", b));
      }

      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }
}
