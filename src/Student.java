import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student implements Comparable<Student>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private Map<String, Integer> scores;

    public Student() {

    }

    public Student(String id, String name, Map<String, Integer> scores) {
        this.name = name;
        this.id = id;
        this.scores = new HashMap<>(scores);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = new HashMap<>(scores);
    }

    public Map<String, Integer> getScores() {
        return this.scores;
    }


    @Override
    public String toString() {
        return this.id + "," + this.name + "," + scores;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Student student) {
            return Objects.equals(this.id, student.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public int compareTo(Student other) {
        if (other == null)
            return 1;
        if (this.id == null && other.id == null)
            return 0;
        if (this.id == null)
            return -1;
        if (other.id == null)
            return 1;
        return this.id.compareTo(other.id);
    }
}
