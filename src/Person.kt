data class Person (val name: String, val salary: Int) {
    override fun toString(): String {
        return "Сотрудник[имя: $name, зарплата: $salary ₽]"
    }
}