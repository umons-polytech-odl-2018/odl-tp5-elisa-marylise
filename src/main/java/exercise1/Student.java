package exercise1;

import java.util.*;
import java.util.stream.*;


/**
 * Represents a student.
 * A Student is identified by its registration number.
 * A student gets scored in different courses.
 * These scores are expressed as integers on a scale from 0 to 20.
 */
public class Student {
    /**
     * Creates a new Student.
     *
     * @throws NullPointerException if one of the parameter is null.
     */

    String name;
    String registrationNumber;
    HashMap<String, Integer> scoreByCourse = new HashMap<>();

    public Student(String name, String registrationNumber) {
        if (name == null || registrationNumber == null) {
            throw new NullPointerException();
        }
        /*else if (){
            throw new DuplicateStudentException();
        }*/
        else {
            this.name = name;
            this.registrationNumber = registrationNumber;
        }
    }

    /**
     * Sets the score of this student for the given course.
     * If the score is set twice for the same course, the new score replaces the previous one.
     *
     * @throws NullPointerException if the course name is null.
     * @throws IllegalArgumentException if the score is less than 0 or greater than 20.
     */
    public void setScore(String course, int score) {
        if (course == null) {
            throw new NullPointerException();
        }
        else if (score < 0 || score > 20) {
            throw new IllegalArgumentException();
        }
        else {
            scoreByCourse.put(course, new Integer(score));
        }
    }

    /**
     * Returns the score for the given course.
     *
     * @return the score if found, <code>OptionalInt#empty()</code> otherwise.
     */
    public OptionalInt getScore(String course) {
        Integer value = scoreByCourse.get(course);
        return value != null ? OptionalInt.of(value):OptionalInt.empty();
    }

    /**
     * Returns the average score.
     *
     * @return the average score or 0 if there is none.
     */
    public double averageScore() {

        return scoreByCourse.values().stream()
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0);
    }

    /**
     * Returns the course with the highest score.
     *
     * @return the best scored course or <code>Optional#empty()</code> if there is none.
     */
    public Optional<String> bestCourse() {
        return scoreByCourse.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .map(Map.Entry::getKey)
            .findFirst();
    }

    /**
     * Returns the highest score.
     *
     * @return the highest score or 0 if there is none.
     */
    public int bestScore() {
        return scoreByCourse.values().stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElse(0);
    }

    /**
     * Returns the set of failed courses sorted by decreasing score.
     * A course is considered as passed if its score is higher than 12.
     */
    public Set<String> failedCourses() {
        return scoreByCourse.entrySet().stream()
            .filter(score -> score.getValue() <= 12)
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Returns <code>true</code> if the student has an average score greater than or equal to 12.0 and has less than 3 failed courses.
     */
    public boolean isSuccessful() {
        if (averageScore() >= 12 && failedCourses().size() < 3)
            return true;
        else
            return false;
    }

    /**
     * Returns the set of courses for which the student has received a score, sorted by course name.
     */
    public Set<String> attendedCourses() {
        return scoreByCourse.entrySet().stream()
            .sorted(Map.Entry.comparingByKey(Comparator.naturalOrder()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public String getName() {
        return name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getName());
        sb.append(" (").append(getRegistrationNumber()).append(")");
        return sb.toString();
    }
}
