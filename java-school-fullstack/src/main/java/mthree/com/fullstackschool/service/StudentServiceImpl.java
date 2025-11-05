package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Course;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    private final StudentDao studentDao;
    private final CourseDao courseDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    @Override
    public Student getStudentById(int id) {
        try {
            return studentDao.findStudentById(id);
        } catch (DataAccessException e) {
            Student s = new Student();
            s.setStudentFirstName("Student Not Found");
            s.setStudentLastName("Student Not Found");
            return s;
        }
    }

    @Override
    public Student addNewStudent(Student student) {
        if (student.getStudentFirstName() == null || student.getStudentFirstName().isBlank() ||
                student.getStudentLastName() == null || student.getStudentLastName().isBlank()) {
            student.setStudentFirstName("First Name blank, student NOT added");
            student.setStudentLastName("Last Name blank, student NOT added");
            return student;
        }
        return studentDao.createNewStudent(student);
    }

    @Override
    public Student updateStudentData(int id, Student student) {
        if (id != student.getStudentId()) {
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
            return student;
        }
        studentDao.updateStudent(student);
        return student;
    }

    @Override
    public void deleteStudentById(int id) {
        studentDao.deleteStudent(id);
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        Student s = getStudentById(studentId);
        Course c = courseDao.findCourseById(courseId);

        if (s.getStudentFirstName().equals("Student Not Found")) {
            System.out.println("Student not found");
        } else if (c.getCourseName().equals("Course Not Found")) {
            System.out.println("Course not found");
        } else {
            studentDao.deleteStudentFromCourse(studentId, courseId);
            System.out.println("Student: " + studentId + " deleted from course: " + courseId);
        }
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        Student s = getStudentById(studentId);
        Course c = courseDao.findCourseById(courseId);

        if (s.getStudentFirstName().equals("Student Not Found")) {
            System.out.println("Student not found");
        } else if (c.getCourseName().equals("Course Not Found")) {
            System.out.println("Course not found");
        } else {
            try {
                studentDao.addStudentToCourse(studentId, courseId);
                System.out.println("Student: " + studentId + " added to course: " + courseId);
            } catch (Exception e) {
                System.out.println("Student: " + studentId + " already enrolled in course: " + courseId);
            }
        }
    }
}
