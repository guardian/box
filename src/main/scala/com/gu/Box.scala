package com.gu

import java.util.concurrent.atomic.AtomicReference

import scala.concurrent.Future

abstract class Box[T] {
  def get(): T
  def apply(): T

  def send(t: T): Unit
  def send(f: T => T): Unit

  def alter(t: T): Future[T]
  def alter(f: T ⇒ T): Future[T]

  def map[A](f: T => A): Box[A]
}

object Box {
  def apply[T](initialValue: T): Box[T] = new AtomicRefBox[T](initialValue)
}

private class AtomicRefBox[T](t: T) extends Box[T] {
  private val ref: AtomicReference[T] = new AtomicReference[T](t)

  def apply(): T = ref.get()
  def get(): T = ref.get()

  def send(t: T): Unit = ref.set(t)
  def send(f: T => T): Unit = ref.updateAndGet(t => f(t))

  def alter(t: T): Future[T] = Future.successful(ref.updateAndGet(_ => t))
  def alter(f: T ⇒ T): Future[T] = Future.successful(ref.updateAndGet(t => f(t)))

  def map[A](f: T => A): Box[A] = new AtomicRefBox[A](f(get()))
}

