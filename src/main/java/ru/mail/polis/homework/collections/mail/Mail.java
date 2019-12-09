package ru.mail.polis.homework.collections.mail;

public abstract class Mail {
    private final String recipient;
    private final String sender;

    public Mail(String recipient, String sender) {
        this.recipient = recipient;
        this.sender = sender;
    }

    String getRecipient() {
        return recipient;
    }

    String getSender() {
        return sender;
    }
}
