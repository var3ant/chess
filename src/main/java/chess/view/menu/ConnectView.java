package chess.view.menu;

import chess.Main;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConnectView extends JPanel {

    public ConnectView() {
        add(new ChangeMode(this));
    }

    public void client(String ip, String port) {
        try {
            Main.frame.remove(this);
            Main.frame.add(Main.view);
            Main.onlineClient(ip, Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void server(String port) {
        try {
            Main.frame.remove(this);
            Main.frame.add(Main.view);
            Main.onlineServer(Integer.parseInt(port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void offline() {
        try {
            Main.frame.remove(this);
            Main.frame.add(Main.view);
            Main.offline();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
