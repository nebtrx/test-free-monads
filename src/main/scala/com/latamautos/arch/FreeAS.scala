package com.latamautos.arch

import cats.arrow.FunctionK

object FreeAS extends App {
  // type aliases
  type UserName = String
  type Image = String

  // DTO
  case class User( name: UserName, photo: Image )

  // Context
  sealed trait Context[A] {
    // just a sample of the kind of things you can do/store here
    lazy val appSettings = Map[String, String]()
  }

  // DSL Commands
  case class GetUserName(userId: Long) extends Context[UserName]
  case class GetUserPhoto(userId: Long) extends Context[Image]

  import cats.free.Free
  // Context Wrapped in a Free Monad or, Computational Context
  type LatamFutureReplacementMonad[A] =  Free[Context, A]


  import cats.free.Free.liftF

  // operations
  def getUserName(userId: Long): LatamFutureReplacementMonad[UserName] = liftF(GetUserName(userId))
  def getUserPhoto(userId: Long): LatamFutureReplacementMonad[Image] = liftF(GetUserPhoto(userId))


  def getUser(id: Long) = for {
    n <- getUserName( id )
    p <- getUserPhoto( id )
  } yield User( n, p )


  // Interpreters(they return monads)

  // ~> type operator: natural transformation
  import cats.{Id, ~>}
  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global

  object ContextPrinter extends FunctionK[Context, Id] {
    def apply[A](fa: Context[A]): Id[A] = fa match {

      case GetUserName(userId) =>
        println(s"GetUserName $userId...")
        "Mauricio"

      case GetUserPhoto(userId) =>
        println(s"GetUserPhoto...")
        " (°~°) "

    }
  }

  object FutureTransformer extends (Context ~> Future) {
    def apply[A](fa: Context[A]): Future[A] = fa match {
      case GetUserName(userId) => Future("Mauricio")
      case GetUserPhoto(userId) => Future(" (°~°) ")
    }
  }


  // cutoms DSL programs
  val computationResult: Id[User] = getUser(1).foldMap(ContextPrinter)
  println(computationResult)
}

