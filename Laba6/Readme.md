# Лабораторная работа №6

## Пакеты и приложения

### Задание

Реализовать на Scala алгоритм RSA и
вспомогательные функции для чисел любой размерности и
оформить их в отдельный пакет:
 -  def isPrime (n: BigInt) - возвращает true, если число n простое
и false, если составное
- def primes(n: BigInt, m: BigInt) - возвращает список (List) всех
простых чисел от n до m включительно
- def randomPrime(bits: Int) - возвращает случайное простое
число BigInt с длиной в битах: bits
- def encrypt(s: String, e: BigInt, n: BigInt) - возвращает
зашифрованную строку s открытым ключом (e, n); результатом
должен быть массив приведенный к строке:
123456,234567,345678,...
- def decrypt(s: String, d: BigInt, n: BigInt) - расшифровывает s
секретным ключом (d, n); результатом должна быть исходная
строка до ее шифрования

Написать с использованием данного пакета утилиту (по выбору:
либо для командной строки, либо GUI c использованием Spring),
позволяющую решать следующие задачи:
- генерировать диапазон простых чисел и позволять их
выбирать из списка
- генерировать ключи RSA, основанные на простых числах
любой размерности
- разделять открытый и секретный ключи RSA (хранить их
раздельно)
- вводить (или вставлять из буфера) сообщение для
шифрования
- шифровать сообщение открытым ключом
- отображать зашифрованное сообщение в виде текста или
сохранять зашифрованное сообщение в файле для
возможности его передачи
- прочитать зашифрованное сообщение из файла при его
получении
- расшифровывать полученное сообщение секретным ключом
