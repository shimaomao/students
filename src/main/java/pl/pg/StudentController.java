package pl.pg;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping()
    public Flux<Student> getAll() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Student>> getById(@PathVariable String id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Mono<Student> save(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Student>> update(@PathVariable String id,
                                                @RequestBody Student student) {
        return studentRepository.findById(id)
                .flatMap(e -> {
                    e.setFirstName(student.getFirstName());
                    e.setLastName(student.getLastName());
                    return studentRepository.save(e);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<Student>> updatePartially(@PathVariable String id,
                                                         @RequestBody Student student) {
        return studentRepository.findById(id)
                .flatMap(e -> {
                    Optional.ofNullable(student.getFirstName()).ifPresent(e::setFirstName);
                    Optional.ofNullable(student.getLastName()).ifPresent(e::setLastName);
                    return studentRepository.save(e);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return studentRepository.findById(id)
                .flatMap(e ->
                        studentRepository.delete(e)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
