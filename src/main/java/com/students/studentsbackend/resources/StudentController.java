package com.students.studentsbackend.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.students.studentsbackend.dtos.StudentRequest;
import com.students.studentsbackend.dtos.StudentResponse;
import com.students.studentsbackend.entities.Student;
import com.students.studentsbackend.services.StudentService;

@RestController
@RequestMapping("students")
public class StudentController {
   
    @Autowired
    private StudentService service;

    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        var students = this.service.getStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        var student = this.service.getStudent(id);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        this.service.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<StudentResponse> save(@Validated @RequestBody StudentRequest student) {
        var savedStudent = this.service.save(student);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedStudent.id())
                .toUri();

        return ResponseEntity.created(location).body(savedStudent);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@RequestBody Student student,
                                       @PathVariable long id
    ){
        this.service.update(id, student);
        return ResponseEntity.ok().build();
    }

}
