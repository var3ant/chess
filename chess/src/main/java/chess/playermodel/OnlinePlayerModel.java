package chess.playermodel;

import chess.dto.MoveDto;
import chess.figure.FigureColor;
import chess.move.Move;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class OnlinePlayerModel extends PlayerModel {
    private final Socket socket;
    private final BlockingQueue<MoveDto> messagesToSend = new LinkedBlockingQueue<>();
    private Thread sender;

    public OnlinePlayerModel(FigureColor myColor, String ip, int port) throws IOException {
        super(myColor);
        socket = new Socket(ip, port);//TODO: не падать если сервер еще не запущен.
        start();
    }

    public OnlinePlayerModel(FigureColor myColor, int port) throws IOException {
        super(myColor);
        socket = new ServerSocket(port).accept();
        start();
    }

    private void start() {
        new Thread(() -> {
            try {
                ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
                while (!socket.isClosed()) {
                    MoveDto moveDto = (MoveDto) stream.readObject();
                    System.out.println("move was read: " + moveDto);
                    model.doMove(this, moveDto.figureX, moveDto.figureY, moveDto.move);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
        sender = new Thread(() -> {
            try {
                ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
                while (!socket.isClosed()) {
                    try {
                        MoveDto moveDto = messagesToSend.poll();
                        if (moveDto == null) {
                            continue;
                        }
                        System.out.println("move to send: " + moveDto);
                        stream.writeObject(moveDto);
                        System.out.println("move was send");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sender.start();
    }

    @Override
    public void notificationMoveWasHappened(int figureX, int figureY, Move move) {
        super.notificationMoveWasHappened(figureX, figureY, move);
        messagesToSend.add(new MoveDto(figureX, figureY, move));
    }
}
