import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Demo {

    public static void main(String[] args) {
        List<Person> persons = List.of(new Person("小明", 88), new Person("小黑", 62), new Person("小白", 45),
                new Person("小黄", 78), new Person("小红", 99), new Person("小林", 58));
        persons.stream().filter(person -> person.score >= 60).forEach(System.out::println);
        List<String> list = List.of("Apple", "Banana", "Blackberry", "Coconut", "Avocado", "Cherry", "Apricots");
        Map<String, List<String>> groups = list.stream()
                .collect(Collectors.groupingBy(s -> s.substring(0, 1), Collectors.toList()));
        System.out.println(groups);
    }
}

class Person {
    String name;
    int score;

    Person(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "%s:%d".formatted(name, score);
    }
}