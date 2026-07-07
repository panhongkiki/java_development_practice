import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Student> studentList = FileUtil.loadFromFile();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[系统检测到退出] 正在为您安全保存数据...");
            FileUtil.saveToFile(studentList);
        }));
        Scanner scanner = new Scanner(System.in);
        System.out.println("欢迎来到学生管理系统简洁版，请输入你要执行的操作：");
        System.out.println("0.退出系统");
        System.out.println("1.查看所有学生");
        System.out.println("2.添加学生（输入学号、姓名、三科成绩）");
        System.out.println("3.删除学生");
        System.out.println("4.修改学生");
        System.out.println("5.查询学生");
        String oper = scanner.nextLine().trim();
        while (true) {
            switch (oper) {
                case "1" -> {
                    System.out.println("即将查看所有学生");
                    showAll(studentList);
                }
                case "2" -> {
                    System.out.println("即将添加学生");
                    if (addStudent(studentList, scanner)) {
                        System.out.println("添加成功");
                    } else {
                        System.out.println("添加失败，终止添加或学号不能重复。");
                    }
                }
                case "3" -> {
                    System.out.println("即将删除学生");
                    if (delStudent(studentList, scanner)) {
                        System.out.println("删除成功");
                    } else {
                        System.out.println("删除失败，当前学生不存在。");
                    }
                }
                case "4" -> {
                    System.out.println("即将修改学生");
                    if (updStudent(studentList, scanner)) {
                        System.out.println("修改成功");
                    } else {
                        System.out.println("修改失败，用户退出或当前学生不存在。");
                    }
                }
                case "5" -> {
                    System.out.println("即将查看所有学生");
                    queryStudent(studentList, scanner);
                }
                case "0" -> {
                    System.out.println("即将退出学生管理系统");
                    return;
                }
                default -> System.out.println("暂时还没有这个操作，试试其他的");
            }
            oper = scanner.nextLine().trim();
        }
    }

    public static void showAll(Set<Student> studentList) {
        if (studentList.isEmpty()) {
            System.out.println("当前还没有学生，快来添加吧");
            return;
        }
        for (Student student : studentList) {
            System.out.println(student.toString());
        }
    }

    public static boolean addStudent(Set<Student> studentList, Scanner scanner) {
        System.out.println("--- 开始录入学生信息（随时输入 quit 退出整个录入） ---");
        System.out.print("请输入学号: ");
        String id = scanner.nextLine().trim();
        if (id.equalsIgnoreCase("quit"))
            return false;
        System.out.print("请输入姓名: ");
        String name = scanner.nextLine().trim();
        if (name.equalsIgnoreCase("quit"))
            return false;
        Map<String, Integer> scores = new HashMap<>();
        while (true) {
            System.out.print("请输入学科名称 (或输入 quit 结束成绩录入): ");
            String discipline = scanner.nextLine().trim();
            if (discipline.equalsIgnoreCase("quit"))
                break;
            System.out.print("请输入 " + discipline + " 的成绩: ");
            String scoreStr = scanner.nextLine().trim();
            if (scoreStr.equalsIgnoreCase("quit"))
                break;
            try {
                Integer score = Integer.parseInt(scoreStr);
                scores.put(discipline, score);
            } catch (NumberFormatException e) {
                System.out.println("成绩格式输入错误，请输入整数！该学科录入跳过。");
            }
        }
        Student student = new Student(id, name, scores);
        return studentList.add(student);
    }

    public static boolean delStudent(Set<Student> studentList, Scanner scanner) {
        System.out.println("--- 开始删除学生信息，输入quit终止删除 ---");
        System.out.print("请输入学号: ");
        String id = scanner.nextLine().trim();
        if (id.equalsIgnoreCase("quit")) {
            System.out.println("终止删除");
            return false;
        }
        return studentList.removeIf(student -> student.getId().equals(id));
    }

    public static boolean updStudent(Set<Student> studentList, Scanner scanner) {
        System.out.println("--- 开始更改学生信息，输入quit终止更改 ---");
        System.out.print("请输入学号: ");
        String id = scanner.nextLine().trim();
        if (id.equalsIgnoreCase("quit")) {
            System.out.println("终止更改");
            return false;
        }
        Student targetStudent = null;
        for (Student student : studentList) {
            if (id.equalsIgnoreCase(student.getId())) {
                targetStudent = student;
                break;
            }
        }
        if (targetStudent == null) {
            return false;
        }

        System.out.println("当前学生信息: " + targetStudent);
        System.out.println("请选择要修改的内容：1.修改姓名 | 2.修改/添加单科成绩 | 0.放弃修改");

        String oper = scanner.nextLine().trim();

        switch (oper) {
            case "1" -> {
                System.out.print("输入修改后的姓名: ");
                String newName = scanner.nextLine().trim();
                targetStudent.setName(newName);
                return true;
            }
            case "2" -> {
                System.out.print("输入想要修改或追加的学科: ");
                System.out.print("请输入学科名称 (或输入 quit 结束成绩录入): ");
                String discipline = scanner.nextLine().trim();
                System.out.print("请输入 " + discipline + " 的成绩: ");
                String scoreStr = scanner.nextLine().trim();
                try {
                    Integer score = Integer.parseInt(scoreStr);
                    Map<String, Integer> scores = targetStudent.getScores();
                    scores.put(discipline, score);
                } catch (NumberFormatException e) {
                    System.out.println("成绩格式输入错误，请输入整数！");
                    return false;
                }
            }
            case "0" -> {
                System.out.println("即将退出修改");
                return true;
            }
            default -> {
                System.out.print("暂时还没有这个操作，试试其他的");
                return false;
            }
        }
        return true;
    }

    public static void queryStudent(Set<Student> studentList, Scanner scanner) {
        System.out.print("输入你要查询的学生学号，输入quit终止查询：");
        String targetId = scanner.nextLine().trim();
        if (targetId.equalsIgnoreCase("quit"))
            return;
        Student targetStudent = null;
        for (Student student : studentList) {
            if (student.getId().equalsIgnoreCase(targetId)) {
                targetStudent = student;
                break;
            }
        }
        if (targetStudent == null) {
            System.out.print("你查找的学生不存在，请重新查找。");
            return;
        }
        System.out.print(targetStudent);
    }
}