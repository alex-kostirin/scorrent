package info.scorrent.client.bencode

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class BencodeEncoderTest extends AnyFlatSpec with Matchers {

  "BencodeEncoder" must "encode int" in {
    BencodeEncoder.encode(42) must be("i42e".getBytes)
    BencodeEncoder.encode(-42) must be("i-42e".getBytes)
  }

  it must "encode string" in {
    BencodeEncoder.encode("spam") must be("4:spam".getBytes)
  }

  it must "encode list" in {
    BencodeEncoder.encode(List("spam", 42)) must be("l4:spami42ee".getBytes)
  }

  it must "encode map" in {
    BencodeEncoder.encode(Map("bar" -> "spam", "foo" -> 42)) must be("d3:bar4:spam3:fooi42ee".getBytes)
  }
}