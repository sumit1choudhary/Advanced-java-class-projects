package com.shopsphere.util;
import java.nio.charset.StandardCharsets; import java.security.MessageDigest;
public class PasswordUtil {
 public static String hash(String value) {
  try { byte[] b=MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
   StringBuilder s=new StringBuilder(); for(byte x:b)s.append(String.format("%02x",x)); return s.toString();
  } catch(Exception e){throw new RuntimeException(e);}
 }
}
