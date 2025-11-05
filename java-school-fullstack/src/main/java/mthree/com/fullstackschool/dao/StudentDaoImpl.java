package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.StudentMapper;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Student createNewStudent(Student student) {
        String sql = "INSERT INTO student (firstname, lastname) VALUES (?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getStudentFirstName());
            ps.setString(2, student.getStudentLastName());
            return ps;
        }, keyHolder);

        student.setStudentId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, new StudentMapper());
    }

    @Override
    public Student findStudentById(int id) {
        String sql = "SELECT * FROM student WHERE studentid = ?";
        return jdbcTemplate.queryForObject(sql, new StudentMapper(), id);
    }

    @Override
    public void updateStudent(Student student) {
        String sql = "UPDATE student SET firstname = ?, lastname = ? WHERE studentid = ?";
        jdbcTemplate.update(sql, student.getStudentFirstName(), student.getStudentLastName(), student.getStudentId());
    }

    @Override
    public void deleteStudent(int id) {
        String sql = "DELETE FROM student WHERE studentid = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        String sql = "INSERT INTO studentcourse (studentid, courseid) VALUES (?, ?)";
        jdbcTemplate.update(sql, studentId, courseId);
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        String sql = "DELETE FROM studentcourse WHERE studentid = ? AND courseid = ?";
        jdbcTemplate.update(sql, studentId, courseId);
    }
}
