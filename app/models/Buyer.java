package models;

import javax.persistence.*;

@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long bid; //primary key with autoincrement.

    public String name;

    public Long getId() {
        return bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.bid = id;
    }

    public String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
