package matej.tejkogames.models.yamb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.rest.core.annotation.RestResource;

import matej.tejkogames.constants.YambConstants;
import matej.tejkogames.models.general.User;
import matej.tejkogames.utils.YambUtil;

@Entity
@Table(name = "game_yamb")
@RestResource(rel = "yambs", path = "yambs")
public class Yamb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@OneToOne
    @JsonIgnore
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

    @Column
    private YambType type;

    @Column
    private int numberOfColumns;
    
    @Column
    private int numberOfDice;
    
    @Lob
    private String form;

    @Column
    private BoxType announcement;
    
    @Lob
    private String diceSet;
    
    @Column
    private int rollCount;

    public Yamb(User user, YambType type, int numberOfColumns, int numberOfDice) {
        if (type == YambType.CLASSIC) {
            numberOfColumns = YambConstants.NUMBER_OF_COLUMNS;
            numberOfDice = YambConstants.NUMBER_OF_DICE;
        }
        this.user = user;
        this.form = YambUtil.generateYambForm(type, YambConstants.NUMBER_OF_COLUMNS, YambConstants.NUMBER_OF_DICE);
        this.announcement = null;
        this.rollCount = 0;
    }

    public Yamb() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public YambType getType() {
        return type;
    }

    public void setType(YambType type) {
        this.type = type;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfDice() {
        return numberOfDice;
    }

    public void setNumberOfDice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public BoxType getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(BoxType announcement) {
        this.announcement = announcement;
    }

    public String getDiceSet() {
        return diceSet;
    }

    public void setDiceSet(String diceSet) {
        this.diceSet = diceSet;
    }

    public int getRollCount() {
        return rollCount;
    }

    public void setRollCount(int rollCount) {
        this.rollCount = rollCount;
    }

    
}
