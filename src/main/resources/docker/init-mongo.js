db.auth('root', 'root')

db = db.getSiblingDB('weasylearn')

db.createUser(
    {
        user: "weasylearner",
        pwd: "weasylearner",
        roles: [
            {
                role: "readWrite",
                db: "weasylearn"
            }
        ]
    }
);
