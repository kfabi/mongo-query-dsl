# Mongo Query DSL

Mongo query DSL is an internal Kotlin DSL that aims to make querying MongoDb easy and typesafe.

## How it works

Typically, when using MongoDb from Kotlin via the official driver, you would create a ``data class`` per collection:

````kotlin
data class Person(
    val _id: ObjectId = ObjectId(),
    val nameFirst: String,
    val nameLast: String,
    val dateOfBirth: BsonDate,
    val hobbies: List<String>,
    val contacts: List<Contact>,
)

data class Contact(val nameFirst: String, val nameLast: String, val phone: String, val favorite: Boolean)
````

And then use a driver to run queries:

````kotlin
val mongoClient = MongoClient.create("<connection string>")
val db = mongoClient.getDatbase("<db name>")
val persons = db.getCollection<Person>("persons")
persons.find(
    Filters.eq("${Peron::contacts.name}.${Contact::favorite.name}", true)
)
````

It is possible to construct queries that fail at runtime or behave unexpectedly:

````kotlin

// contacts.foo does not exist
val filterOnUnknownField = Filters.eq("${Peron::contacts.name}.foo", true)
// favorite is a boolean. Comparing it to a number is usually not intended.
val filterWithUnexpectedType = Filters.eq("${Peron::contacts.name}.${Contact::favorite.name}", 7)
````

To prevent these errors the DSL introduces type safety to the queries.
There are three important concepts for that.

* MongoPaths: Describe tha path to a field (or multiple) in a document including their type.
* Operators: Can be applied to the MongoPath to describe what to filter/update.

````kotlin
// construct a query for a collection holding Person
mongoQuery<Person> {
    // construct a path to the contacts field
    // equivalent to: "nameFirst"
    field(Person::nameFirst) 
    
    // query on that field using a matching operator
    // equivalent to: "nameFirst": { "$eq": "Max" }
    field(Persen::nameFirst) eq "Max"
    
    // construct a nested path using '/'
    // equivalent to: "contacts.nameFirst"
    field(Person::contacts / Contact::nameFirst)
    
    // construct a nested path matching an element in an array at a specific index
    // equivalent to: "contacts.0.nameFrist"
    field(Person::contacts[0] / Contact::nameFirst)
    
    // multiple queries are combined as conjunctions by default. If you want a disjunction use:
    or {
        field(Persen::nameFirst) eq "Max"
        field(Persen::nameFirst) eq "Erika"
        // add a conjunction to the disjunction
        and {
            field(Person::contacts[0] / Contact::nameFirst) eq "Alice"
            field(Person::contacts[0] / Contact::favorite) eq true
        }
    }
}
````

Now we have seen how to construct queries. But the real beauty is that syntactically incorrect queries do not compile. Additionally, queries that make no sense semantically don't compile either (to a certain degree).

````kotlin
// does not compile
mongoQuery<Person> {
    // because Contact is not the type of the Query. Can only use references of Person for the root of paths.
    field(Contact::favorite)
    // because the contacts reference points to a List<Contact>, so only Contact reference can be used to continue the path.
    field(Person::contacts / Person::nameFirst)
    // because the favorite field is of type boolean so operators can only be called with the matching type.
    field(Person::contacts / Contact::favorite) eq 7
}
````

It is also possible to build update queries:

````kotlin
mongoUpdate<Person> {
    // construct paths the same way
    field(Person::nameFirst)
    // and use matching update statements
    field(Person::nameFirst) set "Max 2.0"
    // depending on the type only semantically fitting operators are available. 
    // E.g. for Lists we have the array update statements:
    field(Person::hobbies) push "Music"
}
````

## Modules

### core

The core module holds the DSL for describing queries/updates

### coroutines

Depends on core and provides extensions for the Coroutine [MongoCollection](https://mongodb.github.io/mongo-java-driver/5.5/apidocs/driver-kotlin-coroutine/mongodb-driver-kotlin-coroutine/com.mongodb.kotlin.client.coroutine/-mongo-collection/index.html) so quereis/updates can be specified directly in lambdas:

````kotlin
val persons = database.getCollection<Person>("persons")

// define queries directly in lambdas instead of using mongoQuery builder function
persons.find {
    field(Person::nameFirst) eq "Max"
}

// define updates and their queries directly in lambdas instead of using mongoUpdate builder function
persons.updateOne(
    filter = { field(Person::nameFirst) eq "Max" },
    update = { field(Person::nameFirst) set "Max 2.0" },
)
````

