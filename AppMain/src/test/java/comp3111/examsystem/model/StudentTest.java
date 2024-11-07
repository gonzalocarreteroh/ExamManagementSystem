package comp3111.examsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {
    @Test
    void gettersTest() {
        Student s = new Student(1, "123456", "john@ust.hk", "John", 20, "CS", Gender.Male);
        assertEquals(1, s.getId());
        assertEquals("123456", s.getPassword());
        assertEquals("john@ust.hk", s.getUsername());
        assertEquals("John", s.getName());
        assertEquals(20, s.getAge());
        assertEquals("CS", s.getDepartment());
        assertEquals(Gender.Male, s.getGender());
    }
}
