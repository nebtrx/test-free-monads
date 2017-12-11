package com.latamautos.arch

import org.scalatest._

class HelloSpec extends FlatSpec with Matchers {
  "The Hello object" should "say hello" in {
    FreeAS.greeting shouldEqual "hello"
  }
}
