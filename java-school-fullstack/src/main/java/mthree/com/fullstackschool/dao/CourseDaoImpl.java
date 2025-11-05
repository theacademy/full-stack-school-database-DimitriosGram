package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.CourseMapper;
import mthree.com.fullstackschool.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {

    private final JdbcTemplate jdbcTemplate;

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course createNewCourse(Course course) {
        String sql = "INSERT INTO course (courseCode, courseDesc, teacherId) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getCourseDesc());
            ps.setInt(3, course.getTeacherId());
            return ps;
        }, keyHolder);

        course.setCourseId(keyHolder.getKey().intValue());
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM course";
        return jdbcTemplate.query(sql, new CourseMapper());
    }

    @Override
    public Course findCourseById(int id) {
        String sql = "SELECT * FROM course WHERE cid = ?";
        return jdbcTemplate.queryForObject(sql, new CourseMapper(), id);
    }

    @Override
    public void updateCourse(Course course) {
        String sql = "UPDATE course SET courseCode = ?, courseDesc = ?, teacherId = ? WHERE cid = ?";
        jdbcTemplate.update(sql, course.getCourseName(), course.getCourseDesc(), course.getTeacherId(), course.getCourseId());
    }

    @Override
    public void deleteCourse(int id) {
        // First, remove all student links from course_student
        String sqlDeleteLinks = "DELETE FROM course_student WHERE course_id = ?";
        jdbcTemplate.update(sqlDeleteLinks, id);

        // Then, delete the course itself
        String sqlDeleteCourse = "DELETE FROM course WHERE cid = ?";
        jdbcTemplate.update(sqlDeleteCourse, id);
    }

    @Override
    public void deleteAllStudentsFromCourse(int courseId) {
        String sql = "DELETE FROM course_student WHERE course_id = ?";
        jdbcTemplate.update(sql, courseId);
    }
}
