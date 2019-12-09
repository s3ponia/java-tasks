package ru.mail.polis.homework.collections.mail;

public class MailMessage extends Mail {
    private final String text;

    public MailMessage(String recipient, String sender, String text) {
        super(recipient, sender);
        this.text = text;
    }
}
