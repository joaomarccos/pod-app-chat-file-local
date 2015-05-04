package br.edu.ifpb.pod.app.chat.file;

import br.edu.ifpb.pod.app.chat.chat.GUI.Writter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author João Marcos F <joaomarccos.ads@gmail.com>
 */
public class FileWriter implements Runnable{
        
    private Writter mannager;
    
    public FileWriter(Writter writter){
        this.mannager = writter;
    }
       
    @Override
    public void run() {
        try {
            while(!mannager.writeInFile());
        } catch (IOException ex) {
            Logger.getLogger(FileWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
