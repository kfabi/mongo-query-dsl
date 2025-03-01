package de.kfabi.mongo.query.dsl

import org.bson.conversions.Bson

@MongoQueryDsl
interface MongoQueryScope<T> : MongoFieldScope<T> {
  interface QueryCombiner<T> {
    operator fun get(vararg queries: MongoQueryScope<T>.() -> Unit)
  }

  @MongoQueryDsl val and: QueryCombiner<T>

  @MongoQueryDsl val or: QueryCombiner<T>

  @MongoQueryDsl val nor: QueryCombiner<T>

  @MongoQueryDsl infix fun <R> MongoField<R>.query(query: MongoFieldQueryScope<R>.() -> Unit)

  @MongoQueryDsl infix fun <R> MongoField<R>.matches(item: R)
}

internal class DefaultMongoQueryScope<T> : MongoQueryScope<T> {
  private val queries: MutableList<EncodableQuery> = mutableListOf()

  private inner class QueryCombiner(private val field: String) : MongoQueryScope.QueryCombiner<T> {
    override fun get(vararg queries: MongoQueryScope<T>.() -> Unit) {
      this@DefaultMongoQueryScope.queries.add(
        EncodableQuery(field) {
          encodeArray(queries.map { query -> DefaultMongoQueryScope<T>().apply(query).asBson() })
        }
      )
    }
  }

  override val and: MongoQueryScope.QueryCombiner<T> = QueryCombiner("\$and")
  override val or: MongoQueryScope.QueryCombiner<T> = QueryCombiner("\$or")
  override val nor: MongoQueryScope.QueryCombiner<T> = QueryCombiner("\$nor")

  override infix fun <R> MongoField<R>.query(query: MongoFieldQueryScope<R>.() -> Unit) {
    queries.add(
      EncodableQuery(value) { encodeValue(MongoFieldQueryScope<R>().apply(query).asBson()) }
    )
  }

  override infix fun <R> MongoField<R>.matches(item: R) {
    queries.add(EncodableQuery(value) { encodeValue(item) })
  }

  internal fun asBson(): Bson = QueryBson(queries.toList())
}
