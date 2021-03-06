package br.edu.ifpb.pod.app.chat.file;

import br.edu.ifpb.pod.app.chat.file.entitys.User;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Marcos F <joaomarccos.ads@gmail.com>
 */
public class LoginController {

    private static Path path;
    private static Path path_c;
    private long lastFileLenght;
    private ArrayList<ListenerChangeFile> listenners;

    public LoginController() throws IOException {
        PropertiesLoader pl = new PropertiesLoader();
        path = pl.getLoginfile();
        path_c = pl.getLoginCurFile();
        this.listenners = new ArrayList<>();
        
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        if (!Files.exists(path_c)) {
            Files.createFile(path_c);
            closeFile();
        }
        
        this.lastFileLenght = getFileLength();

    }

    @SuppressWarnings("empty-statement")
    public boolean login(User user) throws IOException {

        ArrayList<User> loggedUsers = loadUsers();

        if (!loggedUsers.contains(user)) {
            while (!writeInFile(user));
            return true;
        }
        return false;

    }

    private boolean writeInFile(User user) throws IOException {

        if (!canWrite()) {
            return false;
        }

        openFile();

        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, CREATE, APPEND))) {
            byte[] bytes = (user.getName() + "\n").getBytes();
            out.write(bytes, 0, bytes.length);
        }

        closeFile();

        return true;

    }

    private void closeFile() throws IOException {
        Files.write(path_c, "-closed-".getBytes());
    }

    private void openFile() throws IOException {
        Files.write(path_c, "-openned-".getBytes());
    }

    private ArrayList<User> loadUsers() throws IOException {
        ArrayList<User> users = new ArrayList<>();

        try (BufferedReader read = Files.newBufferedReader(path)) {
            String line = null;
            while ((line = read.readLine()) != null) {
                users.add(new User(line));
            }
        }

        return users;
    }

    @SuppressWarnings("empty-statement")
    public void logout(User user) throws IOException {

        ArrayList<User> loggedUsers = loadUsers();
        loggedUsers.remove(user);

        while (!canWrite());

        openFile();

        StringBuilder sb = new StringBuilder();
        for (User loggedUser : loggedUsers) {
            sb.append(loggedUser.getName()).append("\n");
        }
        Files.write(path, sb.toString().getBytes());

        closeFile();
        
    }

    private boolean canWrite() throws IOException {

        try (BufferedReader read = Files.newBufferedReader(path_c)) {
            String line = read.readLine();
            if (line != null && line.equals("-closed-")) {
                return true;
            }
        }
        return false;
    }
    
    public void addListenner(ListenerChangeFile listenning) {
        this.listenners.add(listenning);
    }
    
    private long getFileLength() throws IOException {
        return Files.size(path);
    }

    public ArrayList<User> listUsers() throws IOException {
        return loadUsers();
    }
    
    public void checkUsers() {        
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    try {                       
                        long fileLen = getFileLength();
                        if (lastFileLenght != fileLen) {                                                        
                            for (ListenerChangeFile listener : listenners) {
                                listener.notifyChangeOnLogin();
                                lastFileLenght = fileLen;
                            }
                            System.out.println("Eh para modificar");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
                
        new Thread(runnable).start();
    }

}
