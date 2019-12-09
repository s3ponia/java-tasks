package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {
    private long salary;

    public Salary(String recipient, String sender, long salary) {
        super(recipient, sender);
        this.salary = salary;
    }
}
