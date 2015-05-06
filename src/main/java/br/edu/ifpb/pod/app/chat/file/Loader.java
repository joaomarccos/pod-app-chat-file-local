package br.edu.ifpb.pod.app.chat.file;

import br.edu.ifpb.pod.app.chat.file.entitys.Message;
import br.edu.ifpb.pod.app.chat.file.entitys.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Marcos F <joaomarccos.ads@gmail.com>
 */
public class Loader {

    public static void main(String[] args) throws IOException {  
        
        LoginController log = new LoginController();
        log.login(new User("João"));
        System.out.println(log.listUsers());
        log.logout(new User("João"));
        System.out.println(log.listUsers());
               
    }
}
