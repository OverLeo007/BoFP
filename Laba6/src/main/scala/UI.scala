package ru.paskal.laba6

import rsa.{RSA, Utils}

import java.io.PrintWriter
import scala.annotation.tailrec
import scala.io.{Source, StdIn}

object UI extends MenuEnum {
  var rsa: Option[RSA] = None

  private var isRun = true

  def run(): Unit = {
    while (isRun) {

      println("1. Создание RSA из P Q")
      println("2. Создание RSA из существующих ключей")
      if (rsa.isDefined) {
        println("3. Зашифровать сообщение")
        println("4. Расшифровать сообщение")
        println("5. Зашифровать в файл")
        println("6. Расшифровать из файла")
      } else {
        println("(RSA не создан, недоступно) 3. Зашифровать сообщение")
        println("(RSA не создан, недоступно) 4. Расшифровать сообщение")
        println("(RSA не создан, недоступно) 5. Расшифровать из файла")
        println("(RSA не создан, недоступно) 6. Зашифровать в файл")
      }
      println("7. Выход")

      var choice: Int = -1

      try {
        choice = scala.io.StdIn.readInt()
      } catch {
        case e: NumberFormatException =>
      }

      choice match {
        case MAKE_FROM_PQ => makeRsaFromPqHandler()
        case MAKE_FROM_EXISTS => makeRsaFromExistsHandler()
        case ENCRYPT_CONSOLE if rsa.isDefined => encryptFromConsoleHandler()
        case DECRYPT_CONSOLE if rsa.isDefined => decryptFromConsoleHandler()
        case ENCRYPT_FILE if rsa.isDefined => encryptToFileHandler()
        case DECRYPT_FILE if rsa.isDefined => decryptFromFileHandler()
        case EXIT => isRun = false
        case _ => println("Некорректный ввод")
      }

    }
  }


  private def makeRsaFromPqHandler(): Unit = {
    val (p, q) = pickPQ()
    val rsa = RSA.makeFromPQ(p, q)
    println(s"Создан RSAEncoder: $rsa")
    this.rsa = Option.apply(rsa)
  }

  private def makeRsaFromExistsHandler(): Unit = {
    val n = Utils.readBigInt("Введите значение для N:")
    val e = Utils.readBigInt("Введите значение для E:")
    val d = Utils.readBigInt("Введите значение для D:")
    val rsa = new RSA(n, e, d)
    println(s"Создан RSAEncoder: $rsa")
    this.rsa = Option.apply(rsa)
  }

  private def encryptFromConsoleHandler(): Unit = {
    println("Введите сообщение:")
    val message = StdIn.readLine()
    println(s"Зашифрованное сообщение:\n ${rsa.get.encrypt(message)}")
  }

  private def decryptFromConsoleHandler(): Unit = {
    println("Введите сообщение:")
    val message = StdIn.readLine()
    try {

      println(s"Расшифрованное сообщение:\n ${rsa.get.decrypt(message)}")
    } catch
    case e: NumberFormatException => System.err.println(s"Ошибка: некорректный ввод ${e.getMessage}")
  }

  private def encryptToFileHandler(): Unit = {
    println("Введите имя файла:")
    val filePath = StdIn.readLine()
    println("Введите сообщение:")
    val message = StdIn.readLine()
    val enc = rsa.get.encrypt(message)
    val writer = new PrintWriter(filePath)
    try {
      writer.write(enc)
      println("Зашифрованное сообщение сохранено в файл")
    } catch {
      case e: Exception => System.err.println(e.getMessage)
    } finally {
      writer.close()
    }
  }

  private def decryptFromFileHandler(): Unit = {
    println("Введите имя файла:")
    val filePath = StdIn.readLine()
    val source = Source.fromFile(filePath)
    try {
      println(s"Расшифрованное сообщение:\n ${rsa.get.decrypt(source.mkString)}")
    } catch {
      case e: Exception => System.err.println(e.getMessage)
    } finally {
      source.close()
    }
  }

  private def pickPQ(): (BigInt, BigInt) = {
    val primes = pickPrimes()

    primes.zipWithIndex.foreach {
      case (prime, index) =>
        println(s"$index. $prime")
    }

    val p = primes(chooseIndex("Выберете индекс числа для p", primes.size))
    val q = primes(chooseIndex("Выберете индекс числа для q", primes.size))
    println(s"p: $p\nq: $q")
    (p, q)
  }

  @tailrec
  private def pickPrimes(): List[BigInt] = {
    val leftBound = Utils.readBigInt("Введите левую границу диапазона простых чисел:")
    val rightBound = Utils.readBigInt("Введите правую границу диапазона простых чисел:")
    if (rightBound < leftBound || leftBound < 1 || rightBound < 1) {
      System.err.println("Ошибка Некорректный ввод")
      pickPrimes()
    } else {
      val primes = Utils.primes(leftBound, rightBound)
      if (primes.isEmpty) {
        println("В диапазоне нет простых чисел")
        pickPrimes()
      } else {
        primes
      }
    }
  }

  @tailrec
  private def chooseIndex(prompt: String, listSize: Int): Int = {
    print(prompt)
    val index = StdIn.readInt()
    if (index < 0 || index >= listSize) {
      System.err.println("Ошибка: введите корректный индекс.")
      chooseIndex(prompt, listSize)
    } else {
      index
    }
  }


}
