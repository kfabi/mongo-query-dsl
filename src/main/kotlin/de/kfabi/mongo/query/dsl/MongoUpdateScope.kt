package de.kfabi.mongo.query.dsl

import com.mongodb.client.model.Updates
import org.bson.BsonDateTime
import org.bson.BsonTimestamp
import org.bson.conversions.Bson

@MongoQueryDsl
class MongoUpdateScope<T> : MongoFieldScope<T>() {
  private val updates: MutableList<Bson> = mutableListOf()

  @MongoQueryDsl
  fun <R : Number> MongoPath<R>.inc(by: R) {
    updates += Updates.inc(value, by)
  }

  @MongoQueryDsl
  fun <R : Number> MongoPath<R>.mul(by: R) {
    updates += Updates.mul(value, by)
  }

  @MongoQueryDsl
  fun <R> MongoPath<R>.set(item: R) {
    updates += Updates.set(value, item)
  }

  @MongoQueryDsl
  fun <R> MongoPath<R>.setOnInsert(item: R) {
    updates += Updates.setOnInsert(value, item)
  }

  @MongoQueryDsl
  fun <R> MongoPath<R>.max(item: R & Any) {
    updates += Updates.max(value, item)
  }

  @MongoQueryDsl
  fun <R> MongoPath<R>.min(item: R & Any) {
    updates += Updates.min(value, item)
  }

  @MongoQueryDsl
  fun <R> MongoPath<R>.unset() {
    updates += Updates.unset(value)
  }

  @MongoQueryDsl
  fun MongoPath<BsonDateTime>.currentData() {
    updates += Updates.currentDate(value)
  }

  @MongoQueryDsl
  fun MongoPath<BsonTimestamp>.currentTimestamp() {
    updates += Updates.currentTimestamp(value)
  }

  @MongoQueryDsl
  fun <I : Iterable<R>, R> MongoPath<I>.addToSet(item: R) {
    updates += Updates.addToSet(value, item)
  }

  @MongoQueryDsl
  fun <I : Iterable<R>, R> MongoPath<I>.addEachToSet(items: Iterable<R>) {
    updates += Updates.addEachToSet(value, items.toList())
  }

  @MongoQueryDsl
  fun <I : Iterable<R>, R> MongoPath<I>.push(item: R) {
    updates += Updates.push(value, item)
  }

  @MongoQueryDsl
  fun <I : Iterable<R>, R> MongoPath<I>.pushEach(items: Iterable<R>) {
    updates += Updates.pushEach(value, items.toList())
  }

  @MongoQueryDsl
  fun <I : Iterable<R>, R> MongoPath<I>.popFirst() {
    updates += Updates.popFirst(value)
  }

  @MongoQueryDsl
  fun <I : Iterable<R>, R> MongoPath<I>.popLast() {
    updates += Updates.popLast(value)
  }

  @MongoQueryDsl
  fun <I : Iterable<R>, R> MongoPath<I>.pull(item: R) {
    updates += Updates.pull(value, item)
  }

  @MongoQueryDsl
  fun <I : Iterable<R>, R> MongoPath<I>.pullAll(items: Iterable<R>) {
    updates += Updates.pullAll(value, items.toList())
  }

  internal fun toBson() = Updates.combine(updates)
}
