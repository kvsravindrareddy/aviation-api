package com.ravindra.util;

import com.ravindra.config.WindowsConfig;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

@Component
public class WindowsSharedDriveUtil {

    @Autowired
    private WindowsConfig windowsConfig;

    public SmbFile smbHostConnect() {
        NtlmPasswordAuthentication auth =
                new NtlmPasswordAuthentication(windowsConfig.getUrl(), windowsConfig.getUserName(), windowsConfig.getPassword());
        SmbFile smbFile = null;
        try {
            smbFile = new SmbFile("/", auth);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return smbFile;
    }

    public void copyFileToWindosSharedPath(File sourcePath) {
        SmbFile smbFile = smbHostConnect();
        final SmbFileOutputStream smbFileOutputStream;
        try {
            smbFileOutputStream = new SmbFileOutputStream(
                    smbFile);
            final FileInputStream fileInputStream = new FileInputStream(sourcePath);

            final byte[] buf = new byte[16 * 1024 * 1024];
            int len;
            while ((len = fileInputStream.read(buf)) > 0) {
                smbFileOutputStream.write(buf, 0, len);
            }
            fileInputStream.close();
            smbFileOutputStream.close();
        } catch (SmbException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}