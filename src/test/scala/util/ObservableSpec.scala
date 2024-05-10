package util

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ObservableSpec extends AnyWordSpec {
    val observable = new Observable
    val observer1 = new ObserverMock
    val observer2 = new ObserverMock

    "Observable" should {

        "notify added observers" in {
        
        observable.add(observer1)
        observable.add(observer2)

        observable.notifyObservers

        observer1.updated shouldBe true
        observer2.updated shouldBe true
        }

        "not notify removed observers" in {
        val observable = new Observable
        val observer1 = new ObserverMock
        val observer2 = new ObserverMock

        observable.add(observer1)
        observable.add(observer2)
        observable.remove(observer2)

        observable.notifyObservers

        observer1.updated shouldBe true
        observer2.updated shouldBe false
        }

        "not notify when no observers are added" in {
        val observable = new Observable
        observable.notifyObservers // Should not throw any exceptions
        }
  }

    class ObserverMock extends Observer {
        var updated: Boolean = false

        override def update: Unit = updated = true
  }
}
