import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author HUSEYIN EMRE UGDUL
 * @since 28.05.2021
 */

class CourseNotFoundException extends RuntimeException {
    private Student student;
    private Course course;

    public CourseNotFoundException(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    @Override
    public String toString() {
        return "CourseNotFoundException:" + this.student.getID() + " has not "
               + "yet taken " + this.course.courseCode();
    }
}

class DepartmentMismatchException extends RuntimeException {
    private Department department;
    private Teacher person;
    private Course course;

    public DepartmentMismatchException(Course course, Teacher person) {
        this.course = course;
        this.person = person;
        this.department = null;
    }

    public DepartmentMismatchException(Department department, Teacher person) {
        this.department = department;
        this.person = person;
        this.course = null;
    }

    @Override
    public String toString() {
        if (this.course == null) {
            return "DepartmentMismatchException:" + person.getName() + " (" +
                   person.getID() + ") cannot be chair of " + department.getID()
                   + " because he/she is currently assigned to " +
                   person.getDepartment().getID();
        } else {
            return "DepartmentMismatchException:" + person.getName() + " (" +
                   person.getID() + ") cannot teach " + course.courseCode() +
                   " because he/she is currently assigned to " +
                   person.getDepartment().getID();
        }

    }
}

class InvalidGradeException extends RuntimeException {
    private double grade;

    public InvalidGradeException(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "InvalidGradeException:" + this.grade;
    }
}

class InvalidRankException extends RuntimeException {
    private int rank;

    public InvalidRankException(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "InvalidRankException:" + rank;
    }
}

class InvalidIDException extends RuntimeException {
    private String id;

    public InvalidIDException(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "InvalidIDException:Department ID " + this.id + " ID "
               + "must be 3 or 4 characters.";
    }
}

class InvalidNumberException extends RuntimeException {
    private int number;

    public InvalidNumberException(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "InvalidNumberException:" + this.number + " Course number "
               + "must be in the range 100-499 or 5000-5999 or 7000-7999";
    }
}

class mustPositiveException extends RuntimeException {
    private int akts;

    public mustPositiveException(int akts) {
        this.akts = akts;
    }

    @Override
    public String toString() {
        return "mustPositiveException:" + this.akts + " Course AKTS must be "
               + "positive.";
    }
}

class InvalidEmailException extends RuntimeException {
    private String email;

    public InvalidEmailException(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "InvalidEmailException:" + this.email + " email must be "
               + "correct form. Example: example@example.example";
    }
}

class SemesterNotFoundException extends RuntimeException {
    private Student student;
    private Semester semester;

    public SemesterNotFoundException(Student student, Semester semester) {
        this.semester = semester;
        this.student = student;
    }

    @Override
    public String toString() {
        return "SemesterNotFoundException:" + student.getID() + " has not "
               + "taken any courses in " + semester.getSeason() +
               semester.getYear();
    }
}
class compareSemester implements Comparator<Semester>{//TODO: gel

    @Override
    public int compare(Semester o1, Semester o2) {
        if(o1.getYear() < o2.getYear()){
            return -1;
        }
        else if (o1.getYear() > o2.getYear()){
            return 1;
        }
        else
            return o1.getSeason().compareTo(o2.getSeason());
    }
}

class Department {
    private String id;
    private String name;
    private Teacher chair;

    public Department(String id, String name) {
        if (id.length() == 3 || id.length() == 4) {
            this.id = id;
        } else {
            throw new InvalidIDException(id);
        }
        this.name = name;
    }

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        if (id.length() == 3 || id.length() == 4) {
            this.id = id;
        } else {
            throw new InvalidIDException(id);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getChair() {
        return this.chair;
    }

    public void setChair(Teacher chair) {
        if (chair == null) {
            this.chair = null;
        } else if (this.chair == null) {
            if (chair.getDepartment() == this)
                this.chair = chair;
            else
                throw new DepartmentMismatchException(this, chair);
        } else {
            if (chair.getDepartment() == this)
                this.chair = chair;
            else
                throw new DepartmentMismatchException(this, chair);
        }
    }
}

class Course {
    private Department department;
    private Teacher teacher;
    private int number;
    private String title;
    private int akts;

