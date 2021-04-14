COLUMN_TYPES = ["DOWNWARDS", "UPWARDS", "ANY_DIRECTION", "ANNOUNCEMENT"]
BOX_TYPES = ["ONES", "TWOS", "THREES", "FOURS", "FIVES", "SIXES", "MAX", "MIN", "TRIPS", "STRAIGHT", "FULL", "POKER", "JAMB"]

class Jamb:
    def __init__(self):
        self.announcement = None
        self.roll_count = 0
        self.dice = []
        for i in range(5):
            dice = {}
            dice["ordinal"] = i + 1
            dice["value"] = 6
            self.dice.append(dice)
        self.form = {}
        self.form["columns"] = []
        for i in range(4):
            column = {}
            column["type"] = COLUMN_TYPES[i]
            column["boxes"] = []
            for j in range(13):
                box = {}
                box["type"] = BOX_TYPES[j]
                box["value"] = 0
                box["filled"] = False
                box["available"] = column["type"] == "DOWNWARDS" and box["type"] == "ONES" or column["type"] == "UPWARDS" and box["type"] == "JAMB" or column["type"] == "ANY_DIRECTION" or column["type"] == "ANNOUNCEMENT"
                column["boxes"].append(box)
            self.form["columns"].append(column)


    def __repr__(self):
        string = ""
        for d in self.dice:
            C='o '
            dice_string =  '-----\n|'+C[d["value"]<1]+' '+C[d["value"]<3]+'|\n|'+C[d["value"]<5]
            string += dice_string+C[d["value"]&1]+dice_string[::-1]
            string += "\n"
        string += "\n"
        for i in range(13):
            string += '| '
            for j in range(4):
                if self.form["columns"][j]["boxes"][i]["filled"]:
                    string += str(self.form["columns"]
                                  [j]["boxes"][i]["value"]) + ' | '
                else:
                    string += '- | '
            string += '</br>'
        return string

    def to_dict(self):
        return dict((key, value) for (key, value) in self.__dict__.items())

def is_announcement_required(jamb):
        for i in range(3):
            for j in range(13):
                if jamb["form"]["columns"][i]["boxes"][j]["available"]:
                    return False
        return True