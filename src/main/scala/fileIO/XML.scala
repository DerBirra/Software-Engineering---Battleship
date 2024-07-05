package fileIO

import scala.xml.XML
import scala.xml.Elem
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

  def writeGameField(filePath: String, gameField: String): Either[Throwable, Unit] = {
    val xmlData = gameFieldToXML(gameField)
    write(filePath, xmlData.toString())
  }

  private def gameFieldToXML(gameField: String): Elem = {
    <gamefield>
      {
        gameField.map { row =>
          <row>{row}</row>
        }
      }
    </gamefield>
  }
}
