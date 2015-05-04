package br.edu.ifpb.pod.app.chat.file;

import br.edu.ifpb.pod.app.chat.file.entitys.Message;
import br.edu.ifpb.pod.app.chat.file.entitys.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Marcos F <joaomarccos.ads@gmail.com>
 */
public class Loader {

    public static void main(String[] args) {

        try {
            MessageController mc = new MessageController();
            for (int i = 0; i < 10; i++) {
                mc.sendMessage(new Message("joão"+i, "Oi"+i));
            }
            

        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
