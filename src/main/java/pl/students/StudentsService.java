package pl.students;

import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentsService {

    private static List<Student> students = new ArrayList<>();

    public Student add(Student student) {
        students = Stream.concat(students.stream(), Stream.of(student)).collect(Collectors.toList());
        return student;
    }

    public void remove(String id) {
        this.remove(this.getOne(id));
    }

    private void remove(Student student) {
        students = students.stream().filter(s -> !Objects.equals(s.getId(), student.getId())).collect(Collectors.toList());
    }

    public Student update(String id, Student student) {
        students = students.stream().peek(s -> {
            if (Objects.equals(s.getId(), id)) {
                s.setName(student.getName());
                s.setLastName(student.getLastName());
            }
        }).collect(Collectors.toList());
        return student;
    }

    public List<Student> getAll() {
        return students;
    }

    public Student getOne(String id) {
        return students.stream().filter(s -> Objects.equals(s.getId(), id)).findFirst().get();
    }

}
