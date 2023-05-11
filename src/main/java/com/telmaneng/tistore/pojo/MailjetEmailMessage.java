package com.telmaneng.tistore.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MailjetEmailMessage {
    @JsonProperty("FromEmail")
    private String fromEmail;
    @JsonProperty("FromName")
    private String fromName;
    @JsonProperty("Recipients")
    private List<Recipient> recipients;
    @JsonProperty("Subject")
    private String subject;
    @JsonProperty("Text-part")
    private String plainTextBody;
    @JsonProperty("Html-part")
    private String htmlTextBody;

    public MailjetEmailMessage() {
        recipients = new ArrayList<>();
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void addRecipient(String email, String name) {
        recipients.add(new Recipient(email, name));
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPlainTextBody() {
        return plainTextBody;
    }

    public void setPlainTextBody(String plainTextBody) {
        this.plainTextBody = plainTextBody;
    }

    public String getHtmlTextBody() {
        return htmlTextBody;
    }

    public void setHtmlTextBody(String htmlTextBody) {
        this.htmlTextBody = htmlTextBody;
    }

    private class Recipient {
        private String email;
        private String name;

        Recipient(String email, String name) {
            this.email = email;
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
