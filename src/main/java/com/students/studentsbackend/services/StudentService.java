package com.students.studentsbackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.studentsbackend.dtos.StudentRequest;
import com.students.studentsbackend.dtos.StudentResponse;
import com.students.studentsbackend.entities.Student;
import com.students.studentsbackend.mappers.StudentMapper;
import com.students.studentsbackend.repositories.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
@Service
public class StudentService {
    
    @Autowired
    private StudentRepository repository;

    public List<Student> getStudents(){
        return this.repository.findAll();
    }

    public Student getStudent(long id){
        return this.repository
                   .findById(id)
                   .orElseThrow(
                     () -> new EntityNotFoundException("Student not found")
                   );
    }

    public void deleteStudentById(long id) {
        if(this.repository.existsById(id)){
            this.repository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException("Student not found");
        }
    }

    public StudentResponse save(StudentRequest student){
        var entity =this.repository.save(StudentMapper.toEntity(student));         
        return StudentMapper.toDTO(entity);
    }

    public void update(long id, Student student) {

        try{
            var updateStudent = this.repository.getReferenceById(id);
            updateStudent.setName(student.getName());
            updateStudent.setCpf(student.getCpf());
            updateStudent.setSemestre(student.getSemestre());
            updateStudent.setTurma(student.getTurma());
            this.repository.save(updateStudent);
        }
        catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Student not found");
        }

    }   
}
