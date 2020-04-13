package info.scorrent.client.bencode

object Bencode {
  def encode(input: Any): Array[Byte] = BencodeEncoder.encode(input)

  def decode(input: Array[Byte]): Option[Any] = BencodeDecoder.decode(input)
}
