package com.hebaja.auction.model;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    protected String name;
    
    public User() {}

    public User(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

	public void setName(String name) {
		this.name = name;
	}

}
