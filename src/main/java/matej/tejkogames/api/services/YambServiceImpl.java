package matej.tejkogames.api.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.UserRepository;
import matej.tejkogames.api.repositories.YambRepository;
import matej.tejkogames.api.repositories.ScoreRepository;
import matej.tejkogames.exceptions.IllegalMoveException;
import matej.tejkogames.exceptions.InvalidOwnershipException;
import matej.tejkogames.interfaces.services.YambService;
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
public class YambServiceImpl implements YambService {

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
    public Yamb getYamb(String username, YambType type, int numberOfColumns, int numberOfDice)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik s imenom " + username + " nije pronađen."));
        if (user.getYamb() != null) {
            return yambRepository.findById(user.getYamb().getId()).get();
        } else {
            return initializeYamb(user, type, numberOfColumns, numberOfDice);
        }
    }

    private Yamb initializeYamb(User user, YambType type, int numberOfColumns, int numberOfDice) {
        Yamb yamb = new Yamb(user, type, numberOfColumns, numberOfDice);
        return yambRepository.save(yamb);
    }

    /**
     * Deletes {@link Yamb} object from database repository.
     * 
     * @param username the username of the form owner
     * @param yambId   the id of yamb
     * 
     * @throws InvalidOwnershipException if form does not belong to user
     */
    public Yamb restartYambById(String username, UUID yambId) throws InvalidOwnershipException {

        if (!checkYambOwnership(username, yambId))
            throw new InvalidOwnershipException("Yamb s id-em " + yambId + " ne pripada korisniku " + username + ".");

        Yamb yamb = yambRepository.getById(yambId);
        return initializeYamb(yamb.getUser(), yamb.getType(), yamb.getNumberOfColumns(), yamb.getNumberOfDice());
    }

    public Yamb recreateYambById(String username, UUID yambId, YambType type, int numberOfColumns, int numberOfDice)
            throws InvalidOwnershipException {

        if (!checkYambOwnership(username, yambId))
            throw new InvalidOwnershipException("Yamb s id-em " + yambId + " ne pripada korisniku " + username + ".");

        Yamb yamb = yambRepository.getById(yambId);
        return initializeYamb(yamb.getUser(), type, numberOfColumns, numberOfDice);
    }

    public void saveScore(User user, int totalSum) {
        scoreRepository.save(new Score(user, totalSum));
    }

    public Yamb getById(UUID id) {
        return yambRepository.findById(id).get();
    }

    public List<Yamb> getAll() {
        return yambRepository.findAll();
    }

    public void deleteById(UUID id) {
        yambRepository.deleteById(id);
    }

    public void deleteAll() {
        yambRepository.deleteAll();
    }

    public Set<Dice> rollDice(String username, UUID yambId) throws IllegalMoveException, InvalidOwnershipException {

        if (!checkYambOwnership(username, yambId))
            throw new InvalidOwnershipException("Yamb s id-em " + yambId + " ne pripada korisniku " + username + ".");

        Yamb yamb = yambRepository.getById(yambId);
        Set<Dice> diceSet = yamb.getDiceSet();
        YambForm form = yamb.getForm();
        if (yamb.getRollCount() == 0) {
            for (Dice dice : diceSet) {
                dice.setHeld(false);
            }
        } else if (yamb.getRollCount() == 3) {
            throw new IllegalMoveException("Roll limit reached!");
        } else if (yamb.getRollCount() == 1 && yamb.getAnnouncement() == null && form.isAnnouncementRequired()) {
            throw new IllegalMoveException("Announcement is required!");
        }
        if (yamb.getRollCount() < 3) yamb.setRollCount(yamb.getRollCount() + 1);
        
        for (Dice dice : diceSet) {
            if (!dice.isHeld()) {
                dice.roll();
            }
        }
        yamb.setDiceSet(diceSet);
        yambRepository.save(yamb);

        return diceSet;
    }

    public Set<Dice> holdDice(String username, UUID yambId, int order) throws InvalidOwnershipException {

        if (!checkYambOwnership(username, yambId))
            throw new InvalidOwnershipException("Yamb s id-em " + yambId + " ne pripada korisniku " + username + ".");

        Yamb yamb = yambRepository.getById(yambId);
        Set<Dice> diceSet = yamb.getDiceSet();
        for (Dice dice : diceSet) {
            if (dice.getOrder() == order) {
                dice.setHeld(!dice.isHeld());
                break;
            }
        }
        yamb.setDiceSet(diceSet);
        yambRepository.save(yamb);

        return diceSet;
    }

    public BoxType announce(String username, UUID yambId, BoxType announcement)
            throws IllegalMoveException, InvalidOwnershipException {

        if (!checkYambOwnership(username, yambId))
            throw new InvalidOwnershipException("Yamb s id-em " + yambId + " ne pripada korisniku " + username + ".");

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

    public Yamb fill(String username, UUID yambId, ColumnType columnType, BoxType boxType)
            throws IllegalMoveException, InvalidOwnershipException {

        if (!checkYambOwnership(username, yambId))
            throw new InvalidOwnershipException("Yamb s id-em " + yambId + " ne pripada korisniku " + username + ".");

        Yamb yamb = yambRepository.getById(yambId);

        YambForm form = yamb.getForm();
        Set<Dice> diceSet = yamb.getDiceSet();

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
                    } else if (column.getType() == ColumnType.DOWNWARDS && boxType != BoxType.YAMB
                            && BoxType.valueOf(boxType.name()).ordinal() + 1 == box.getType().ordinal()) {
                        box.setAvailable(true);
                    } else if (column.getType() == ColumnType.UPWARDS && boxType != BoxType.ONES
                            && BoxType.valueOf(boxType.name()).ordinal() - 1 == box.getType().ordinal()) {
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

        yamb.setDiceSet(diceSet);
        yamb.setForm(form);
        yamb.setRollCount(0);
        yamb.setAnnouncement(null);

        yambRepository.save(yamb);

        return yamb;
    }

    private boolean checkYambOwnership(String username, UUID yambId) {
        return getById(yambId).getUser().getUsername().equals(username);
    }

}
