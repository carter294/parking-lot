fun main() {
    var parking: Parking = Parking(0)
    var com: Array<String>
    do {
        com = readLine()!!.split(" ").toTypedArray()
        when(com[0]) {
            "create" -> parking = Parking(com[1].toInt())
            "leave" -> parking.leave(com[1].toInt())
            "park" -> parking.park(com)
            "status" -> parking.status()
            "reg_by_color" -> parking.findRegByColor(com[1])
            "spot_by_color" -> parking.findSpotByColor(com[1])
            "spot_by_reg" -> parking.findSpotByReg(com[1])
        }
    } while (com[0] != "exit")
}

class Parking(n: Int) {
    var spots: MutableList<Car?>

    init {
        spots = MutableList<Car?>(n) { null }
        if (n != 0)
            println("Created a parking lot with $n spots.")
    }

    fun isEmpty(): Boolean {
        for (i in spots)
            if (i != null)
                return false
        return true
    }

    fun spotsIsEmpty(): Boolean {
        if (spots.isEmpty()) {
            println("Sorry, a parking lot has not been created.")
            return false
        }
        return true
    }

    fun leave(com: Int) {
        if (!spotsIsEmpty()) return
        println(if (spots[com - 1] == null) "There is no car in spot ${com}." else "Spot ${com} is free.")
        spots[com - 1] = null
    }

    fun park(com: Array<String>) {
        var free = 0
        if (!spotsIsEmpty()) return
        while (free < spots.size && spots[free] != null)
            free++
        println(if (free == spots.size) "Sorry, the parking lot is full." else "${com[2]} car parked in spot ${free + 1}.")
        if (free < spots.size) spots[free] = Car(com[1], com[2].lowercase())
    }

    fun status() {
        if (!spotsIsEmpty()) return
        if (this.isEmpty())
            println("Parking lot is empty.")
        else {
            for (i in spots.indices)
                if (spots[i] != null)
                    println("${i + 1} ${spots[i]?.str} ${spots[i]?.clr}")
        }
    }

    fun findRegByColor(c: String) {
        if (!spotsIsEmpty()) return
        val cars = mutableListOf<String?>()
        for (i in spots.indices)
            if (spots[i]?.clr == c.lowercase())
                cars.add(spots[i]?.str)
        if (cars.size == 0)
            println("No cars with color $c were found.")
        else
            println(cars.joinToString())
    }

    fun findSpotByColor(c: String) {
        if (!spotsIsEmpty()) return
        val cars = mutableListOf<Int?>()
        for (i in spots.indices)
            if (spots[i]?.clr == c.lowercase())
                cars.add(i + 1)
        if (cars.size == 0)
            println("No cars with color $c were found.")
        else
            println(cars.joinToString())
    }

    fun findSpotByReg(reg: String) {
        if (!spotsIsEmpty()) return
        for (i in spots.indices)
            if (spots[i]?.str == reg) {
                println(i + 1)
                return
            }
        println("No cars with registration number $reg were found.")
    }

}

data class Car(val str: String, val clr: String)

/*
spot_by_color yellow
Sorry, a parking lot has not been created.
> create 4
Created a parking lot with 4 spots.
> park KA-01-HH-9999 White
White car parked in spot 1.
> park KA-01-HH-3672 White
White car parked in spot 2.
> park Rs-P-N-21 Red
Red car parked in spot 3.
> park Rs-P-N-22 Red
Red car parked in spot 4.
> reg_by_color GREEN
No cars with color GREEN were found.
> reg_by_color WHITE
KA-01-HH-9999, KA-01-HH-3672
> spot_by_color GREEN
No cars with color GREEN were found.
> spot_by_color red
3, 4
> spot_by_reg ABC
No cars with registration number ABC were found.
> spot_by_reg KA-01-HH-3672
2
> spot_by_reg Rs-P-N-21
3
> exit
*/