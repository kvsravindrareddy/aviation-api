package com.ravindra.util;

import com.ravindra.common.ApiConstants;
import com.ravindra.config.SFTPConfig;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.FileAttributes;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;

@Component
public class SFTPUtil {

    @Autowired
    private SFTPConfig sftpConfig;

    public SSHClient connectSFTP()
    {
        SSHClient sshClient = null;
        try {
            sshClient = new SSHClient();
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());
            //scpClient.getFileTransfer.setPreserveAttributes(false)
            sshClient.connect(sftpConfig.getHost());
            sshClient.authPassword(sftpConfig.getUserName(),sftpConfig.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  sshClient;
    }

    public void copyFileToSftp(File... files)
    {
        SSHClient sshClient = connectSFTP();
        try {
            try(SFTPClient sftpClient = sshClient.newSFTPClient())
            {
                for(File file : files) {
                    sftpClient.put(new FileSystemFile(file), sftpConfig.getServerPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                sshClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[ApiConstants.DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }
}
