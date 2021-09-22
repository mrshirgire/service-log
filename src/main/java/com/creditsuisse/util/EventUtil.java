package com.creditsuisse.util;

import com.creditsuisse.model.Event;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import  com.creditsuisse.exception.FileNotFoundException;

@Component
public class EventUtil {



    public static void writeToFile(File file, String content) {

        try{
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();

            file.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
            throw new FileNotFoundException(Event.class, "File not found:"+file.getName());
        }

        try (FileChannel channel = FileChannel.open(Paths.get(file.getPath()), StandardOpenOption.WRITE,StandardOpenOption.APPEND)) {
                //channel.lock();
                channel.position(channel.size());
                channel.force(true);
                ByteBuffer buff = ByteBuffer.wrap(content.concat("\n").getBytes(StandardCharsets.UTF_8));
                channel.write(buff);

        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileNotFoundException(Event.class, "File not found:"+file.getName());
        } catch (IOException ioException) {
            throw new FileNotFoundException(Event.class, "File not found:"+file.getName());
        }
    }
}
