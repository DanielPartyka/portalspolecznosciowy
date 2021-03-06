package com.example.portalspolecznosciowy.models;

import javax.persistence.*;

@Entity
public class Followers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "follower_id",nullable = false)
    private User follower_id;

    public Followers() {

    }

    public Followers(User user, User follower_id) {
        this.user = user;
        this.follower_id = follower_id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(User follower_id) {
        this.follower_id = follower_id;
    }
}
