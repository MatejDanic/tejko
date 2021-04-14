from flask import Flask, request, render_template, redirect, url_for, jsonify, session
from pymongo import MongoClient, errors
import os, sys, random
import json
import db_operations
from models import is_announcement_required

# database
MONGO_URI = os.environ.get("MONGO_URI")
if not MONGO_URI:
    MONGO_URI = "mongodb://localhost:27017/tejko"
client = MongoClient(MONGO_URI)
db = client.jamb

#flask app
app = Flask(__name__)
app.secret_key = "tanji ključ"
app.config["MONGO_URI"] = MONGO_URI

# default route
@app.route("/")
def index():
    return render_template("index.html")

# jamb
@app.route("/jamb")
def jamb():
    try:
        # check if jamb id is in session
        if "jamb_id" in session:
            # get jamb id from session
            if not db_operations.jamb_exists_by_id(db, session["jamb_id"]):
                # if jamb doesn't exist clear the session storage
                session.pop("jamb_id")
            else:
                jamb = db_operations.find_jamb_by_id(db, session["jamb_id"])

        # check again if jamb id is not in session
        if "jamb_id" not in session:
            # save jamb to database and get jamb id
            jamb = db_operations.create_new_jamb(db)
            # put jamb id into session storage
            session["jamb_id"] = str(jamb["_id"])
        # if jamb is successfully retrieved from database render jamb view
        return render_template('jamb.html', jamb={x: jamb[x] for x in jamb if x != "_id"}, jamb_id=jamb["_id"])
    except Exception as error:
        # if an exception ocurred render view with error message
        return render_template("error.html", error=error)



@app.route("/jamb/<jamb_id>")
def jamb_load(jamb_id):
    try:
        session["jamb_id"] = jamb_id
        return redirect(url_for("index"))
    except Exception as error:
        # if an exception ocurred render view with error message
        return render_template("error.html", error=error)


# route for rolling dice
@app.route("/jamb/<jamb_id>/roll", methods=["PUT"])
def roll(jamb_id):
    try:
        # check if jamb with given id exists
        if db_operations.jamb_exists_by_id(db, jamb_id):
            # get jamb from database by jamb id
            jamb = db_operations.find_jamb_by_id(db, jamb_id)
            dice_to_roll = json.loads(request.data.decode('utf8'))["dice_to_roll"]

            if jamb["roll_count"] == 0:
                dice_to_throw = [1, 2, 3, 4, 5]
            elif jamb["roll_count"] == 3:
                raise Exception("Dice roll limit reached!")
            elif jamb["roll_count"] == 1 and jamb["announcement"] is None and is_announcement_required(jamb):
                raise Exception("Announcement is required!")
                
            
            jamb["roll_count"] = jamb["roll_count"] + 1

            for dice in jamb["dice"]:
                if dice["ordinal"] in dice_to_roll:
                    dice["value"] = random.randint(1, 6)
            # update current jamb
            db_operations.update_jamb(db, jamb)
            # return dice values
            return jsonify(jamb["dice"])
        else:
            raise Exception("jamb with ID " + jamb_id + " doesn't exist!")
    except Exception as error:
        response = jsonify({"error": str(error)})
        #response.status = 500
        # if an exception ocurred return error status
        return response


# route for restarting jamb
@app.route("/jamb/<jamb_id>/restart", methods=["PUT"])
def restart(jamb_id):
    try:
        # check if jamb with given id exists
        if db_operations.jamb_exists_by_id(db, jamb_id):
            # restart jamb from database by jamb id
            jamb = db_operations.restart_jamb_by_id(db, jamb_id)
            response = jsonify({x: jamb[x] for x in jamb if x != "_id"})
            return response
    except Exception as error:
        response = jsonify({"error": str(error)})
        #response.status = 500
        # if an exception ocurred return error status
        return response


if __name__ == "__main__":
    app.run(debug=True)
