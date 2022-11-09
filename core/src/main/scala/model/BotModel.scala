package model

import cats.*
import cats.data.*
import cats.effect.*
import cats.implicits.*

import io.circe.generic.semiauto.*
import io.circe.{Decoder, Encoder}

import org.http4s.ParseFailure

case class SearchQuery(query: String)

// ADT -- DB Model
case class Answer(id: Int, title: String)
given encodeAnswer: Encoder[Answer] = deriveEncoder
given decodeAnswer: Decoder[Answer] = deriveDecoder

case class FullAnswer(id: Int, blockId: Int, title: String, content: String)

given encodeFullAnswer: Encoder[FullAnswer] = deriveEncoder
given decodeFullAnswer: Decoder[FullAnswer] = deriveDecoder
case class Message(id: Int, body: Option[String])
case class State(id: String, value: Option[String])
