package net.stevencai.entity;

import net.stevencai.security.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String title;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private LocalDateTime lastUpdatedTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    public Account() {
        this.lastUpdatedTime = LocalDateTime.now();
    }

    public Account(String title, String username, String password) {
        this.title = title;
        this.username = username;
        this.password = password;
        this.lastUpdatedTime = LocalDateTime.now();
    }

    public Account(String title, String username, String password, String email) {
        this.title = title;
        this.username = username;
        this.password = password;
        this.email = email;
        this.lastUpdatedTime = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        String key = password.substring(0, PasswordEncoder.getKeySize() / 4);
        String encryptedPw = password.substring(PasswordEncoder.getKeySize() / 4);
        try {
            return PasswordEncoder.decryptPassword(encryptedPw, key);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setPassword(String password) {
        try {
            String key = PasswordEncoder.generateKey();

            this.password = key + PasswordEncoder.encryptPassword(password, key);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
