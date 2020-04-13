package info.scorrent.client.bencode

import scala.util.parsing.combinator.{ImplicitConversions, Parsers}
import scala.util.parsing.input.CharArrayReader

object BencodeDecoder extends Parsers with ImplicitConversions {
  type Elem = Char

  def decode(input: Array[Byte]): Either[DecodeError, Any] = {
    val result = doc.apply(input)
    result match {
      case Success(v, _) => Right(v)
      case NoSuccess(v, _) => Left(DecodeError(v))
    }
  }

  protected def int: Parser[Long] =
    'i' ~> digits <~ 'e'

  protected def digits: BencodeDecoder.Parser[Long] =
    (rep1(digit) ^^ (x => x.mkString.toLong)) |
      ('-' ~> rep1(digit) ^^ (x => x.mkString.toLong * -1))

  protected def digit: Parser[Char] =
    elem("digit", c => c >= '0' && c <= '9')

  protected def bytes: Parser[Array[Byte]] =
    bytesLen >> (x => {
      repN(x.toInt, char)
    }) ^^ (x => x.map(_.toByte).toArray)

  protected def bytesLen: Parser[Long] =
    digits <~ ':'

  protected def char: Parser[Char] =
    elem("any char", _ => true)

  protected def list: Parser[List[Any]] =
    'l' ~> rep(doc) <~ 'e'

  protected def dict: Parser[Map[String, Any]] =
    'd' ~> rep1(keyValuePair) <~ 'e' ^^ (xs => Map(xs: _*)) | 'd' ~ 'e' ^^ {
      Map()
    }

  protected def keyValuePair: Parser[(String, Any)] =
    bytes ~ doc ^^ { case x ~ y => (x.map(_.toChar).mkString, y) }

  protected def doc: Parser[Any] =
    int | bytes | list | dict

  implicit def byteArrayToInput(in: Array[Byte]): Input =
    new CharArrayReader(in.map(_.toChar))
}
