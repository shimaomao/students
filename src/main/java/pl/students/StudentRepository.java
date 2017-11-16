package pl.students;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class StudentRepository {

    private List<Student> students = new ArrayList<>();

    public StudentRepository() {
    }

    public StudentRepository(List<Student> students) {
        this.students = students;
    }

    private void setUUIDasId(Student entity) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, UUID.randomUUID().toString());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Missing id field on " + entity);
        }
    }

    public Mono<Student> save(Student entity) {
        return students.stream()
                .filter(e -> e.getId().equals(entity.getId()))
                .findAny()
                .map(e -> {
                    int i = students.indexOf(e);
                    students.set(i, entity);
                    return Mono.just(entity);
                })
                .orElseGet(() -> Mono.just(entity)
                        .doOnNext(this::setUUIDasId)
                        .doOnNext(students::add));
    }

    public Mono<Student> findById(String aString) {
        return Flux.fromStream(students.stream())
                .filter(e -> e.getId().equals(aString))
                .singleOrEmpty();
    }

    public Flux<Student> findAll() {
        return Flux.fromStream(students.stream());
    }

    public Mono<Long> count() {
        return Mono.just((long) students.size());
    }

    public Mono<Void> delete(Student entity) {
        return Mono.create(sink -> {

            students.stream()
                    .filter(e -> entity.getId().equals(e.getId()))
                    .findFirst()
                    .ifPresent(e -> students.remove(entity));

            sink.success();
        });
    }

    public Mono<Void> deleteAll() {
        return Mono.create(sink -> {
            students.clear();

            sink.success();
        });
    }
}
