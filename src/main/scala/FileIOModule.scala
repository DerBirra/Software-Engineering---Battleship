import com.google.inject.AbstractModule
import com.google.inject.name.Names

class FileIOModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[FileIO]).annotatedWith(Names.named("xml")).to(classOf[XMLFileIO])
    bind(classOf[FileIO]).annotatedWith(Names.named("json")).to(classOf[JSONFileIO])
  }
}