    public Course(Department department, int number, String title, int akts,
                  Teacher teacher) {
        this.department = department;


        this.teacher = teacher;
        if (number >= 100 && number < 500 || number >= 5000 && number < 6000 ||
            number >= 7000 && number < 8000) {
            this.number = number;
        } else {
            throw new InvalidNumberException(number);
        }
        this.title = title;
        if (akts > 0) {
            this.akts = akts;
        } else {
            throw new mustPositiveException(akts);
        }
        if (department != teacher.getDepartment())
            throw new DepartmentMismatchException(this, teacher);
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Teacher getTeacher() {//exception için dön
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        if (this.department != teacher.getDepartment())
            throw new DepartmentMismatchException(this, teacher);
        this.teacher = teacher;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if (number >= 100 && number < 500 || number >= 5000 && number < 6000 ||
            number >= 7000 && number < 8000) {
            this.number = number;
        } else {
            throw new InvalidNumberException(number);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAKTS() {
        return akts;
    }

    public void setAKTS(int akts) {
        if (akts > 0) {
            this.akts = akts;
        } else {
            throw new mustPositiveException(akts);
        }
    }

    public String courseCode() {
        return this.department.getID() + " " + this.number;
    }

    @Override
    public String toString() {
        return this.department.getID() + " " + this.number + " - " +
               this.title + " (" + this.akts + ")";
    }
}

abstract class Person {
    private Department department;
    private String name;
    private String email;
    private long id;
    String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/="
                   + "?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+$";

    Pattern pattern = Pattern.compile(regex);

    public Person(String name, String email, long id, Department department) {
        this.department = department;
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            this.email = email;
        } else {
            throw new InvalidEmailException(email);
        }
        this.name = name;
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            this.email = email;
        } else {
            throw new InvalidEmailException(email);
        }
    }

    public long getID() {
        return this.id;
    }

    public void setID(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.name + "(" + this.id + ")" + " - " + this.email;
    }
}

class Teacher extends Person {
    private int rank;

    public Teacher(String name, String email, long id, Department department,
                   int rank) {
        super(name, email, id, department);
        if (rank >= 1 && rank <= 5) {
            this.rank = rank;
        } else {
            throw new InvalidRankException(rank);
        }
    }

    @Override
    public void setDepartment(Department department) {//override
        if (this == this.getDepartment().getChair()) {
            this.getDepartment().setChair(null);
        }
        super.setDepartment(department);
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        if (rank >= 1 && rank <= 5) {
            this.rank = rank;
        } else {
            throw new InvalidRankException(rank);
        }
    }

    public String getTitle() {
        if (this.rank == 1)
            return "Adjunct Instructor";
        else if (this.rank == 2)
            return "Lecturer";
        else if (this.rank == 3)
            return "Assistant Professor";
        else if (this.rank == 4)
            return "Associate Professor";
        else
            return "Professor";
    }

    //increase the status of teacher
    public void promote() {
        if (this.rank >= 1 && this.rank <= 4)
            this.rank++;
        else {
            throw new InvalidRankException(this.rank + 1);
        }
    }

    //decrease the status of teacher
    public void demote() {
        if (this.rank >= 2 && this.rank <= 5)
            this.rank--;
        else {
            throw new InvalidRankException(this.rank - 1);
        }
    }

    @Override
    public String toString() {
        return this.getTitle() + " " + super.toString();
    }
}

class Student extends Person {
    private HashMap<Semester, HashMap<Course, Double>> x = new HashMap<>();


    public Student(String name, String email, long id, Department department) {
        super(name, email, id, department);
    }

    public HashMap<Semester, HashMap<Course, Double>> getX() {
        return x;
    }

    public void addCourse(Course course, Semester semester, double grade) {
        if (x.containsKey(semester)) {
            if (grade >= 0 && grade <= 100) {
                x.get(semester).put(course, grade);
            } else {
                throw new InvalidGradeException(grade);
            }
        } else {
            if (grade >= 0 && grade <= 100) {
                x.put(semester, new HashMap<>());
                x.get(semester).put(course, grade);
            } else {
                throw new InvalidGradeException(grade);
            }
        }
    }

    public int getAKTS() {
        int totalAKTS = 0;
        Collection<HashMap<Course, Double>> arrayList = x.values();
        HashSet<Course> courses = new HashSet<>();
        for (HashMap<Course, Double> list : arrayList) {
            for (Course course : list.keySet()) {
                courses.add(course);
            }
        }
        for (Course course : courses) {
            if (courseResult(course).equals("Passed")) {
                totalAKTS += course.getAKTS();
            }
        }
        return totalAKTS;
    }

    public int getAttemptedAKTS() {
        int totalAKTS = 0;
        Collection<HashMap<Course, Double>> arrayList = x.values();
        HashSet<Course> courses = new HashSet<>();
        for (HashMap<Course, Double> list : arrayList) {
            for (Course course : list.keySet()) {
                courses.add(course);
            }
        }
        for (Course course : courses) {
            totalAKTS += course.getAKTS();
        }
        return totalAKTS;
    }

