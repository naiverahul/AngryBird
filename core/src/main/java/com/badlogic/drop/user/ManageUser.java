package com.badlogic.drop.user;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ManageUser implements Serializable {
    private Set<User> users;
    public ManageUser() {
        this.users = new HashSet<User>();
    }
    public boolean addUser(String name){
        return users.add(new User(name));
    }
    public boolean removeUser(String name){
        for(User user: users){
            if(user.getName().equals(name)){
                users.remove(user);
                return true;
            }
        }
        return false;
    }
    public boolean checkUser(String name){
        for(User user: users){
            if(user.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
    public User getUser(String name){
        for(User user: users){
            if(user.getName().equalsIgnoreCase(name)){
                return user;
            }
        }
        return null;
    }
    public Set<User> getUsers(){
        return users;
    }
}
