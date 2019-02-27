# EndPoints

id = Numbers from 0 to inf

| CMD    |   path      |   Action  |
|--------|-------------|-----------|
| HEAD   |  /          |  OK 200   |
| GET    |  /json      | Give all jsons |
| GET    |  /json/{id} | Give json by {id} |
| POST   |  /json/{id} | Add a new json to server, cannot over erased an existing {id} |
| PUT    |  /json/{id} |  Update json file (replacement) |
| DELETE |  /json/{id} | Remove json by {id} |
     
 