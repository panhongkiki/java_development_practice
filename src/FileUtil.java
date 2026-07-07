import java.util.Set;
import java.util.TreeSet;
import java.io.*;

public class FileUtil {
    private static final String FILE_NAME = "students.dat";

    // 序列化
    public static void saveToFile(Set<Student> studentList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentList);
            System.out.println("数据已成功自动保存至本地文件！");
        } catch (IOException e) {
            System.out.println("数据保存失败: " + e.getMessage());
        }
    }

    // 反序列化
    @SuppressWarnings("unchecked")
    public static Set<Student> loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("未检测到历史数据，系统已初始化新列表。");
            return new TreeSet<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // 从文件中读取对象，并强转回 Set<Student>
            return (Set<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("历史数据读取失败，已加载空列表: " + e.getMessage());
            return new TreeSet<>();
        }
    }
}
