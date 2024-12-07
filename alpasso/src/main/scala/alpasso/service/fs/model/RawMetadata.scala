package alpasso.service.fs.model

import cats.*
import cats.syntax.all.*

import alpasso.core.model.*

import io.circe.*
import io.circe.syntax.*

opaque type RawMetadata = Map[String, String]

object RawMetadata:

  def fromString(raw: String): Either[Exception, RawMetadata] =
    if raw.nonEmpty then parser.parse(raw).flatMap(_.as[Map[String, String]])
    else RawMetadata.empty.asRight

  extension (m: RawMetadata)
    def rawString: String = Printer.noSpaces.print(m.asJson)

    def into(): SecretMetadata =
      SecretMetadata.from(m)

  given Show[RawMetadata] =
    Show.show(v => v.toList.map((a, b) => s"$a=$b").mkString(","))

  def of(kv: Map[String, String]): RawMetadata = kv

  val empty: RawMetadata = Map.empty