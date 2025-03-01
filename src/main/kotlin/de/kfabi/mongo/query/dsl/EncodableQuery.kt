package de.kfabi.mongo.query.dsl

import org.bson.BsonDocument
import org.bson.BsonDocumentWriter
import org.bson.codecs.Encoder
import org.bson.codecs.EncoderContext
import org.bson.codecs.configuration.CodecRegistry
import org.bson.conversions.Bson

internal data class EncodableQuery(val key: String, val encodeValue: EncodeScope.() -> Unit)

internal class EncodeScope(val writer: BsonDocumentWriter, val codecRegistry: CodecRegistry)

internal fun <T> EncodeScope.encodeArray(items: Iterable<T>) {
  writer.writeStartArray()
  for (item in items) {
    encodeValue(item)
  }
  writer.writeEndArray()
}

internal fun <T> EncodeScope.encodeValue(value: T?) {
  when (value) {
    null -> {
      writer.writeNull()
    }

    is Bson -> {
      codecRegistry
        .get(BsonDocument::class.java)
        .encode(
          writer,
          (value as Bson).toBsonDocument(BsonDocument::class.java, codecRegistry),
          EncoderContext.builder().build(),
        )
    }

    else -> {
      (codecRegistry[value.javaClass] as Encoder<T>).encode(
        writer,
        value,
        EncoderContext.builder().build(),
      )
    }
  }
}

internal class QueryBson(private val queries: List<EncodableQuery>) : Bson {
  override fun <T : Any?> toBsonDocument(
    clazz: Class<T>,
    codecRegistry: CodecRegistry,
  ): BsonDocument =
    BsonDocumentWriter(BsonDocument())
      .apply {
        val encodeScope = EncodeScope(this, codecRegistry)
        writeStartDocument()
        for (query in queries) {
          writeName(query.key)
          query.encodeValue(encodeScope)
        }
        writeEndDocument()
      }
      .document
}
