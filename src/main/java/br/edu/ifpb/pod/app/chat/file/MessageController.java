package br.edu.ifpb.pod.app.chat.file;

import br.edu.ifpb.pod.app.chat.file.entitys.Message;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;

/**
 *
 * @author João Marcos F <joaomarccos.ads@gmail.com>
 */
public class MessageController {

    private static Path path;
    private static Path path_c;

    public MessageController() throws IOException {
        PropertiesLoader pl = new PropertiesLoader();
        path = pl.getMsgfile();
        path_c = pl.getMsgCurFile();

        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        if (!Files.exists(path_c)) {
            Files.createFile(path_c);
            closeFile();
        }
    }

    @SuppressWarnings("empty-statement")
    public void sendMessage(Message msg) throws IOException {

        while (!writeInFile(msg));

    }

    public boolean writeInFile(Message msg) throws IOException {
        
        if (!canWrite()) {
            return false;
        }

        openFile();

        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, CREATE, APPEND))) {
            byte[] bytes = (msg.toString() + "\n").getBytes();
            out.write(bytes, 0, bytes.length);
        }

        closeFile();
        return true;
    }

    public void closeFile() throws IOException {
        Files.write(path_c, "-closed-".getBytes());
    }

    public void openFile() throws IOException {
        Files.write(path_c, "-openned-".getBytes());
    }

    private ArrayList<Message> loadMessages() throws NumberFormatException, IOException {

        ArrayList<Message> msgs = new ArrayList<>();

        try (BufferedReader read = Files.newBufferedReader(path)) {
            String line;
            String[] data;
            while ((line = read.readLine()) != null) {
                data = line.split(":");
                msgs.add(new Message(Long.parseLong(data[0]), data[1], data[2]));
            }

            return msgs;
        }
    }

    public boolean canWrite() throws IOException {

        try (BufferedReader read = Files.newBufferedReader(path_c)) {
            String line = read.readLine();

            if (line != null && line.equals("-closed-")) {
                return true;
            }
            
            return false;
        }
    }

    public ArrayList<Message> listMessages() throws NumberFormatException, IOException {
        return loadMessages();
    }
}
