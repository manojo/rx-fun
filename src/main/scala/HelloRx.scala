import rx.lang.scala._
import rx.lang.scala.schedulers._

import scala.concurrent.duration.Duration
import scala.concurrent.duration.DurationInt
import scala.concurrent.duration.DurationLong
import scala.language.postfixOps
import scala.language.implicitConversions

import scala.collection.Iterator

trait SimpleObservables {

  val fromIterable = Observable.from(List(1,2,3,4,5))
  val mapped = fromIterable map { i => 2 * i}
  val flatMapped = fromIterable flatMap { i => Observable.from(1 to i) }
  val concatted = (fromIterable map { i => Observable.from(1 to i) }).concat

}

trait TimedObservables {

  val simpleInterval: Observable[Long] = Observable.interval(1 seconds).doOnNext(x => println("in simple"))

  val interval2: Observable[Long] = Observable.interval(4 seconds).doOnNext(x => println("in interval2"))

  val simpleMapped: Observable[Long] = interval2.map { i => Observable.interval(1 seconds).map{x => (x + 1) * i}}.switch.doOnNext(x => println("in simpleMapped"))

  //inspired by stuff in RxScalademo:
  // https://github.com/Netflix/RxJava/blob/master/language-adaptors/rxjava-scala/src/examples/scala/rx/lang/scala/examples/RxScalaDemo.scala
  val simpleIterator: Observable[Int] = Observable { subscriber =>
    var i = 0
    while(!subscriber.isUnsubscribed) {
      subscriber.onNext{ println("whappen deh: "+ i); i }
      i += 1
    }

    if(subscriber.isUnsubscribed){ subscriber.onCompleted() }
  }


}

object HelloRx extends App with TimedObservables {

  println("Whappen! Greetings Lion!")

  //simpleIterator.take(10).toBlockingObservable.foreach { i => {println(i)} }
  simpleIterator.take(10).toBlockingObservable.foreach { i => println(i) }


//  fromIterable.subscribe{ i => println(i) }
//  println("watch out!")

//  simpleMapped.take(10).toBlockingObservable.foreach { i => println(i) }



}