package info.scorrent.client.bencode

import scala.collection.{Map, Seq}


object BencodeEncoder {

  private case class StringKeyMap(value: Map[String, _])

  def encode(input: Any): Array[Byte] =
    input match {
      case i: Byte => int(i)
      case i: Short => int(i)
      case i: Int => int(i)
      case i: Long => int(i)
      case i: String => bytes(i)
      case i: Array[Byte] => bytes(i)
      case i: Seq[_] => list(i)
      case i: Map[String, _]@unchecked => map(i) // FIXME: handle type erasure
      case _ => Array()
    }

  protected def int(input: Number): Array[Byte] =
    ("i" + input + "e").getBytes

  protected def bytes(input: Array[Byte]): Array[Byte] =
    (input.length + ":").getBytes ++ input


  protected def list(input: Seq[_]): Array[Byte] =
    'l'.toByte +: input.map(x => encode(x)).reduce(_ ++ _) :+ 'e'.toByte

  protected def map(input: Map[String, _]): Array[Byte] =
    'd'.toByte +: input.toList.sortWith(_._1 < _._1).map(x => encode(x._1) ++ encode(x._2)).reduce(_ ++ _) :+ 'e'.toByte

  implicit def stringToByteArray(s: String): Array[Byte] =
    s.getBytes

  implicit def byteListToByteArray(l: List[Byte]): Array[Byte] =
    l.toArray
}
