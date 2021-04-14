from bson import ObjectId
from models import Jamb

def find_jamb_by_id(db, jamb_id):
    return db.jamb.find_one({"_id": ObjectId(jamb_id)})

def create_new_jamb(db):
    jamb = Jamb().to_dict()
    inserted_id = db.jamb.insert_one(jamb).inserted_id
    return db.jamb.find_one({"_id": inserted_id})

def update_jamb(db, jamb):
    db.jamb.update_one({"_id": ObjectId(jamb["_id"])}, {"$set": {"dice": jamb["dice"], "form": jamb["form"], "roll_count": jamb["roll_count"], "announcement": jamb["announcement"]}}, upsert=False)

def restart_jamb_by_id(db, jamb_id):
    jamb = Jamb().to_dict()
    db.jamb.update_one({"_id": ObjectId(jamb_id)}, {"$set": {"dice": jamb["dice"], "form": jamb["form"], "roll_count": jamb["roll_count"], "announcement": jamb["announcement"]}}, upsert=False)
    return db.jamb.find_one({"_id": ObjectId(jamb_id)})

def remove_jamb_by_id(db, jamb_id):
    db.jamb.remove({"_id": ObjectId(jamb_id)})

def jamb_exists_by_id(db, jamb_id):
    return ObjectId.is_valid(jamb_id) and db.jamb.find_one({"_id" : ObjectId(jamb_id)}) is not None