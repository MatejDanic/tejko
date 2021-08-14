package matej.tejkogames.models.payload.responses;

import matej.tejkogames.models.enums.MessageType;

public class MessageResponse {

    private String subject;

    private String sender;

    private MessageType type;
    
    private Object body;

    public MessageResponse(String subject) {
        this.subject = subject;
        this.sender = "Server";
        this.type = MessageType.DEFAULT;
    }

    public MessageResponse(String subject, Object body) {
        this.subject = subject;
        this.sender = "Server";
        this.type = MessageType.DEFAULT;
        this.body = body;
    }

    public MessageResponse(String subject, MessageType type) {
        this.subject = subject;
        this.sender = "Server";
        this.type = type;
    }

    public MessageResponse(String subject, MessageType type, Object body) {
        this.subject = subject;
        this.sender = "Server";
        this.type = type;
        this.body = body;
    }

    public MessageResponse(String subject, MessageType type, Object body, String sender) {
        this.subject = subject;
        this.sender = sender;
        this.type = type;
        this.body = body;
    }

    // public MessageResponse(String subject, String username, MessageType type) {
    //     this.subject = subject;
    //     this.username = username;
    //     this.type = type;
    // }

    // public MessageResponse(String subject, String username, MessageType type, Object body) {
    //     this.subject = subject;
    //     this.username = username;
    //     this.type = type;
    //     this.body = body;
    // }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
    
}