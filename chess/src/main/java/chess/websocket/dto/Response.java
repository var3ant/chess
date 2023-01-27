package chess.websocket.dto;

public class Response <T> {
    public Long id;
    public T data;

    public Response() {

    }

    public Response(Long id, T data) {
        this.id = id;
        this.data = data;
    }
}
