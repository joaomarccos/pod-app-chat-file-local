package br.edu.ifpb.pod.app.chat.file.temp;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class DriveCommandLine {

    private String CLIENT_ID = "31931409646-f5f54022vuehcrljt0vp18k1lrn5d6bc.apps.googleusercontent.com";
    private String CLIENT_SECRET = "fRPeMOzFRRjhtHFgWGPOHiQy";
    private String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    private Drive service;

    public DriveCommandLine() throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
                .setAccessType("online")
                .setApprovalPrompt("force").build();

        String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
        String code = JOptionPane.showInputDialog(null,"Please open the following URL in your browser then type the authorization code:", url);                        

        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
        GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);

        //Create a new authorized API client
        service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
    }

    public void uploadFile(java.io.File fileContent, File body) throws IOException {        
        
        FileContent mediaContent = new FileContent("text/plain", fileContent);
        File file = service.files().insert(body, mediaContent).execute();
        JOptionPane.showInputDialog(null,null, file.getId());
    }
    
    /*public static void main(String[] args) throws IOException {
        new DriveCommandLine().uploadFile(null);
    }*/
}
