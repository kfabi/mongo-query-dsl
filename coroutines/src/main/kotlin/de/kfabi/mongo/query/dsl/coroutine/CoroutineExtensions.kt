package de.kfabi.mongo.query.dsl.coroutine

import com.mongodb.client.model.DeleteOptions
import com.mongodb.client.model.FindOneAndDeleteOptions
import com.mongodb.client.model.FindOneAndReplaceOptions
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import com.mongodb.kotlin.client.coroutine.ClientSession
import com.mongodb.kotlin.client.coroutine.FindFlow
import com.mongodb.kotlin.client.coroutine.MongoCollection
import de.kfabi.mongo.query.dsl.*


@MongoQueryDsl
fun <T : Any> MongoCollection<T>.find(filter: MongoQueryScope<T>.() -> Unit): FindFlow<T> = find(mongoQuery(filter))

@MongoQueryDsl
fun <T : Any> MongoCollection<T>.find(
    clientSession: ClientSession,
    filter: MongoQueryScope<T>.() -> Unit
): FindFlow<T> = find(clientSession, mongoQuery(filter))

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.updateMany(
    filter: MongoQueryScope<T>.() -> Unit,
    update: MongoUpdateScope<T>.() -> Unit,
    options: UpdateOptions = UpdateOptions()
): UpdateResult =
    updateMany(mongoQuery(filter), mongoUpdate(update), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.updateMany(
    clientSession: ClientSession,
    filter: MongoQueryScope<T>.() -> Unit,
    update: MongoUpdateScope<T>.() -> Unit,
    options: UpdateOptions = UpdateOptions()
): UpdateResult =
    updateMany(clientSession, mongoQuery(filter), mongoUpdate(update), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.updateOne(
    filter: MongoQueryScope<T>.() -> Unit,
    update: MongoUpdateScope<T>.() -> Unit,
    options: UpdateOptions = UpdateOptions()
): UpdateResult =
    updateOne(mongoQuery(filter), mongoUpdate(update), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.updateOne(
    clientSession: ClientSession,
    filter: MongoQueryScope<T>.() -> Unit,
    update: MongoUpdateScope<T>.() -> Unit,
    options: UpdateOptions = UpdateOptions()
): UpdateResult =
    updateOne(clientSession, mongoQuery(filter), mongoUpdate(update), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.deleteMany(
    filter: MongoQueryScope<T>.() -> Unit,
    options: DeleteOptions = DeleteOptions()
): DeleteResult =
    deleteMany(mongoQuery(filter), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.deleteMany(
    clientSession: ClientSession,
    filter: MongoQueryScope<T>.() -> Unit,
    options: DeleteOptions = DeleteOptions()
): DeleteResult =
    deleteMany(clientSession, mongoQuery(filter), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.deleteOne(
    filter: MongoQueryScope<T>.() -> Unit,
    options: DeleteOptions = DeleteOptions()
): DeleteResult =
    deleteOne(mongoQuery(filter), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.deleteOne(
    clientSession: ClientSession,
    filter: MongoQueryScope<T>.() -> Unit,
    options: DeleteOptions = DeleteOptions()
): DeleteResult =
    deleteOne(clientSession, mongoQuery(filter), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.findOneAndDelete(
    clientSession: ClientSession,
    filter: MongoQueryScope<T>.() -> Unit,
    options: FindOneAndDeleteOptions = FindOneAndDeleteOptions()
): T? =
    findOneAndDelete(clientSession, mongoQuery(filter), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.findOneAndDelete(
    filter: MongoQueryScope<T>.() -> Unit,
    options: FindOneAndDeleteOptions = FindOneAndDeleteOptions()
): T? =
    findOneAndDelete(mongoQuery(filter), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.findOneAndUpdate(
    clientSession: ClientSession,
    filter: MongoQueryScope<T>.() -> Unit,
    update: MongoUpdateScope<T>.() -> Unit,
    options: FindOneAndUpdateOptions = FindOneAndUpdateOptions()
): T? =
    findOneAndUpdate(clientSession, mongoQuery(filter), mongoUpdate(update), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.findOneAndUpdate(
    filter: MongoQueryScope<T>.() -> Unit,
    update: MongoUpdateScope<T>.() -> Unit,
    options: FindOneAndUpdateOptions = FindOneAndUpdateOptions()
): T? =
    findOneAndUpdate(mongoQuery(filter), mongoUpdate(update), options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.findOneAndReplace(
    clientSession: ClientSession,
    filter: MongoQueryScope<T>.() -> Unit,
    replacement: T,
    options: FindOneAndReplaceOptions = FindOneAndReplaceOptions()
): T? =
    findOneAndReplace(clientSession, mongoQuery(filter), replacement, options)

@MongoQueryDsl
suspend fun <T : Any> MongoCollection<T>.findOneAndDelete(
    filter: MongoQueryScope<T>.() -> Unit,
    replacement: T,
    options: FindOneAndReplaceOptions = FindOneAndReplaceOptions()
): T? =
    findOneAndReplace(mongoQuery(filter), replacement, options)