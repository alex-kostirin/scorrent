package info.scorrent.client.bencode

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class BencodeDecoderTest extends AnyFlatSpec with Matchers {

  "BencodeDecoder" must "decode int" in {
    BencodeDecoder.decode("i42e".getBytes) must be(Some(42))
    BencodeDecoder.decode("i-42e".getBytes) must be(Some(-42))
  }

  it must "decode string" in {
    BencodeDecoder.decode("4:spam".getBytes).getOrElse(None) must be("spam".getBytes)
  }

  it must "decode list" in {
    val res = BencodeDecoder.decode("l4:spami42ee".getBytes).getOrElse(List(None, None)).asInstanceOf[List[Any]]
    res.head must be("spam".getBytes)
    res(1) must be(42)
  }

  it must "decode map" in {
    val res = BencodeDecoder.decode("d3:bar4:spam3:fooi42ee".getBytes).getOrElse(Map()).asInstanceOf[Map[String, Any]]
    res("bar") must be("spam".getBytes)
    res("foo") must be(42)
  }
}