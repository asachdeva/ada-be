package model

import cats.*
import cats.effect.*
import cats.implicits.*

import io.circe.{Decoder, Encoder}

import org.http4s.ParseFailure

opaque type SearchQuery <: String = String
object SearchQuery {
  def parseSearchQuery(query: String): Either[ParseFailure, SearchQuery] = Option(query)
    .filter(!_.isBlank)
    .toRight(ParseFailure("The given query is invalid", s"The given query: $query cannot be parsed as a string"))
}

 // implicit encoders/decoders for request params
given encodeSearchQuery: Encoder[SearchQuery] = Encoder.encodeString.contramap[SearchQuery](it => it)
given decodeSearchQuery: Decoder[SearchQuery] = Decoder.decodeString.emapTry(query => SearchQuery.parseSearchQuery(query).toTry)

 // ADT -- DB Model
case class Answer(id: Int, title: String)
case class Block(id: Int, content: String, answer_id: Int)
case class Message(id: Int, body: String)
case class State(id: String, value: String)
