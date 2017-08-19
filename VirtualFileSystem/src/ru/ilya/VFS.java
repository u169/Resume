package ru.ilya;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public interface VFS {

    boolean isExist(String path);

    boolean isDirectory(String path);

    String getAbsolutePath(String file);

    byte[] getBytes(String file);

    String getUTF8Text(String file) throws UnsupportedEncodingException;

    Iterator<String> getIterator(String startDir);
}