    public double courseGPAPoints(Course course) {
        Collection<HashMap<Course, Double>> arrayList = x.values();
        boolean isContains = false;
        for (HashMap<Course, Double> list : arrayList) {
            if (list.containsKey(course)) {
                isContains = true;
                break;
            }
        }
        if (!isContains)
            throw new CourseNotFoundException(this, course);
        else {
            double grade = 0.0;
            for (HashMap<Course, Double> list : arrayList) {
                if (list.containsKey(course)) {
                    if (list.get(course) > grade) {
                        grade = list.get(course);
                    }
                }
            }
            if (grade >= 88 && grade <= 100)
                return 4.0;
            else if (grade >= 81 && grade <= 87)
                return 3.5;
            else if (grade >= 74 && grade <= 80)
                return 3.0;
            else if (grade >= 67 && grade <= 73)
                return 2.5;
            else if (grade >= 60 && grade <= 66)
                return 2.0;
            else if (grade >= 53 && grade <= 59)
                return 1.5;
            else if (grade >= 46 && grade <= 52)
                return 1.0;
            else if (grade >= 35 && grade <= 45)
                return 0.5;
            else
                return 0.0;
        }

    }

    public String courseGradeLetter(Course course) {
        Collection<HashMap<Course, Double>> arrayList = x.values();
        boolean isContains = false;
        for (HashMap<Course, Double> list : arrayList) {
            if (list.containsKey(course)) {
                isContains = true;
                break;
            }
        }
        if (!isContains)
            throw new CourseNotFoundException(this, course);
        else {
            double grade = 0.0;
            for (HashMap<Course, Double> list : arrayList) {
                if (list.containsKey(course)) {
                    if (list.get(course) > grade) {
                        grade = list.get(course);
                    }
                }
            }
            if (grade >= 88 && grade <= 100)
                return "AA";
            else if (grade >= 81 && grade <= 87)
                return "BA";
            else if (grade >= 74 && grade <= 80)
                return "BB";
            else if (grade >= 67 && grade <= 73)
                return "CB";
            else if (grade >= 60 && grade <= 66)
                return "CC";
            else if (grade >= 53 && grade <= 59)
                return "DC";
            else if (grade >= 46 && grade <= 52)
                return "DD";
            else if (grade >= 35 && grade <= 45)
                return "FD";
            else
                return "FF";
        }
    }

    public String courseResult(Course course) {
        Collection<HashMap<Course, Double>> arrayList = x.values();
        boolean isContains = false;
        for (HashMap<Course, Double> list : arrayList) {
            if (list.containsKey(course)) {
                isContains = true;
                break;
            }
        }
        if (!isContains)
            throw new CourseNotFoundException(this, course);
        else {
            double grade = 0.0;
            for (HashMap<Course, Double> list : arrayList) {
                if (list.containsKey(course)) {
                    if (list.get(course) > grade) {
                        grade = list.get(course);
                    }
                }
            }
            if (grade >= 60 && grade <= 100)
                return "Passed";
            else if (grade >= 46 && grade <= 59)
                return "Conditionally Passed";
            else
                return "Failed";
        }
    }

    public double getGPA() {
        double gpa = 0;
        Collection<HashMap<Course, Double>> arrayList = x.values();
        HashSet<Course> courses = new HashSet<>();
        for (HashMap<Course, Double> list : arrayList) {
            for (Course course : list.keySet()) {
                courses.add(course);
            }
        }
        for (Course course : courses) {
            gpa += courseGPAPoints(course) * course.getAKTS();
        }
        return gpa / getAttemptedAKTS();
    }

    public String listGrades(Semester semester) {
        String listGrades = "";
        HashMap<Course, Double> tempMap = x.get(semester);
        if (!x.containsKey(semester))
            throw new SemesterNotFoundException(this, semester);
        for (Course course : tempMap.keySet()) {
            double grade = tempMap.get(course);
            listGrades += course.courseCode() + " - ";
            if (grade >= 88 && grade <= 100)
                listGrades += "AA";
            else if (grade >= 81 && grade <= 87)
                listGrades += "BA";
            else if (grade >= 74 && grade <= 80)
                listGrades += "BB";
            else if (grade >= 67 && grade <= 73)
                listGrades += "CB";
            else if (grade >= 60 && grade <= 66)
                listGrades += "CC";
            else if (grade >= 53 && grade <= 59)
                listGrades += "DC";
            else if (grade >= 46 && grade <= 52)
                listGrades += "DD";
            else if (grade >= 35 && grade <= 45)
                listGrades += "FD";
            else
                listGrades += "FF";
        listGrades += "\n";
        }


        return listGrades;
    }

