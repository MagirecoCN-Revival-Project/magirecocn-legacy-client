package com.squareup.tape;

import java.io.File;
import java.io.IOException;

/* loaded from: classes.dex */
public class FileException extends RuntimeException {
    private final File file;

    public FileException(String str, IOException iOException, File file) {
        super(str, iOException);
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }
}
