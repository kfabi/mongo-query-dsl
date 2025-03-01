package de.kfabi.mongo.query.dsl

import org.bson.conversions.Bson

@MongoQueryDsl
class MongoFieldQueryScope<T> {
  private val queries: MutableList<EncodableQuery> = mutableListOf()

  private fun add(query: EncodableQuery) {
    queries.add(query)
  }

  private fun addQuery(name: String, encodeValue: EncodeScope.() -> Unit = {}) {
    add(EncodableQuery(name, encodeValue))
  }

  private fun addQuery(name: String, item: T) {
    addQuery(name) { encodeValue(item) }
  }

  private fun addQuery(name: String, items: Iterable<T>) {
    addQuery(name) { encodeArray(items) }
  }

  internal fun asBson(): Bson = QueryBson(queries.toList())

  companion object {
    @MongoQueryDsl
    fun <T> MongoFieldQueryScope<T>.not(query: MongoFieldQueryScope<T>.() -> Unit) =
      addQuery("\$not") { encodeValue(MongoFieldQueryScope<T>().apply(query).asBson()) }

    @MongoQueryDsl fun <T> MongoFieldQueryScope<T>.eq(item: T) = addQuery("\$eq", item)

    @MongoQueryDsl fun <T> MongoFieldQueryScope<T>.gt(item: T & Any) = addQuery("\$gt", item)

    @MongoQueryDsl fun <T> MongoFieldQueryScope<T>.gte(item: T & Any) = addQuery("\$gte", item)

    @MongoQueryDsl
    fun <T> MongoFieldQueryScope<T>.isIn(items: Iterable<T>) = addQuery("\$in", items)

    @MongoQueryDsl fun <T> MongoFieldQueryScope<T>.lt(item: T & Any) = addQuery("\$lt", item)

    @MongoQueryDsl fun <T> MongoFieldQueryScope<T>.lte(item: T & Any) = addQuery("\$lte", item)

    @MongoQueryDsl fun <T> MongoFieldQueryScope<T>.ne(item: T) = addQuery("\$ne", item)

    @MongoQueryDsl
    fun <T> MongoFieldQueryScope<T>.isNotIn(items: Iterable<T>) = addQuery("\$nin", items)

    @MongoQueryDsl
    fun <I : Iterable<T>, T> MongoFieldQueryScope<I>.elemMatch(
      query: MongoFieldQueryScope<T>.() -> Unit
    ) = addQuery("\$elemMatch") { encodeValue(MongoFieldQueryScope<T>().apply(query).asBson()) }

    @MongoQueryDsl
    fun <I : Iterable<T>, T> MongoFieldQueryScope<I>.all(items: Iterable<T>) =
      addQuery("\$all") { encodeArray(items) }

    @MongoQueryDsl
    fun <I : Iterable<T>, T> MongoFieldQueryScope<I>.size(size: Int) =
      addQuery("\$size") { writer.writeInt32(size) }
  }
}
