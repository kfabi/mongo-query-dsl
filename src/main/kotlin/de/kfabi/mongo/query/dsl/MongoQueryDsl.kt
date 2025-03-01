package de.kfabi.mongo.query.dsl

@DslMarker annotation class MongoQueryDsl

@MongoQueryDsl
fun <T> mongoQuery(query: MongoQueryScope<T>.() -> Unit) =
  DefaultMongoQueryScope<T>().apply(query).asBson()
