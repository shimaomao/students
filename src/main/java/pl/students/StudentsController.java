package pl.students;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentsController {

    private StudentsService studentsService;

    public StudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping("/students")
    public List<Student> getAll() {
        return this.studentsService.getAll();
    }

    @GetMapping("/students/{id}")
    public Student getOne(@PathVariable String id) {
        return this.studentsService.getOne(id);
    }

    @PostMapping("/students")
    public Student add(@RequestBody Student student) {
        return this.studentsService.add(student);
    }

    @PutMapping("/students/{id}")
    public Student update(@PathVariable String id, @RequestBody Student student) {
        return this.studentsService.update(id, student);
    }

    @DeleteMapping("/students/{id}")
    public void remove(@PathVariable String id) {
        this.studentsService.remove(id);
    }

}
