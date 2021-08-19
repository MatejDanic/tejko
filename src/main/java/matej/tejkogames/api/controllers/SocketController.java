package matej.tejkogames.api.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.ExceptionLogService;
import matej.tejkogames.api.services.SocketService;
import matej.tejkogames.api.services.UserService;
import matej.tejkogames.utils.JwtUtil;
import matej.tejkogames.models.general.enums.MessageType;
import matej.tejkogames.models.general.payload.requests.MessageRequest;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@RestController
public class SocketController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @Autowired
    SocketService socketService;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ExceptionLogService exceptionLogService;

    @MessageMapping("/greeting")
    @SendTo("/topic/greetings")
    public MessageResponse greeting(MessageRequest message, Principal principal) throws Exception {
        try {
            if (message.getSubject().equals("Hello") && message.getToken() != null
                    && jwtUtil.getUsernameFromJwtToken(message.getToken()).equals(message.getSender())) {
                socketService.addUUID(message.getSender(), principal.getName());
            }
            return new MessageResponse(message.getSubject() + ", " + message.getSender() + "!", MessageType.GREETING);
        } catch (Exception exception) {
            exceptionLogService.save(exception);
            return new MessageResponse("Greeting", MessageType.ERROR, exception.getMessage());
        }

    }

    @MessageMapping("/text")
    @SendTo("/topic/everyone")
    public MessageResponse message(MessageRequest message) throws Exception {
        try {
            if (message.getToken() != null
                    && jwtUtil.getUsernameFromJwtToken(message.getToken()).equals(message.getSender())) {
                return new MessageResponse(message.getSubject(), MessageType.CHAT, message.getBody(),
                        message.getSender());
            }
        } catch (Exception exception) {
            exceptionLogService.save(exception);
            return new MessageResponse("Message", MessageType.ERROR, exception.getMessage());
        }
        return null;

    }

    @MessageMapping("/challenge")
    @SendToUser("/topic/challenge")
    public void sendSpecific(@Payload MessageRequest message, @Header("simpSessionId") String sessionId)
            throws Exception {
        try {
            if (message.getToken() != null
                    && jwtUtil.getUsernameFromJwtToken(message.getToken()).equals(message.getSender())) {
                MessageResponse response = new MessageResponse(message.getSubject(), MessageType.CHALLENGE,
                        message.getBody(), message.getSender());
                simpMessagingTemplate.convertAndSendToUser(socketService.getUUIDFromUsername(message.getReceiver()),
                        "/topic/challenge", response);
            }
        } catch (Exception exception) {
            exceptionLogService.save(exception);
        }
    }
}