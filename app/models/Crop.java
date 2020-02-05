package models;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class Crop {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long cid; //primary key with autoincrement.

    public String name;

    public Long getId() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.cid = id;
    }

    public String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Time duration;

    public Time getDuration() { return duration; }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

  /*@ManyToOne
    public Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }*/

    /*@OneToOne(mappedBy = "Crop", cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    public Bid bidid;

    public Bid getBid() {
        return bidid;
    }

    public void setBid(Bid bidid) {
        this.bidid = bidid;
    }*/

}