    public String listGrades(Course course) {
        String listGrades = "";
        for (Semester semester : x.keySet()) {
            if (x.get(semester).containsKey(course)) {
                double grade = x.get(semester).get(course);
                listGrades += semester.getSeason() + " - " +
                              semester.getYear() + " - ";
                if (grade >= 88 && grade <= 100)
                    listGrades += "AA";
                else if (grade >= 81 && grade <= 87)
                    listGrades += "BA";
                else if (grade >= 74 && grade <= 80)
                    listGrades += "BB";
                else if (grade >= 67 && grade <= 73)
                    listGrades += "CB";
                else if (grade >= 60 && grade <= 66)
                    listGrades += "CC";
                else if (grade >= 53 && grade <= 59)
                    listGrades += "DC";
                else if (grade >= 46 && grade <= 52)
                    listGrades += "DD";
                else if (grade >= 35 && grade <= 45)
                    listGrades += "FD";
                else
                    listGrades += "FF";
                listGrades += "\n";
            }
        }
        if (listGrades.equals("")) {
            throw new CourseNotFoundException(this, course);
        }
        return listGrades;
    }

    public String transcript() {//TODO: comparator ile karşılaştırma yap
        String transcript = "";
        List<Semester> new_list = new ArrayList<>();
        for (Semester semesterX : x.keySet()){
            new_list.add(semesterX);
        }
        Collections.sort(new_list, new compareSemester());
        for (Semester semester : new_list) {
            HashMap<Course, Double> tempMap = x.get(semester);
            transcript += semester.getSeason() + " - " + semester.getYear()
                          + "\n";
            double gpa = 0.0;
            int akts = 0;
            for (Course course : tempMap.keySet()) {
                double variablaGPA = 0.0;
                double grade = tempMap.get(course);
                transcript += "    " + course.courseCode() + " - ";
                if (grade >= 88 && grade <= 100){
                    transcript += "AA";
                    variablaGPA = 4.0;
                }
                else if (grade >= 81 && grade <= 87) {
                    transcript += "BA";
                    variablaGPA = 3.5;
                }
                else if (grade >= 74 && grade <= 80) {
                    transcript += "BB";
                    variablaGPA = 3.0;

                }
                else if (grade >= 67 && grade <= 73) {
                    transcript += "CB";
                    variablaGPA = 2.5;

                }
                else if (grade >= 60 && grade <= 66) {
                    transcript += "CC";
                    variablaGPA = 2.0;

                }
                else if (grade >= 53 && grade <= 59) {
                    transcript += "DC";
                    variablaGPA = 1.5;
                }
                else if (grade >= 46 && grade <= 52) {
                    transcript += "DD";
                    variablaGPA = 1.0;
                }
                else if (grade >= 35 && grade <= 45) {
                    transcript += "FD";
                    variablaGPA = 0.5;
                }
                else {
                    transcript += "FF";
                    variablaGPA = 0.0;

                }
                transcript += "\n";
                gpa += variablaGPA * course.getAKTS();
                akts += course.getAKTS();
            }
            transcript += "GPA: - " + gpa / akts + "\n\n";
        }
        transcript += "Overall GPA: " + getGPA();
        return transcript;
    }

    @Override
    public String toString() {
        return super.toString() + "-GPA:" + getGPA();
    }
}

class GradStudent extends Student {
    private String thesis;
    private HashMap<GradStudent, Course> teachingAssistant = new HashMap<>();

    public GradStudent(String name, String email, long id,
                       Department department, String thesis) {
        super(name, email, id, department);
        this.thesis = thesis;
    }

    @Override
    public double courseGPAPoints(Course course) {
        Collection<HashMap<Course, Double>> arrayList = getX().values();
        boolean isContains = false;
        for (HashMap<Course, Double> list : arrayList) {
            if (list.containsKey(course)) {
                isContains = true;
                break;
            }
        }
        if (!isContains)
            throw new CourseNotFoundException(this, course);
        else {
            double grade = 0.0;
            for (HashMap<Course, Double> list : arrayList) {
                if (list.containsKey(course)) {
                    if (list.get(course) > grade) {
                        grade = list.get(course);
                    }
                }
            }
            if (grade >= 90 && grade <= 100)
                return 4.0;
            else if (grade >= 85 && grade <= 89)
                return 3.5;
            else if (grade >= 80 && grade <= 84)
                return 3.0;
            else if (grade >= 75 && grade <= 79)
                return 2.5;
            else if (grade >= 70 && grade <= 74)
                return 2.0;
            else
                return 0.0;
        }

    }

