db.createUser(
    {
        user: "weasylearner",
        pwd: "weasylearner",
        roles: [
            {
                role: "readWrite",
                db: "test"
            }
        ]
    }
)
