class PersonManager(private val personList: List<Person>) {
    fun addPerson(name: String, salary: Int): List<Person>{
        val personOne = Person (name, salary)
        val personListOut = personList.toMutableList()
        personListOut.add(personOne)
        return personListOut
    }
}