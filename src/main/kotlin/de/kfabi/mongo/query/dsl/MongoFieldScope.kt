package de.kfabi.mongo.query.dsl

import kotlin.reflect.KProperty1

@JvmInline @Suppress("UNUSED") value class MongoPath<T> internal constructor(val value: String)

@MongoQueryDsl
abstract class MongoFieldScope<T> {
  @JvmName("propertyDiv")
  @MongoQueryDsl
  operator fun <R, A> KProperty1<T, R>.div(focus: KProperty1<R, A>): MongoPath<A> =
    MongoPath("$name.${focus.name}")

  @JvmName("pathDiv")
  @MongoQueryDsl
  operator fun <R, A> MongoPath<R>.div(focus: KProperty1<R, A>): MongoPath<A> =
    MongoPath("$value.${focus.name}")

  @JvmName("arrayPathDiv")
  @MongoQueryDsl
  operator fun <I : Iterable<R>, R, A> KProperty1<T, I>.div(focus: KProperty1<R, A>): MongoPath<A> =
    MongoPath("$name.${focus.name}")

  @JvmName("arrayPropertyDiv")
  @MongoQueryDsl
  operator fun <I : Iterable<R>, R, A> MongoPath<I>.div(focus: KProperty1<R, A>): MongoPath<A> =
    MongoPath("$value.${focus.name}")

  @MongoQueryDsl fun <R> field(field: KProperty1<T, R>) = MongoPath<R>(field.name)

  @MongoQueryDsl fun <R> field(field: MongoPath<R>) = MongoPath<R>(field.value)

  @MongoQueryDsl
  operator fun <I : Iterable<R>, R> MongoPath<I>.get(index: Int) = MongoPath<R>("$value.$index")

  @MongoQueryDsl
  operator fun <I : Iterable<R>, R> KProperty1<T, I>.get(index: Int) = MongoPath<R>("$name.$index")
}
