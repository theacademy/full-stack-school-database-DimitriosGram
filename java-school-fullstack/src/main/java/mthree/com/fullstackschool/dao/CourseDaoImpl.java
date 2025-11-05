package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.CourseMapper;
import mthree.com.fullstackschool.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
        //YOUR CODE STARTS HERE

        String sql = "INSERT INTO Course (CourseName, CourseDesc) VALUES (?, ?)";
        Keyholder keyholder = new GeneratedKeyHodler();

        jdbcTemplate.update(connection -> {

            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getCourseDesc());

            return ps;
        }, keyholder);

        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE

            String sql = "SELECT * FROM Course";
            return jdbcTemplate.query(sql, new CourseMapper());

        //YOUR CODE ENDS HERE
    }

    @Override
    public Course findCourseById(int id) {
        //YOUR CODE STARTS HERE

        String sql = "SELECT * FROM Course WHERE CourseId = ?";
        return jdbcTemplate.queryForObject(sql, CourseMapper(, id));
        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateCourse(Course course) {
        //YOUR CODE STARTS HERE
        String sql = "UPDATE Course SET CourseName = ?, CourseDesc = ? WHERE CourseId = ?";
        jdbcTemplate.update(sql, course.getCourseName(), course.getCourseDesc(), course.getCourseId());
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteCourse(int id) {
        //YOUR CODE STARTS HERE
        String sql = "DELETE FROM Course WHERE CourseId = ?";
        jdbcTemplate.update(sql, id);
        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteAllStudentsFromCourse(int courseId) {
        //YOUR CODE STARTS HERE
        String sql = "DELETE FROM StudentCourse WHERE CourseId = ?";
        jdbcTemplate.update(sql, courseId);
        //YOUR CODE ENDS HERE
    }
}
