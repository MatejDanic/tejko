def calculate_score(dice, box_order):
    score = 0
    if box_order <= 6:
        score = dice.count(box_order) * (box_order)

    elif box_order == 7 or box_order == 8:
        score = sum(dice)

    elif box_order == 9:
        for d in dice:
            if dice.count(d)  >= 3:
                score = 3 * d + 10
                break

    elif box_order == 10:
        has_straight = True
        straight = [2, 3, 4, 5]
        for n in straight:
            if n not in dice:
                has_straight = False
        if has_straight:
            if 1 in dice:
                score = 35
            elif 6 in dice:
                score = 45

    elif box_order == 11:
        found_pair = False
        found_trips = False
        for d in dice:
            if dice.count(d)  == 3 and not found_trips:
                score += 3 * dice
                found_trips = True
                continue
            elif dice.count(d)  == 2 and not found_pair:
                score += 2 * dice
                found_pair = True
                continue
        if not found_pair or not found_trips:
            score = 0
        else:
            score += 30

    elif box_order == 12:
        for d in dice:
            if dice.count(d)  >= 4:
                score = 4 * d + 40
                break

    elif box_order == 13:
        for d in dice:
            if dice.count(d)  == 5:
                score = 5 * d + 50
                break
    return score
