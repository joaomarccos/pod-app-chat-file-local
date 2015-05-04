package br.edu.ifpb.pod.app.chat.chat.GUI;

/**
 *
 * @author João Marcos F <joaomarccos.ads@gmail.com>
 */
public class Loader {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame main = new MainFrame();
                main.setVisible(true);
            }
        });

    }
}
