package de.kfabi.mongo.query.dsl

import org.bson.conversions.Bson

@DslMarker annotation class MongoQueryDsl

@MongoQueryDsl
fun <T> mongoQuery(query: MongoQueryScope.And<T>.() -> Unit): Bson =
  MongoQueryScope.And<T>().apply(query).toBson()

@MongoQueryDsl
fun <T> mongoUpdate(query: MongoUpdateScope<T>.() -> Unit): Bson =
  MongoUpdateScope<T>().apply(query).toBson()
