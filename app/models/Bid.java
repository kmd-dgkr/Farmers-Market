package models;

import javax.persistence.*;
import java.sql.Time;
import java.util.*;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long bidId; //bid id and primary key

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    /*public List<String> buyers; //buyer's id's who bid for a particular bid, foreign key mapping to be done.

    public List<String> getBuyers() { return buyers; } //JAVA PERSISTENCE EXCEPTION

    public void setBuyers(List<String> buyers) { this.buyers = buyers; }*/

    public String buyers; //buyer's id's who bid for a particular bid, foreign key mapping to be done.

    public String getBuyers() { return buyers; }

    public void setBuyers(String buyers) { this.buyers = buyers; }

    public Time bidWindow;

    public Time getBidWindow() { return bidWindow; }

    public void setBidWindow(Time bidWindow) { this.bidWindow = bidWindow; }

    @OneToOne(mappedBy = "bidId")
    @JoinTable(name = "Crop")
    private Crop crop;

    public Crop getCrop() { return crop; }

    public void setCrop(Crop crop) { this.crop = crop; }

}
