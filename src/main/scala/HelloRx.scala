import rx.lang.scala._
import rx.lang.scala.schedulers._

import scala.concurrent.duration.Duration
import scala.concurrent.duration.DurationInt
import scala.concurrent.duration.DurationLong
import scala.language.postfixOps
import scala.language.implicitConversions

trait SimpleObservables {

  val fromIterable = Observable.from(List(1,2,3,4,5))
  val mapped = fromIterable map { i => 2 * i}
  val flatMapped = fromIterable flatMap { i => Observable.from(1 to i) }
  val concatted = (fromIterable map { i => Observable.from(1 to i) }).concat

}

trait TimedObservables {

  val simpleInterval: Observable[Long] = Observable.interval(1 seconds)

  val interval2: Observable[Long] = Observable.interval(4 seconds)

  val simpleMapped: Observable[Long] = interval2.map { i => Observable.interval(1 seconds).map{x => (x + 1) * i}}.switch

}

object HelloRx extends App with TimedObservables {

  println("Whappen! Greetings Lion!")


//  fromIterable.subscribe{ i => println(i) }
//  println("watch out!")

  simpleMapped.toBlockingObservable.foreach { i => println(i) }

}