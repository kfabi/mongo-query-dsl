package de.kfabi.mongo.query.dsl

import kotlin.reflect.KProperty1

/** Represents a path to a field in a Document. */
@JvmInline @Suppress("UNUSED") value class MongoPath<T> internal constructor(val value: String)

/** DSL Scope for constructing [MongoPath]s of Documents of type [T]. */
@MongoQueryDsl
abstract class MongoPathScope<T> {
  /** Creates a nested [MongoPath] that leads to the nested [field]. */
  @JvmName("propertyDiv")
  @MongoQueryDsl
  operator fun <R, A> KProperty1<T, R>.div(focus: KProperty1<R, A>): MongoPath<A> =
    MongoPath("$name.${focus.name}")

  /** Creates a nested [MongoPath] that leads to the nested [field]. */
  @JvmName("pathDiv")
  @MongoQueryDsl
  operator fun <R, A> MongoPath<R>.div(focus: KProperty1<R, A>): MongoPath<A> =
    MongoPath("$value.${focus.name}")

  /** Creates a nested [MongoPath] for array fields that leads to the [field]. */
  @JvmName("arrayPathDiv")
  @MongoQueryDsl
  operator fun <I : Iterable<R>, R, A> KProperty1<T, I>.div(focus: KProperty1<R, A>): MongoPath<A> =
    MongoPath("$name.${focus.name}")

  /** Creates a nested [MongoPath] for array fields that leads to the field [field]. */
  @JvmName("arrayPropertyDiv")
  @MongoQueryDsl
  operator fun <I : Iterable<R>, R, A> MongoPath<I>.div(focus: KProperty1<R, A>): MongoPath<A> =
    MongoPath("$value.${focus.name}")

  /** Creates a [MongoPath] that leads to the [field]. */
  @MongoQueryDsl fun <R> field(field: KProperty1<T, R>) = MongoPath<R>(field.name)

  /** Creates a [MongoPath] that leads to the [field]. */
  @MongoQueryDsl fun <R> field(field: MongoPath<R>) = MongoPath<R>(field.value)

  /** Creates a nested [MongoPath] that leads to the array element at [index]. */
  @MongoQueryDsl
  operator fun <I : Iterable<R>, R> MongoPath<I>.get(index: Int) = MongoPath<R>("$value.$index")

  /** Creates a nested [MongoPath] that leads to the array element at [index]. */
  @MongoQueryDsl
  operator fun <I : Iterable<R>, R> KProperty1<T, I>.get(index: Int) = MongoPath<R>("$name.$index")
}
