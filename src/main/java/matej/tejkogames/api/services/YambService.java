package matej.tejkogames.api.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.UserRepository;
import matej.tejkogames.api.repositories.YambRepository;
import matej.tejkogames.api.repositories.ScoreRepository;
import matej.tejkogames.exceptions.IllegalMoveException;
import matej.tejkogames.exceptions.InvalidOwnershipException;
import matej.tejkogames.models.yamb.YambType;
import matej.tejkogames.utils.YambUtil;
import matej.tejkogames.models.general.User;
import matej.tejkogames.models.yamb.Box;
import matej.tejkogames.models.yamb.BoxType;
import matej.tejkogames.models.yamb.Column;
import matej.tejkogames.models.yamb.ColumnType;
import matej.tejkogames.models.yamb.Dice;
import matej.tejkogames.models.yamb.Score;
import matej.tejkogames.models.yamb.Yamb;
import matej.tejkogames.models.yamb.YambForm;

@Service
public class YambService {

    @Autowired
    YambRepository yambRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScoreRepository scoreRepository;

    /**
     * Creates or retrieves existing {@link Yamb} Object for the current
     * {@link User}.
     * 
     * @param username the username of the current user
     * 
     * @return {@link Yamb} the form that the game will be played on
     * 
     * @throws UsernameNotFoundException if user with given username does not exist
     */
    public Yamb initializeYamb(String username, YambType type, int numberOfColumns, int numberOfDice)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik s imenom " + username + " nije pronađen."));
        if (user.getYamb() != null) {
            return yambRepository.findById(user.getYamb().getId()).get();
        } else {
            Yamb yamb = new Yamb(user, type, numberOfColumns, numberOfDice);
            return yambRepository.save(yamb);
        }
    }

    /**
     * Deletes {@link Yamb} object from database repository.
     * 
     * @param username the username of the form owner
     * @param id       the id of the form
     * 
     * @throws InvalidOwnershipException if form does not belong to user
     */
    public void restartYambById(int id) throws InvalidOwnershipException {
        Yamb yamb = yambRepository.getById(id);
        yamb.setAnnouncement(null);
        yamb.setRollCount(0);
        YambForm form = YambUtil.formStringToForm(yamb.getForm());
        for (Column column : form.getColumns()) {
            for (Box box : column.getBoxes()) {
                box.setValue(0);
                if (column.getType() == ColumnType.FREE || column.getType() == ColumnType.ANNOUNCEMENT)
                    box.setAvailable(true);
                else if (column.getType() == ColumnType.DOWNWARDS && box.getType() == BoxType.ONES)
                    box.setAvailable(true);
                else if (column.getType() == ColumnType.UPWARDS && box.getType() == BoxType.YAMB)
                    box.setAvailable(true);
                else
                    box.setAvailable(false);
                box.setFilled(false);
            }
        }
        Set<Dice> diceSet = YambUtil.diceSetStringToDiceSet(yamb.getDiceSet());

        for (Dice dice : diceSet) {
            dice.setValue(6);
        }
        yamb.setDiceSet(YambUtil.diceSetToDiceSetString(diceSet));
        yambRepository.save(yamb);
    }

    public void saveScore(User user, int totalSum) {
        scoreRepository.save(new Score(user, totalSum));
    }

    public Yamb getYambById(int id) {
        return yambRepository.findById(id).get();
    }

    public List<Yamb> getYambList() {
        return yambRepository.findAll();
    }

    
    public Set<Dice> rollDice(int yambId) throws IllegalMoveException {
        Yamb yamb = yambRepository.getById(yambId);

        Set<Dice> diceSet = YambUtil.diceSetStringToDiceSet(yamb.getDiceSet());
        YambForm form = YambUtil.formStringToForm(yamb.getForm());
        if (yamb.getRollCount() == 0) {
            for (Dice dice : diceSet) {
                dice.setHeld(false);
            }
        } else if (yamb.getRollCount() == 3) {
            throw new IllegalMoveException("Roll limit reached!");
        } else if (yamb.getRollCount()== 1 && yamb.getAnnouncement()== null && 
                    form.isAnnouncementRequired()) {
            throw new IllegalMoveException("Announcement is required!");
        }

        if (yamb.getRollCount() < 3) {
            yamb.setRollCount(yamb.getRollCount() + 1);
        }

        for (Dice dice : diceSet) {
            if (!dice.isHeld()) {
                dice.roll();
            }
        }
        yamb.setDiceSet(YambUtil.diceSetToDiceSetString(diceSet));
        yambRepository.save(yamb);

        return diceSet;
    }

    public Set<Dice> holdDice(int yambId, int order) {
        Yamb yamb = yambRepository.getById(yambId);

        Set<Dice> diceSet = YambUtil.diceSetStringToDiceSet(yamb.getDiceSet());
        for (Dice dice : diceSet) {
            if (dice.getOrder() == order) {
                dice.setHeld(!dice.isHeld());
                break;
            }
        }
        yamb.setDiceSet(YambUtil.diceSetToDiceSetString(diceSet));
        yambRepository.save(yamb);

        return diceSet;
    }

    public BoxType announce(int yambId, BoxType announcement) throws IllegalMoveException {
        Yamb yamb = yambRepository.getById(yambId);

        if (yamb.getAnnouncement() != null) {
            throw new IllegalMoveException("Announcement already declared!");
        } else if (yamb.getRollCount() != 1) {
            throw new IllegalMoveException("Announcement is available only after first roll!");
        }

        yamb.setAnnouncement(announcement);
        yambRepository.save(yamb);

        return yamb.getAnnouncement();
    }

    public Yamb fill(int yambId, ColumnType columnType, BoxType boxType) throws IllegalMoveException {
        Yamb yamb = yambRepository.getById(yambId);

        YambForm form = YambUtil.formStringToForm(yamb.getForm());
        Set<Dice> diceSet = YambUtil.diceSetStringToDiceSet(yamb.getDiceSet());

        Box selectedBox = form.getColumnByType(columnType).getBoxByType(boxType);
        
        if (selectedBox.isFilled()) {
			throw new IllegalMoveException("Box is already filled!");
        } else if (!selectedBox.isAvailable()) {
            throw new IllegalMoveException("Box is not available!");
        } else if (yamb.getRollCount() == 0) {
            throw new IllegalMoveException("Cannot fill box without rolling dice first!");
        } else if (yamb.getAnnouncement() != null && yamb.getAnnouncement() != selectedBox.getType()) {
            throw new IllegalMoveException("Another box is announced");
        }

        for (Column column : form.getColumns()) {
            if (column.getType() == columnType) {
                for (Box box : column.getBoxes()) {
                    if (box.getType() == boxType) {
                        box.fill(YambUtil.calculateScore(diceSet, box.getType()));
                    } else if (column.getType() == ColumnType.DOWNWARDS && 
                    boxType != BoxType.YAMB && 
                    BoxType.valueOf(boxType.name()).ordinal() + 1 == box.getType().ordinal()) {
                        box.setAvailable(true);
                    } else if (column.getType() == ColumnType.UPWARDS && 
                    boxType != BoxType.ONES && 
                    BoxType.valueOf(boxType.name()).ordinal() - 1 == box.getType().ordinal()) {
                        box.setAvailable(true);
                    }
                }   
                break;
            }
        }

        form.updateSums(columnType);
        form.setAvailableBoxes(form.getAvailableBoxes() - 1);
        
        if (form.getAvailableBoxes() == 0) {
            saveScore(yamb.getUser(), form.getTotalSum());
        }

        for (Dice dice : diceSet) {
            dice.setHeld(false);
        }

        yamb.setDiceSet(YambUtil.diceSetToDiceSetString(diceSet));
        yamb.setForm(YambUtil.formToFormString(form));
        yamb.setRollCount(0);
        yamb.setAnnouncement(null);

        yambRepository.save(yamb);

        return yamb;
    }

}
