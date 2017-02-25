
import java.util.{Date, Locale}
import java.text.DateFormat
import java.text.DateFormat._
//import ShellExecutor

object Test {
    def oncePerSecond(callback: () => Unit) {
        while(true) {
            callback()
            Thread sleep 1000
        }
    }

    def printOnce() {
        println("print once")
    }

    def main (args : Array[String]) {
        println("Good result!")
       
        //val se = new ShellExecutor()
        //se.showCmd("ls -l")

        //ShellExecutor.executeCommand("ls -l /var/tmp")
        //val now = new Date
        //val df = getDateInstance(LONG, Locale.FRANCE)
        //println(df format now)

        //oncePerSecond(printOnce)
        oncePerSecond(() => {
            println("anno")
            println("anno2")
        })

        //val c = new Complex(1.3, 3.2)
        //println(c.re, c.im)

        //val d1 = new MyDate(3,3,3)
        //val d2 = new MyDate(3,3,1)
        //if (d1 < d2) {
        //    println("yes, d1 is smaller")
        //} else {
        //    println("no, d1 is bigger")
        //}
    }
}

class Complex(real:Double, imaginary:Double) {
    def re = real
    def im = imaginary
}

trait Ord {
    def <  (that : Any) : Boolean
    def <= (that : Any) : Boolean = (this == that) || (this < that)
    def >  (that : Any) : Boolean = !(this <= that)
    def >= (that : Any) : Boolean = !(this < that)
}

class MyDate(y: Int, m: Int, d: Int) extends Ord{
    def year = y
    def month = m
    def day = d

    override def toString() : String = year + "-" + month + "-" + day
    override def equals(that: Any) : Boolean = 
        that.isInstanceOf[MyDate] && {
            val o = that.asInstanceOf[MyDate]
            o.year == year && o.month == month && o.day == day 
        }

    def <  (that : Any) : Boolean = {
        if (!that.isInstanceOf[MyDate]) 
            error("Cannot compare with a different type")
        val o = that.asInstanceOf[MyDate]
        (o.year > year) || (o.year == year && o.month > month) || (o.year == year && o.month == month && o.day > day) 
    }
}
