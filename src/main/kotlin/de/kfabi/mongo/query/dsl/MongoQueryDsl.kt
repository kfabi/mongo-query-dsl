package de.kfabi.mongo.query.dsl

@DslMarker annotation class MongoQueryDsl

@MongoQueryDsl
fun <T> mongoQuery(query: MongoQueryScope<T>.() -> Unit) =
  DefaultMongoQueryScope<T>().apply(query).asBson()

@MongoQueryDsl
fun <T> mongoUpdate(update: MongoUpdateScope<T>.() -> Unit) =
  DefaultMongoUpdateScope<T>().apply(update).asBson()
