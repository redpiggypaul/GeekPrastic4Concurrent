package javaCoreTech.copyFile_12;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

public class copyFileByStream {

    public static void copyFileByStream(File source, File dest) throws
            IOException {
        try (InputStream is = new FileInputStream(source);
             OutputStream os = new FileOutputStream(dest);){
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }


    public static void copyFileByChannel(File source, File dest) throws
            IOException {
        try (FileChannel sourceChannel = new FileInputStream(source)
                .getChannel();
             FileChannel targetChannel = new FileOutputStream(dest).getChannel
                     ();){
            for (long count = sourceChannel.size() ;count>0 ;) {
                long transferred = sourceChannel.transferTo(
                        sourceChannel.position(), count, targetChannel);            sourceChannel.position(sourceChannel.position() + transferred);
                count -= transferred;
            }
        }
    }

    /*
    public static Path copy(Path source, Path target, CopyOption... options)
            throws IOException{
        Path result = new Path();
        return result;
    }*/

    public static Path copy(Path source, Path target, CopyOption... options)
            throws IOException
    {
        FileSystemProvider provider = provider(source);
        if (provider(target) == provider) {
            // same provider
            provider.copy(source, target, options);// 这是本文分析的路径
        } else {
            // different providers
            CopyMoveHelper.copyToForeignTarget(source, target, options);
        }
        return target;
    }




    public static long copy(InputStream in, Path target, CopyOption... options)
            throws IOException{};

    public static long copy(Path source, OutputStream out)
            throws IOException{};





}
