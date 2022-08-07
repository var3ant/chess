package chess.view.menu;

import javax.swing.*;
import java.awt.*;

public class ChangeMode extends JPanel {
    public ChangeMode(ConnectView connectView) {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JButton client = new JButton("client");
        JButton server = new JButton("server");
        JButton offline = new JButton("offline");


        JTextField ipField = new JTextField();
        ipField.setColumns(10);
        JTextField portField = new JTextField();
        portField.setColumns(10);
        c.gridy = 0;
        c.gridx = 0;
        add(new JLabel("ip:"), c);

        c.gridx = 1;
        add(ipField, c);


        c.gridy = 1;

        c.gridx = 0;
        add(new JLabel("port:"), c);

        c.gridx = 1;
        add(portField, c);

        client.addActionListener(e -> {
            connectView.client(ipField.getText(), portField.getText());
        });

        server.addActionListener(e -> {
            connectView.server(portField.getText());
        });

        offline.addActionListener(e -> {
            connectView.offline();
        });

        c.gridy = 2;

        c.gridx = 0;
        add(client, c);

        c.gridx = 1;
        add(server, c);

        c.gridx = 2;
        add(offline, c);
    }
}
