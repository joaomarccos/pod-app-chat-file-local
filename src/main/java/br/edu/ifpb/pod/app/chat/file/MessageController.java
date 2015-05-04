package br.edu.ifpb.pod.app.chat.file;

import br.edu.ifpb.pod.app.chat.chat.GUI.Writter;
import br.edu.ifpb.pod.app.chat.file.entitys.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author João Marcos F <joaomarccos.ads@gmail.com>
 */
public class MessageController implements Writter{
   
    private static final Path PATH = Paths.get("/home/joaomarcos/data/mensagem.txt");
    private static final Path PATH_C = Paths.get("/home/joaomarcos/data/concurrence_msg.txt");
    private ArrayList<Message> msgs;

    public MessageController() throws IOException {
        if (!Files.exists(PATH)) {
            Files.createFile(PATH);
        }                
    }   

    public void sendMessage(Message msg) throws IOException {                
        
        msgs = loadMessages();
        msgs.add(msg);
        
//        testes com thread
//        FileWriter fw = new FileWriter(this);
//        new Thread(fw).start();
        
        while(!writeInFile());
        
    }

    @Override
    public boolean writeInFile() throws IOException {
        
        if (!canWrite())
            return false;

        ArrayList<Message> storedMsgs = loadMessages();
        for (Message msg : this.msgs) {
            if(!storedMsgs.contains(msg))
                storedMsgs.add(msg);
        }
        
        StringBuilder sbMsg = new StringBuilder();
        for (Message message : storedMsgs) {
            sbMsg.append(message.toString()).append("\n");
        }
        Files.write(PATH, sbMsg.toString().getBytes());
        
        return true;
    }

    private ArrayList<Message> loadMessages() throws NumberFormatException, IOException {

        ArrayList<Message> msgs = new ArrayList<>();

        try (BufferedReader read = Files.newBufferedReader(PATH)) {
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

        try (BufferedReader read = Files.newBufferedReader(PATH_C)) {
            String line = read.readLine();
            if (line!=null && line.equals("-closed-")) {
                return true;
            }
        }
        return false;
    }

    
    public  ArrayList<Message> listMessages() throws NumberFormatException, IOException{
        return loadMessages();
    }
}
