package info.scorrent.client.bencode

import java.nio.file.{Files, Paths}

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class BencodeTest extends AnyFlatSpec with Matchers {

  private val sample = Files.readAllBytes(Paths.get(getClass.getResource("/test.torrent").getPath))

  "Bencode" must "decode sample" in {
    Bencode.decode(sample) must be(a[Right[_,_]])
  }
  "Bencode" must "encode decoded sample" in {
    Bencode.encode(Bencode.decode(sample).getOrElse(None)) must be(sample)
  }
}
