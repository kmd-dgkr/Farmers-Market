package models;

import javax.persistence.*;
import java.sql.Time;
import java.util.*;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long bidid; //bid id and primary key

    public Long getBidid() {
        return bidid;
    }

    public void setBidid(Long bidid) {
        this.bidid = bidid;
    }

    /*public List<String> buyers; //buyer's id's who bid for a particular bid, foreign key mapping to be done.

    public List<String> getBuyers() { return buyers; } //JAVA PERSISTENCE EXCEPTION

    public void setBuyers(List<String> buyers) { this.buyers = buyers; }*/

    public String buyers; //buyer's id's who bid for a particular bid, foreign key mapping to be done.

    public String getBuyers() { return buyers; }

    public void setBuyers(String buyers) { this.buyers = buyers; }

    public Time bidwindow;

    public Time getBidwindow() { return bidwindow; }

    public void setBidwindow(Time window) { this.bidwindow = bidwindow; }

    /*@OneToOne(mappedBy = "bidid")
    @JoinTable(name = "Crop")
    private Crop crop;*/

}
