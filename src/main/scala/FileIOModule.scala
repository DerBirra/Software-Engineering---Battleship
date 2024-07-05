import com.google.inject.AbstractModule
import com.google.inject.name.Names
import fileIO.*

class FileIOModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[FileIO]).annotatedWith(Names.named("xml")).to(classOf[XML])
    bind(classOf[FileIO]).annotatedWith(Names.named("json")).to(classOf[JSON])
  }
}