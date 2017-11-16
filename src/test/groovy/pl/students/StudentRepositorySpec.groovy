package pl.students

import spock.lang.Specification

class StudentRepositorySpec extends Specification {

    def "should save entity"() {
        given:
        ArrayList<Student> list = new ArrayList<Student>()
        StudentRepository repository = new StudentRepository(list)
        Student student = new Student("jan", "kowalski")

        when:
        repository.save(student).subscribe()

        then:
        student.id != null
        list.contains(student)
    }

}
