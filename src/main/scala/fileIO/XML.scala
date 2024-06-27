package fileIO

import scala.xml.XML
import scala.util.Try

class XML extends FileIO {

  override def read(filePath: String): Either[Throwable, String] = {
    Try {
      val xml = XML.loadFile(filePath)
      xml.toString()
    }.toEither
  }

  override def write(filePath: String, data: String): Either[Throwable, Unit] = {
    Try {
      val xml = XML.loadString(data)
      XML.save(filePath, xml)
    }.toEither
  }
}
