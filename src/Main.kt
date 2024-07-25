import kotlinx.coroutines.*

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
suspend fun main() = coroutineScope {
    //TODO 1.	Написать класс Person с полями имени и зарплаты.
    // Создать список personList типа Person для их хранения.
    // Написать класс PersonManager, в котором будут функция добавления Person в список
    // addPerson(person: Person).
    // Создать Map<Person, Int> resultList.
    // Написать функцию addPassword(), которая будет добавлять в созданный словарь
    // Person и добавлять шестизначный пароль, сгенерированный в этой функции в
    // качестве значения. Эта функция должна добавлять данные элементы с задержкой в
    // 500L для имитации создания надежного пароля.
    // Написать функцию readDataPersonList(), выводящую данные созданного map в виде
    // ("Сотрудник: ${i.key}; пароль: ${i.value}" с временной задержкой в 1000L.
    // В главной функции программа запускается с приветствия: "Программа работы с базой данных сотрудников" и через секундную задержку предлагает выполнить порядок действий: "Добавить сотрудника:\n1.Да\n2.Нет". В цикле можно добавлять бесконечное количество сотрудников, вводя данные имени и зарплаты с консоли и используя функцию addPerson(person: Person). После добавления каждого сотрудника, предлагать продолжение работы или выход с чтением базы данных: "Добавить сотрудника:\n1.Да\n2.Прочитать базу данных"
    //
    //TODO В случае прекращения работы в функции main запускается корутина, которая
    // внутри выполняет функции добавления пароля сотруднику и вывода в консоль
    // получившегося в результате map. Эти функции описаны были выше.
    //
    //TODO Написать еще одну корутину, которая будет выполняться параллельно с той.
    // В ней указано условие отмены работы предыдущей корутины, это ввод с консоли
    // значения равного «0». Т.е. при вводе «0» добавление пароля и вывод данных
    // прекращаются и выходит сообщение "Завершение полной работы".
    println("1. Задание")
    val decoration = Decoration()

    val personList = listOf(
        Person("Александр", 100000),
        Person("Елена", 50000),
        Person("Сергей", 75000),
        Person("Полина", 120000),
        Person("Екатерина", 100000),
        Person("Александр", 12000),
        Person("Алла", 80000),
        Person("Анастасия", 25000),
        Person("Евгений", 180000),
        Person("Игорь", 20000)
    )

    val resultList = mapOf<Person, Int>()

    println("Программа работы с базой данных сотрудников\n")

    delay(1000L)

    println("Добавить сотрудника:\n1.Да\n2.Нет")

    val personManager = PersonManager(personList)

    val personOne = readln()

    var personPlus = personList

    val startTime = System.currentTimeMillis()
    val personConsoleOut =
        launch(Dispatchers.Default, start = CoroutineStart.LAZY) {
            personOut(resultList, personPlus)
        }

    val personConsoleNull =
        launch(Dispatchers.Default, start = CoroutineStart.LAZY) {
            var nextTime = startTime
            val readCancel = readln()
            when (readCancel) {
                "0" -> {
                    personConsoleOut.cancelAndJoin()
                    println("Завершение полной работы")
                }
            }
            while (isActive) {
                if (System.currentTimeMillis() >= nextTime) {
                    nextTime += 500L
                }
            }
        }

    fun personConsole(person: String) {
        if (person == "1") {
            while (true) {
                println("Введите имя сотрудника")
                val name = readln()

                println("Введите зарплату сотрудника")
                val salary = readln().toInt()

                personPlus = personManager.addPerson(name, salary)

                decoration.getLine("_")

                println("Добавить сотрудника:\n1.Да\n2.Прочитать базу данных")

                val personNew = readln()
                checkPerson(personNew)
                if (personNew == "2") {
                    break
                }
            }
        }
    }

    try {
        checkPerson(personOne)
        personConsole(personOne)
        personConsoleOut.start()
        personConsoleNull.start()
        personConsoleOut.join()
        delay(500L)
        personConsoleNull.cancelAndJoin()
        personConsoleOut.cancel()
        decoration.getLine("=")
    } catch (e: Exception) {
        println(e.message)
    }
}