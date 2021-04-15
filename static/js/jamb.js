var dice_buttons, box_buttons, roll_dice_button, restart_button, roll_count, announcement;
const JAMB_URL = "http://localhost:5000/jamb";
window.onload = function () {
    initialize(jamb);
};

function initialize(jamb) {
    let t0 = performance.now();
    console.log("Initializing...")
    
    dice_buttons = document.querySelectorAll('[id^="DICE"]');
    box_buttons = document.querySelectorAll('[id^="BOX"]');
    roll_dice_button = document.getElementById("ROLL-DICE-BUTTON");
    restart_button = document.getElementById("RESTART-BUTTON");
    roll_count = jamb.roll_count;
    announcement = jamb.announcement;

    for (dice_button of dice_buttons) {
        dice_button.disabled = roll_count == 0 || roll_count == 1 && announcement == null && is_announcement_required(jamb) || roll_count == 3;

        if (dice_button.disabled) {
            dice_button.classList.add("dice-border-gray");
        } else if (dice_button.classList.contains("dice-border-red")) {
            dice_button.classList.remove("dice-border-red");
            dice_button.classList.remove("dice-border-gray");
            dice_button.classList.add("dice-border-black");
        }

        dice_button.onclick = function () {
            if (this.classList.contains("dice-border-black")) {
                this.classList.remove("dice-border-black");
                this.classList.add("dice-border-red");
            } else {
                this.classList.remove("dice-border-red");
                this.classList.add("dice-border-black");
            }
        }
        dice_button.setAttribute("style", "background-image: url(../static/images/dice/" + jamb.dice[dice_button.id.split("-")[1] - 1].value + ".png");
    }

    for (box_button of box_buttons) {
        box_button.onclick = function () {
            console.log(this.id);
        }
        box_button.disabled = !jamb.form.columns[box_button.id.split("-")[1] - 1].boxes[box_button.id.split("-")[2] - 1].available;
    }

    roll_dice_button.disabled = roll_count == 1 && announcement == null && is_announcement_required(jamb) || roll_count == 3;
    roll_dice_button.setAttribute("style", "background-image: url(../static/images/misc/roll_" + roll_count + ".png);");
    roll_dice_button.onclick = function() {
        console.log("Rolling dice...");
        roll_dice_button.disabled = true;
        let dice_to_roll = [];
        for (dice_button of dice_buttons) {
            if (dice_button.classList.contains("dice-border-black")) {
                dice_to_roll.push(parseInt(dice_button.id.split("-")[1]));
            }
        }
        data = {"dice_to_roll": dice_to_roll};

        let Http = new XMLHttpRequest();
        let url = JAMB_URL + "/" + jamb_id + "/roll";
        Http.open("PUT", url);
        Http.send(JSON.stringify(data));

        Http.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                dice = JSON.parse(Http.response);
                console.log(dice);
                for (dice_button of dice_buttons) {
                    dice_button.setAttribute("style", "background-image: url(../static/images/dice/" + dice[dice_button.id.split("-")[1] - 1].value + ".png);");
                }
                roll_count = roll_count + 1;
                roll_dice_button.setAttribute("style", "background-image: url(../static/images/misc/roll_" + roll_count + ".png);");
                roll_dice_button.disabled = roll_count == 1 && announcement == null && is_announcement_required(jamb) || roll_count == 3;
                for (dice_button of dice_buttons) {
                    dice_button.disabled = roll_count == 0 || roll_count == 1 && announcement == null && is_announcement_required(jamb) || roll_count == 3;
                    if (!dice_button.disabled) {
                        dice_button.classList.remove("dice-border-gray");
                    }
                }
            }
        }
    }

    restart_button.onclick = function() {
        console.log("Restarting...")
        restart_button.disabled = true;
        let Http = new XMLHttpRequest();
        let url = JAMB_URL + "/" + jamb_id + "/restart";
        Http.open("PUT", url);
        Http.send();

        Http.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                jamb = JSON.parse(Http.response);
                console.log(jamb);
                initialize(jamb);
                roll_dice_button.disabled = false;
                roll_count = 0;
                announcement = null;

                restart_button.disabled = false;
            }
        }
    }
    let t1 = performance.now();
    console.log("Initialization took " + (t1 - t0) + " milliseconds.");
}

function is_announcement_required(jamb) {
    for (column of jamb.form.columns) {
        for (box of column.boxes) {
            if (box.label != "ANNOUNCEMENT" && box.available) {
                return false;
            }
        }
    }
    return true;
}
