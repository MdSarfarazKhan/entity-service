# Entity managment system
Entity managment is a basic enitity store for storing and fetching any kind of entity. The system allows to create and retrieve any type entity over REST using json format. It also provides a way to add simple business rule or validation rule on entity. Using a REST post request we can define business rule or validation by a simple JSON object.

The system allows the following capabilities::
  - Creation of any kind of entity without any code changes.
  - Adding, removing, modifying (by PUT) entity.
  - It allows nested structure of Objects/entities.
  - Creating of entity type with it's validation rule.
  - Each attribute has it's own validation and business rule.

For simplicity the **authentication is basic in memory authentication** with default user , password and role in config.

# Config

```
db.mongo.connection=mongodb/localhost:27017
db.mongo.dbname=entity
com.msrk.er.auth.user=test
com.msrk.er.auth.pass=test
com.msrk.er.auth.role=test
```

# Usage
Create the package
```
mvn clean package
java -cp 
java -jar target/entity-service-1.0.0-SNAPSHOT.jar
```
##### Swagger UI Can be accessed

```
http://localhost:8080/swagger-ui.html
```
#### Sample Request
###### Entity Type Creation
POST:

```
http://localhost:8080/entity/collection/patient
{
	"name": "patient",
	"required": [
		"name",
		"age",
		"gender"
	],
	"attributes": [{
			"name": "name",
			"attributeType": "STRING",
			"attributeValidationType": "REGEX",
			"attributeValidationData": "^[A-Za-z .]+"
		}, {
			"name": "gender",
			"attributeType": "STRING",
			"attributeValidationType": "SET",
			"attributeValidationData": "Male, Female, Other"
		},
		{
			"name": "age",
			"attributeType": "INT",
			"attributeValidationType": "RANGE",
			"attributeValidationData": "0 - 120"
		}
	]
}
```

###### Entity Creation

```
http://localhost:8080/entity/patient
{
	"name":"John",
	"age": 26,
	"location":"Bangalore",
	"gender":"Male",
	
	"address" : {
		"house":"221B",
		"street": "Baker steet",
		"city":"London",
		"country":"UK"
	}
	
}
```

###### Misc. Request
GET request to get entity from a given collection of specific id
http://localhost:8080/entity/<collectionType>/<id>

DELETE request to delete entity of a given collection of specific id
http://localhost:8080/entity/<collectionType>/<id>

PUT request to update entity of a given collection of specific id

```
http://localhost:8080/entity/<collectionType>/<id>

{
//JSON Data
}
```

### Future Scope
  - Add search/listing feature for entity.
  - Enhance in memory authentication to use UAA server. 
  - Enhance and add more flixble and descriptive business rule layer.
