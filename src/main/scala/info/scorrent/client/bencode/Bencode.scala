package info.scorrent.client.bencode

object Bencode {
  def encode(input: Any): Array[Byte] = BencodeEncoder.encode(input)

  def decode(input: Array[Byte]): Either[DecodeError, Any] = BencodeDecoder.decode(input)
}