    @Override
    public String courseGradeLetter(Course course) {
        Collection<HashMap<Course, Double>> arrayList = getX().values();
        boolean isContains = false;
        for (HashMap<Course, Double> list : arrayList) {
            if (list.containsKey(course)) {
                isContains = true;
                break;
            }
        }
        if (!isContains)
            throw new CourseNotFoundException(this, course);
        else {
            double grade = 0.0;
            for (HashMap<Course, Double> list : arrayList) {
                if (list.containsKey(course)) {
                    if (list.get(course) > grade) {
                        grade = list.get(course);
                    }
                }
            }
            if (grade >= 90 && grade <= 100)
                return "AA";
            else if (grade >= 85 && grade <= 89)
                return "BA";
            else if (grade >= 80 && grade <= 84)
                return "BB";
            else if (grade >= 75 && grade <= 79)
                return "CB";
            else if (grade >= 70 && grade <= 74)
                return "CC";
            else
                return "FF";
        }

    }

    @Override
    public String courseResult(Course course) {
        Collection<HashMap<Course, Double>> arrayList = getX().values();
        boolean isContains = false;
        for (HashMap<Course, Double> list : arrayList) {
            if (list.containsKey(course)) {
                isContains = true;
                break;
            }
        }
        if (!isContains)
            throw new CourseNotFoundException(this, course);
        else {
            double grade = 0.0;
            for (HashMap<Course, Double> list : arrayList) {
                if (list.containsKey(course)) {
                    if (list.get(course) > grade) {
                        grade = list.get(course);
                    }
                }
            }
            if (grade >= 70 && grade <= 100)
                return "Passed";
            else
                return "Failed";
        }
    }

    public void setTeachingAssistant(Course course) {
        Collection<HashMap<Course, Double>> arrayList = getX().values();
            for (HashMap<Course, Double> list : arrayList) {
                if (list.containsKey(course)) {
                    if (list.get(course) >= 80) {
                        teachingAssistant.put(this, course);
                    } else {
                        throw new CourseNotFoundException(this, course);
                    }
                } else {
                    throw new CourseNotFoundException(this, course);
                }
            }
        }
    public Course getTeachingAssistant() {
        return teachingAssistant.get(this);
    }

    public String getThesis() {
        return this.thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Semester {
    private int season;
    private int year;

    public Semester(int season, int year) {
        this.year = year;
        if (season >= 1 && season <= 3) {
            this.season = season;
        }
    }

    public String getSeason() {
        if (this.season == 1) {
            return "Fall";
        } else if (this.season == 2) {
            return "Spring";
        } else {
            return "Summer";
        }
    }

    public int getYear() {
        return this.year;
    }

    @Override
    public String toString() {
        return getSeason() + "-" + this.year;
    }
}

public class Assignment04_20190808029 {
    public static void main(String[] args) {
        Department cse = new Department("CSE", "Computer Engineering");
        Teacher teacher = new Teacher("Joseph LEDET",
                "josephledet@akdeniz.edu.tr", 123L, cse, 3);
        System.out.println(teacher);
        Student stu = new Student("Assignment 4 STUDENT",
                "me@somewhere.com", 456L, cse);
        Semester s1 = new Semester(1, 2020);
        Course c101 = new Course(cse, 101, "Programming 1", 6,
                teacher);
        Semester s2 = new Semester(2,2021);
        Course c102 = new Course(cse, 102, "Programming 2", 4,
                teacher);
        Course c204 = new Course(cse, 204, "Database Systems",6
        ,teacher);

        stu.addCourse(c101, s1, 80);
        stu.addCourse(c102, s2, 30);
        stu.addCourse(c204, s2, 70);
        System.out.println("List Grades for CSE 101:\n" + stu.listGrades(c101));
        System.out.println("List Grades for Spring 2021:\n" +
                           stu.listGrades(s2));
        System.out.println("Student Transcript:\n" + stu.transcript());

        GradStudent gs = new GradStudent("Assignment 4 STUDENT",
                "me@somewhere.com", 789L, cse, "MDE");
        gs.addCourse(c101,s1,85);
        gs.addCourse(c102,s1,40);
        gs.setTeachingAssistant(c101);
        System.out.println("Teaching Assistant:\n" + gs.getTeachingAssistant());

    }
}
