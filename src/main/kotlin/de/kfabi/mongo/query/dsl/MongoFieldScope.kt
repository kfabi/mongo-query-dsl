package de.kfabi.mongo.query.dsl

import kotlin.reflect.KProperty1

@JvmInline @Suppress("UNUSED") value class MongoField<T> internal constructor(val value: String)

@MongoQueryDsl
interface MongoFieldScope<T> {
  @MongoQueryDsl
  operator fun <R, A> KProperty1<T, R>.div(focus: KProperty1<R, A>): MongoField<A> =
    MongoField("$name.${focus.name}")

  @MongoQueryDsl
  operator fun <R, A> MongoField<R>.div(focus: KProperty1<R, A>): MongoField<A> =
    MongoField("$value.${focus.name}")

  @MongoQueryDsl fun <R> field(field: KProperty1<T, R>) = MongoField<R>(field.name)

  @MongoQueryDsl fun <R> field(field: MongoField<R>) = MongoField<R>(field.value)
}
