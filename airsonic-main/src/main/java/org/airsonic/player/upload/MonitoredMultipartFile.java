package org.airsonic.player.upload;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MonitoredMultipartFile implements MultipartFile {


    private MultipartFile file;
    private UploadListener listener;

    public MonitoredMultipartFile(MultipartFile file, UploadListener listener) {
        this.file = file;
        this.listener = listener;

        if (file.getOriginalFilename() != null) {
            listener.start(FilenameUtils.getName(file.getOriginalFilename()));
        }

    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return file.getOriginalFilename();
    }

    @Override
    public String getContentType() {
        return file.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return file.isEmpty();
    }

    @Override
    public long getSize() {
        return file.getSize();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return file.getBytes();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return file.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (
            FileOutputStream fos = new FileOutputStream(dest);
            MonitoredOutputStream monitoredOutputStream = new MonitoredOutputStream(fos, listener)) {
            monitoredOutputStream.write(file.getBytes());
        }
    }
}
