
import kotlinx.coroutines.delay

fun passwordGenerate(): Int {
    val firstPasswordChar = ('1'..'9').random()
    val passwordSecond = (0..4).map { ('0'..'9').random() }.toMutableList()
    passwordSecond.add(0, firstPasswordChar)
    val password = passwordSecond.toList().fold("") { a, b -> "$a$b" }.toInt()
    return password
}

fun password(func: () -> Int): Int {
    return func()
}

suspend fun addPassword(map: Map<Person, Int>, list: List<Person>): Map<Person, Int> {
    val mapOut = map.toMutableMap()
    for (i in list.indices) {
        delay(500L)
        mapOut[list[i]] = password(::passwordGenerate)
    }
    return mapOut
}

suspend fun readDataPersonList(map: Map<Person, Int>) {
    var k = 0
    for (i in map) {
        delay(1000L)
        println("Сотрудник: ${i.key}; пароль: ${i.value}")
        k++
        if (k == map.size) break
    }
}

fun checkPerson(personChoice: String): String {
    val checkReadln: Boolean = personChoice == "1" || personChoice == "2" || personChoice == "0"

    if (!checkReadln) {
        throw Exception(
            "Введено недопустимое значение"
        )
    }
    return personChoice
}

suspend fun personOut(map: Map<Person, Int>, list: List<Person>) {
    readDataPersonList(addPassword(map,list))
}