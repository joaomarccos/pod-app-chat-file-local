package br.edu.ifpb.pod.app.chat.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author João Marcos F <joaomarccos.ads@gmail.com>
 */
public class PropertiesLoader {

    private Path PATH;
    private Properties props;

    public PropertiesLoader() throws IOException {
        this.PATH = Paths.get("src/main/resources/config.txt");
        if (!Files.exists(PATH)) {
            Files.createFile(PATH);
        }
        
        loadProperties();
    }
    
    public Path getLoginfile() throws FileNotFoundException, IOException{        
        return Paths.get(props.getProperty("login-file"));
    }
    public Path getMsgfile() throws FileNotFoundException, IOException{        
        return Paths.get(props.getProperty("msg-file"));
    }
    public Path getLoginCurFile() throws FileNotFoundException, IOException{        
        return Paths.get(props.getProperty("login-c-file"));
    }
    public Path getMsgCurFile() throws FileNotFoundException, IOException{        
        return Paths.get(props.getProperty("msg-c-file"));
    }

    private void loadProperties() throws IOException {
        this.props = new Properties();
        try(InputStream input = Files.newInputStream(PATH)){            
            props.load(input);               
        }
    }
    
    

}
