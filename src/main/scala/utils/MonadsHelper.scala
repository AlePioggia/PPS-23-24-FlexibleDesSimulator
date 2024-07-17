package utils

def either[T](condition: => Boolean, successValue: => T, errorMessage: String): Either[String, T] =
    if condition then Right(successValue) else Left(errorMessage)