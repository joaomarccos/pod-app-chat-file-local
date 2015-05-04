package br.edu.ifpb.pod.app.chat.file;

import br.edu.ifpb.pod.app.chat.chat.GUI.Writter;
import br.edu.ifpb.pod.app.chat.file.entitys.User;
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
public class LoginController implements Writter {

    private static final Path PATH = Paths.get("/home/joaomarcos/data/login.txt");
    private static final Path PATH_C = Paths.get("/home/joaomarcos/data/concurrence_login.txt");
    private ArrayList<User> loggedUsers;

    public LoginController() throws IOException {
        if (!Files.exists(PATH)) {
            Files.createFile(PATH);
        }

        if (!Files.exists(PATH_C)) {
            Files.createFile(PATH_C);
            Files.write(PATH_C, "-closed-".getBytes());
        }

        loggedUsers = new ArrayList<>();

    }

    @SuppressWarnings("empty-statement")
    public boolean login(User user) throws IOException {

        loggedUsers = loadUsers();

        if (!loggedUsers.contains(user)) {
            loggedUsers.add(user);

//            Testes com Thread
//            FileWriter fw = new FileWriter(this);
//            new Thread(fw).start();

            while(!writeInFile());
            
            return true;
        }

        return false;
    }

    @Override
    public boolean writeInFile() throws IOException {               
        
        if(!canWrite())
            return false;                
        
        ArrayList<User> storedUsers = loadUsers();
        Files.write(PATH_C, "-openned-".getBytes());
        
        for (User user : loggedUsers) {
            if (!storedUsers.contains(user)) {
                storedUsers.add(user);
            }
        }

        StringBuilder sbUser = new StringBuilder();

        for (User newUser : storedUsers) {
            sbUser.append(newUser.getName()).append("\n");
        }

        Files.write(PATH, sbUser.toString().getBytes());
        Files.write(PATH_C, "-closed-".getBytes());
        return true;

    }

    private  ArrayList<User> loadUsers() throws IOException {        
        ArrayList<User> users = new ArrayList<>();
        
        try (BufferedReader read = Files.newBufferedReader(PATH)) {
            String line = null;
            while ((line = read.readLine()) != null) {
                users.add(new User(line));
            }
        }

        return users;
    }

    public void logout(User user) throws IOException {

        loggedUsers = loadUsers();
        loggedUsers.remove(user);

//        FileWriter fw = new FileWriter(this);
//        new Thread(fw).start();
        
        while(!writeInFile());        
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

    public ArrayList<User> listUsers() throws IOException {
        return loadUsers();
    }

}
