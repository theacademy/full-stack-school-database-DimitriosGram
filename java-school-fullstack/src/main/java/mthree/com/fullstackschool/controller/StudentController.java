package mthree.com.fullstackschool.controller;

import mthree.com.fullstackschool.model.Student;
import mthree.com.fullstackschool.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

    @Autowired
    StudentServiceImpl studentServiceImpl;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentServiceImpl.getAllStudents();
    }

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentServiceImpl.addNewStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentServiceImpl.getStudentById(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student student) {
        return studentServiceImpl.updateStudentData(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentServiceImpl.deleteStudentById(id);
    }

    @DeleteMapping("/{studentId}/{courseId}")
    public void deleteStudentFromCourse(@PathVariable int studentId, @PathVariable int courseId) {
        studentServiceImpl.deleteStudentFromCourse(studentId, courseId);
    }

    @PostMapping("/{studentId}/{courseId}")
    public void addStudentToCourse(@PathVariable int studentId, @PathVariable int courseId) {
        studentServiceImpl.addStudentToCourse(studentId, courseId);
    }
}
