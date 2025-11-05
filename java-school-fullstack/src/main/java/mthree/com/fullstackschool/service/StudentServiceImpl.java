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

    //YOUR CODE STARTS HERE
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }
    //YOUR CODE ENDS HERE

    @Override
    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE
        return studentDao.getAllStudents();
        //YOUR CODE ENDS HERE
    }

    @Override
    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE
        try {
            return studentDao.findStudentById(id);
        } catch (DataAccessException e) {
            Student s = new Student();
            s.setFirstName("Student Not Found");
            s.setLastName("Student Not Found");
            return s;
        }
        //YOUR CODE ENDS HERE
    }

    @Override
    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE
        if (student.getFirstName() == null || student.getFirstName().isBlank() ||
                student.getLastName() == null || student.getLastName().isBlank()) {
            student.setFirstName("First Name blank, student NOT added");
            student.setLastName("Last Name blank, student NOT added");
            return student;
        }
        return studentDao.createNewStudent(student);
        //YOUR CODE ENDS HERE
    }

    @Override
    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE
        if (id != student.getStudentId()) {
            student.setFirstName("IDs do not match, student not updated");
            student.setLastName("IDs do not match, student not updated");
            return student;
        }
        studentDao.updateStudent(student);
        return student;
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE
        studentDao.deleteStudent(id);
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        Student s = getStudentById(studentId);
        Course c = courseDao.findCourseById(courseId);

        if (s.getFirstName().equals("Student Not Found")) {
            System.out.println("Student not found");
        } else if (c.getCourseName().equals("Course Not Found")) {
            System.out.println("Course not found");
        } else {
            studentDao.deleteStudentFromCourse(studentId, courseId);
            System.out.println("Student: " + studentId + " deleted from course: " + courseId);
        }
        //YOUR CODE ENDS HERE
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE
        Student s = getStudentById(studentId);
        Course c = courseDao.findCourseById(courseId);

        if (s.getFirstName().equals("Student Not Found")) {
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
        //YOUR CODE ENDS HERE
    }
}
