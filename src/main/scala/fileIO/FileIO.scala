package fileIO

trait FileIO {

    def read(filePath: String): Either[Throwable, String]
    def write(filePath: String, data: String): Either[Throwable, Unit]
    def writeGameField(filePath: String, gameBoard: String) : Either[Throwable, Unit]
  
}
