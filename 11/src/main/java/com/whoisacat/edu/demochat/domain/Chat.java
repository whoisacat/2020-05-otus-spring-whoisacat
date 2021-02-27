package com.whoisacat.edu.demochat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Document
public class Chat {

    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @Field(name = "isIndividual")
    private boolean isIndividual = true;

    @Field(name = "users")
    private List<ChatUser> users = new ArrayList<>();


    @Field(name = "lastChange")
    private LocalDateTime lastChange;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(boolean individual) {
        isIndividual = individual;
    }

    public List<ChatUser> getUsers() {
        return users;
    }

    public void setUsers(List<ChatUser> users) {
        this.users = users;
    }

    public LocalDateTime getLastChange() {
        return lastChange;
    }

    public void setLastChange(LocalDateTime lastChange) {
        this.lastChange = lastChange;
    }
}
