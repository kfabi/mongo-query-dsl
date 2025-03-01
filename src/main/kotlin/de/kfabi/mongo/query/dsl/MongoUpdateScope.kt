package de.kfabi.mongo.query.dsl

import com.mongodb.client.model.Updates
import org.bson.BsonDateTime
import org.bson.BsonTimestamp
import org.bson.conversions.Bson

@MongoQueryDsl
interface MongoUpdateScope<T> : MongoFieldScope<T> {
  @MongoQueryDsl infix fun <R : Number> MongoField<R>.inc(by: R)

  @MongoQueryDsl infix fun <R : Number> MongoField<R>.mul(by: R)

  @MongoQueryDsl infix fun <R> MongoField<R>.set(item: R)

  @MongoQueryDsl infix fun <R> MongoField<R>.setOnInsert(item: R)

  @MongoQueryDsl infix fun <R> MongoField<R>.max(item: R & Any)

  @MongoQueryDsl infix fun <R> MongoField<R>.min(item: R & Any)

  @MongoQueryDsl fun <R> MongoField<R>.unset()

  @MongoQueryDsl fun MongoField<BsonDateTime>.currentData()

  @MongoQueryDsl fun MongoField<BsonTimestamp>.currentTimestamp()

  @MongoQueryDsl infix fun <I : Iterable<R>, R> MongoField<I>.addToSet(item: R)

  @MongoQueryDsl infix fun <I : Iterable<R>, R> MongoField<I>.addEachToSet(items: Iterable<R>)

  @MongoQueryDsl infix fun <I : Iterable<R>, R> MongoField<I>.push(item: R)

  @MongoQueryDsl infix fun <I : Iterable<R>, R> MongoField<I>.pushEach(items: Iterable<R>)

  @MongoQueryDsl fun <I : Iterable<R>, R> MongoField<I>.popFirst()

  @MongoQueryDsl fun <I : Iterable<R>, R> MongoField<I>.popLast()

  @MongoQueryDsl infix fun <I : Iterable<R>, R> MongoField<I>.pull(item: R)

  @MongoQueryDsl infix fun <I : Iterable<R>, R> MongoField<I>.pullAll(items: Iterable<R>)
}

internal class DefaultMongoUpdateScope<T> : MongoUpdateScope<T> {
  private val updates: MutableList<Bson> = mutableListOf()

  override fun <R : Number> MongoField<R>.inc(by: R) {
    updates += Updates.inc(value, by)
  }

  override fun <R : Number> MongoField<R>.mul(by: R) {
    updates += Updates.mul(value, by)
  }

  override fun <R> MongoField<R>.set(item: R) {
    updates += Updates.set(value, item)
  }

  override fun <R> MongoField<R>.setOnInsert(item: R) {
    updates += Updates.setOnInsert(value, item)
  }

  override fun <R> MongoField<R>.max(item: R & Any) {
    updates += Updates.max(value, item)
  }

  override fun <R> MongoField<R>.min(item: R & Any) {
    updates += Updates.min(value, item)
  }

  override fun <R> MongoField<R>.unset() {
    updates += Updates.unset(value)
  }

  override fun MongoField<BsonDateTime>.currentData() {
    updates += Updates.currentDate(value)
  }

  override fun MongoField<BsonTimestamp>.currentTimestamp() {
    updates += Updates.currentTimestamp(value)
  }

  override fun <I : Iterable<R>, R> MongoField<I>.addToSet(item: R) {
    updates += Updates.addToSet(value, item)
  }

  override fun <I : Iterable<R>, R> MongoField<I>.addEachToSet(items: Iterable<R>) {
    updates += Updates.addEachToSet(value, items.toList())
  }

  override fun <I : Iterable<R>, R> MongoField<I>.push(item: R) {
    updates += Updates.push(value, item)
  }

  override fun <I : Iterable<R>, R> MongoField<I>.pushEach(items: Iterable<R>) {
    updates += Updates.pushEach(value, items.toList())
  }

  override fun <I : Iterable<R>, R> MongoField<I>.popFirst() {
    updates += Updates.popFirst(value)
  }

  override fun <I : Iterable<R>, R> MongoField<I>.popLast() {
    updates += Updates.popLast(value)
  }

  override fun <I : Iterable<R>, R> MongoField<I>.pull(item: R) {
    updates += Updates.pull(value, item)
  }

  override fun <I : Iterable<R>, R> MongoField<I>.pullAll(items: Iterable<R>) {
    updates += Updates.pullAll(value, items.toList())
  }

  internal fun asBson(): Bson {
    return Updates.combine(updates)
  }
}
