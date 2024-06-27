package fileIO

import play.api.libs.json._
import scala.util.Try
import java.nio.file.{Files, Paths}

class JSON extends FileIO {

  override def read(filePath: String): Either[Throwable, String] = {
    Try {
      val source = scala.io.Source.fromFile(filePath)
      try source.mkString finally source.close()
    }.toEither
  }

  override def write(filePath: String, data: String): Either[Throwable, Unit] = {
    Try {
      Files.write(Paths.get(filePath), data.getBytes)
    }.toEither
  }
}