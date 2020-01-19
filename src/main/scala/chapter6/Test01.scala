package chapter6

object Test01 {

  def sum0(number: Int): Unit = {
    var result = 0
    for (i <- 1 to number) {
      result += i
    }
    result
  }

  def sum1(number: Int, codeBlock : Int => Int) = {
    var result = 0
    for (i <- 1 to number) {
      result += codeBlock(i)
    }
    result
  }
  //相比于sum0，sum1似乎更加笨重，但是它却是可扩展的，这是使用高阶函数实现中间层的好处之一。
  sum1(10, i => i)
  sum1(10, i => if (i % 2 == 0) i else 0)

  def printValue(generator: () => Int): Unit = {
    print(s"value is ${generator}")
  }
  printValue(() => 11)

  def inject(arr: Array[Int], initial: Int, operation: (Int, Int) => Int) = {
    var carryOver = initial
    arr.foreach( element => carryOver = operation(carryOver, element))
    carryOver
  }
  val array = Array(1,2,3,4,5,6)
  val sum = inject(array, 0, (carry, elem) => carry + elem)
  val max = inject(array, 0, (carry , elem) => Math.max(carry, elem))
  println(s"value is ${sum}")

  //像这样的inject方法，在scala中我们不必去亲自实现，Scala标准库中已经内置了这种方法：foldLeft(),foldLeft还有一个等效的/:操作符,被deprecated了。
  val sum1 = array.foldLeft(0) {(sum, elem) => sum + elem}
  val sum2 = (0 /: array) ((sum, elem) => sum + elem)
  //foldLeft的这种用法(函数值放到了大括号中)，涉及到了另一个概念：柯里化。

  def foo(a: Int)(b: Int)(c: Int) = {}
  /*
  Scala中的柯里化会把接收多个参数的函数转化为接收多个参数列表的函数。如foo函数，它其实经过了一系列的转换：
  Int => (Int => (Int => Unit))
  链路中的每一个函数都接收一个Int参数，并返回一个部分应用函数，最后一个是例外，它会返回一个Unit。
   */
  //foo _创建了一个部分应用函数：含有一个或多个未绑定参数的函数。
  foo _

  //使用柯里化重写之前的inject()方法：

  def inject1(arr: Array[Int], initial: Int)(operation: (Int, Int) => Int): Int = {
    var carryOver = initial
    arr.foreach(element => carryOver = operation(carryOver, element))
    carryOver
  }
  //这时候，就可以用更美观的大括号来调用这个方法
  val sum3 = inject1(array, 0) {(carry, elem) => carry + elem}

  //下划线可以表示一个函数值的参数,用法如下：
  val total = (0 /: array) { _ + _ }
  val largest = (Integer.MIN_VALUE /: array) {Math.max(_, _)}
  val largest1 = (Integer.MIN_VALUE /: array) {Math.max _}
  val largest2 = (Integer.MIN_VALUE /: array) {Math.max}





  def main(args: Array[String]): Unit = {

  }
}

