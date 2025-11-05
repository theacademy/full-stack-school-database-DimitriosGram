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
        String sql = "INSERT INTO Course (CourseName, CourseDesc) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getCourseDesc());
            return ps;
        }, keyHolder);

        course.setCourseId(keyHolder.getKey().intValue());
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM Course";
        return jdbcTemplate.query(sql, new CourseMapper());
    }

    @Override
    public Course findCourseById(int id) {
        String sql = "SELECT * FROM Course WHERE CourseId = ?";
        return jdbcTemplate.queryForObject(sql, new CourseMapper(), id);
    }

    @Override
    public void updateCourse(Course course) {
        String sql = "UPDATE Course SET CourseName = ?, CourseDesc = ? WHERE CourseId = ?";
        jdbcTemplate.update(sql, course.getCourseName(), course.getCourseDesc(), course.getCourseId());
    }

    @Override
    public void deleteCourse(int id) {
        String sql = "DELETE FROM Course WHERE CourseId = ?";
        jdbcTemplate.update(sql,
