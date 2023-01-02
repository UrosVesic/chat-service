package rs.urosvesic.chatservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import rs.urosvesic.chatservice.dto.MessageResponse;

@FeignClient(name = "websocket-client", url = "${WEBSOCKET_SERVICE_SERVICE_HOST:http://localhost}:8084/api/websocket")
public interface WebSocketClient{

    @PostMapping("/message")
    @Async
    void notifyForMessage(@RequestBody MessageResponse messageResponse,
                  @RequestHeader(value = "Authorization") String authorizationHeader);


}
