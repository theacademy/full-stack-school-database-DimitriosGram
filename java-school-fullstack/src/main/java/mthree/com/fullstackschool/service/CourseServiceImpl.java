package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseServiceInterface {

    private final CourseDao courseDao;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDao.getAllCourses();
    }

    @Override
    public Course getCourseById(int id) {
        try {
            return courseDao.findCourseById(id);
        } catch (DataAccessException e) {
            Course course = new Course();
            course.setCourseName("Course Not Found");
            course.setCourseDesc("Course Not Found");
            return course;
        }
    }

    @Override
    public Course addNewCourse(Course course) {
        if (course.getCourseName() == null || course.getCourseName().isBlank() ||
                course.getCourseDesc() == null || course.getCourseDesc().isBlank()) {
            course.setCourseName("Name blank, course NOT added");
            course.setCourseDesc("Description blank, course NOT added");
            return course;
        }
        return courseDao.createNewCourse(course);
    }

    @Override
    public Course updateCourseData(int id, Course course) {
        if (id != course.getCourseId()) {
            course.setCourseName("IDs do not match, course not updated");
            course.setCourseDesc("IDs do not match, course not updated");
            return course;
        }
        courseDao.updateCourse(course);
        return course;
    }

    @Override
    public void deleteCourseById(int id) {
        courseDao.deleteCourse(id);
        System.out.println("Course ID: " + id + " deleted");
    }
}
