package com.job.board.notification.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPayload implements Serializable {

    private String receiverUsername;
    private String message;
    private String link;

    @Override
    public String toString() {
        return "NotificationPayload{" +
                "receiverUsername='" + receiverUsername + '\'' +
                ", message='" + message + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}

