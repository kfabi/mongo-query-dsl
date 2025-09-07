package de.kfabi.mongo.query.dsl

import com.mongodb.client.model.Filters
import org.bson.conversions.Bson

@MongoQueryDsl
sealed class MongoQueryScope<T> : MongoPathScope<T>() {
  protected val filters: MutableList<Bson> = mutableListOf()

  protected fun add(bson: Bson) {
    filters.add(bson)
  }

  @MongoQueryDsl
  class And<T> internal constructor() : MongoQueryScope<T>() {

    @MongoQueryDsl
    fun or(query: Or<T>.() -> Unit) {
      add(Or<T>().apply(query).toBson())
    }

    internal fun toBson() = Filters.and(filters)
  }

  @MongoQueryDsl
  class Or<T> internal constructor() : MongoQueryScope<T>() {

    @MongoQueryDsl
    fun and(query: And<T>.() -> Unit) {
      add(And<T>().apply(query).toBson())
    }

    internal fun toBson() = Filters.or(filters)
  }

  @MongoQueryDsl infix fun <R> MongoPath<R>.eq(item: R) = add(Filters.eq(value, item))

  @MongoQueryDsl infix fun <R> MongoPath<R>.gt(item: R & Any) = add(Filters.gt(value, item))

  @MongoQueryDsl infix fun <R> MongoPath<R>.gte(item: R & Any) = add(Filters.gte(value, item))

  @MongoQueryDsl infix fun <R> MongoPath<R>.isIn(items: Iterable<R>) = add(Filters.`in`(value, items))

  @MongoQueryDsl infix fun <R> MongoPath<R>.isNotIn(items: Iterable<R>) = add(Filters.nin(value, items))

  @MongoQueryDsl infix fun <R> MongoPath<R>.lt(item: R & Any) = add(Filters.lt(value, item))

  @MongoQueryDsl infix fun <R> MongoPath<R>.lte(item: R & Any) = add(Filters.lte(value, item))

  @MongoQueryDsl infix fun <R> MongoPath<R>.ne(item: R) = add(Filters.ne(value, item))
}
