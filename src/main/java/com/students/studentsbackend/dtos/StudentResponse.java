package com.students.studentsbackend.dtos;

public record StudentResponse(
    long id,
    String name,
    String cpf,
    String semester,
    String school_class
)  {
    
}
