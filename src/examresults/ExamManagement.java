package examresults;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Paths;

public class ExamManagement {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ExamService examService = new ExamService();
    private static final StudentService studentService = new StudentService();

        public static void main(String[] args) {

            /*
             *
             *
             * START INIT OF SAMPLE DATA*
             *
             *
             */

            try {

                //Init the lists
                List<Student> students = new ArrayList<>();
                List<Exam> exams = new ArrayList<>();
                List<EssayResult> essayResults = new ArrayList<>();
                List<MCResult> mcResults = new ArrayList<>();


                //Init some fake students
                for (int i = 0; i < 5; i++) {
                    students.add(new Student(12345 + i, "Student " + i));
                }


                //Initialize exams
                exams.add(new Essay("56789", "English", 120, 1500));
                exams.add(new Essay("56790", "History", 60, 750));
                exams.add(new MultipleChoice("67895", "Math", 90, 50));
                exams.add(new MultipleChoice("67896", "Science", 60, 30));


                //Add exams to Exam Service
                for (Exam exam : exams) {
                    examService.addExam(exam);
                }

                //Add students to Student Service
                for (Student student : students) {
                    studentService.addStudent(student);
                }


                //Initialize and add Essay and MC results for all students - sample answers
                for (Student student : students) {
                    for (Exam exam : exams) {
                        if (exam instanceof Essay) {
                            EssayResult essayResult = new EssayResult(student, exam, "Example essay answer.", 8, 85);
                            essayResults.add(essayResult);
                            student.addExamResult(essayResult);
                        } else if (exam instanceof MultipleChoice) {
                            MCResult mcResult = new MCResult(student, exam, 25);
                            mcResults.add(mcResult);
                            student.addExamResult(mcResult);
                        }
                    }
                }

            } catch (ExamException | StudentException e) {
                e.printStackTrace();
            }

            /*
             *
             *
             * END INIT OF SAMPLE DATA*
             *
             *
             */



            //Main menu loop
            boolean exit = false;

            while (!exit) {
                System.out.println("Welcome to Examify! Please choose an option:");
                System.out.println("1. Exam Directory");
                System.out.println("2. Student Directory");
                System.out.println("3. Exam Results");
                System.out.println("4. Exit");

                String input = scanner.nextLine();
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        handleExamService();
                        break;
                    case 2:
                        handleStudentService();
                        break;
                    case 3:
                        handleExamResults();
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }



        //Methods for handling different services (Exam, Student, Exam Results)
        //Each method contains a submenu for specific actions related to that service
        private static void handleExamService() {
            System.out.println("Exam Service:");
            System.out.println("a. List exams");
            System.out.println("b. Add exam");
            System.out.println("c. Back to Main Menu");
            System.out.println("\n NOTE: If you would like to remove an exam listed here, please contact your Administrator.");

            String choice = scanner.nextLine();

            switch (choice) {
                case "a":
                    System.out.println(examService);
                    ;
                    break;
                case "b":
                    addNewExam();
                    break;
                case "c":
                    break;
            }
        }

        //Add new exam
        private static void addNewExam() {
            System.out.println("Enter Exam Type (Essay/MultipleChoice):");
            String examType = scanner.nextLine();

            System.out.println("Enter Exam ID:");
            String examId = scanner.nextLine();

            System.out.println("Enter Subject:");
            String subject = scanner.nextLine();

            System.out.println("Enter Duration (in minutes):");
            int duration = scanner.nextInt();
            scanner.nextLine();

            try {
                if ("Essay".equalsIgnoreCase(examType)) {
                    System.out.println("Enter Word Limit for the Essay:");
                    int wordLimit = scanner.nextInt();
                    scanner.nextLine();
                    Exam essayExam = new Essay(examId, subject, duration, wordLimit);
                    examService.addExam(essayExam);
                } else if ("MultipleChoice".equalsIgnoreCase(examType)) {
                    System.out.println("Enter Number of Questions:");
                    int numQuestions = scanner.nextInt();
                    scanner.nextLine();
                    Exam mcExam = new MultipleChoice(examId, subject, duration, numQuestions);
                    examService.addExam(mcExam);
                } else {
                    System.out.println("Invalid exam type. Please try again.");
                    return; //Exit
                }
                System.out.println("Exam added successfully.");
            } catch (ExamException e) {
                System.out.println("Error adding exam: " + e.getMessage());
            }
        }

        //Menu for handling and viewing student information
        private static void handleStudentService() {
            System.out.println("Student Service:");
            System.out.println("a. View student directory");
            System.out.println("b. Add student to directory");
            System.out.println("c. See student record");
            System.out.println("d. Export results to CSV");
            System.out.println("e. Back to Main Menu");
            System.out.println("\n NOTE: If you would like to remove or modify an existing student record listed here, please contact your Administrator.");

            String choice = scanner.nextLine();

            switch (choice) {
                case "a":
                    System.out.println(studentService.studentIndex());
                    break;
                case "b":
                    addStudentToDirectory();
                    break;
                case "c":
                    searchStudentRecord();
                    break;
                case "d":
                    exportResultsToCsv();
                case "e":
                    break;
            }
        }

        //Adding student to directory
        private static void addStudentToDirectory() {
            System.out.println("Enter Student ID:");
            int studentId = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter Student Name:");
            String studentName = scanner.nextLine();

            try {
                Student newStudent = new Student(studentId, studentName);
                studentService.addStudent(newStudent);
                System.out.println("Student added successfully.");
            } catch (StudentException e) {
                System.out.println("Error adding student: " + e.getMessage());
            }
        }

        //Search for student
        private static void searchStudentRecord() {
            System.out.println("Search by:");
            System.out.println("1. Student ID");
            System.out.println("2. Student Name");

            int searchChoice = scanner.nextInt();
            scanner.nextLine();

            Student student = null;
            try {
                switch (searchChoice) {
                    case 1:
                        student = searchStudentById();
                        break;
                    case 2:
                        student = searchStudentByName();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        return;
                }

                if (student != null) {
                    System.out.println("Select result type:");
                    System.out.println("1. Detailed Results");
                    System.out.println("2. Summary Results");
                    int resultType = scanner.nextInt();
                    scanner.nextLine();

                    switch (resultType) {
                        case 1:
                            System.out.println(student.printDetailedResults());
                            break;
                        case 2:
                            System.out.println(student.printSummaryResults());
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                }
            } catch (StudentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        private static Student searchStudentById() throws StudentException {
            System.out.println("Enter Student ID:");
            int studentId = scanner.nextInt();
            scanner.nextLine();
            return studentService.getStudentById(studentId);
        }

        private static Student searchStudentByName() throws StudentException {
            System.out.println("Enter Student Name:");
            String name = scanner.nextLine();
            return studentService.getStudentByName(name);
        }

        //Export student record to CSV
        private static void exportResultsToCsv() {
            System.out.println("Enter Student ID for CSV Export:");
            int studentId = scanner.nextInt();
            scanner.nextLine();

            try {
                Student student = studentService.getStudentById(studentId);
                if (student != null) {
                    System.out.println("Enter directory path for saving CSV:");
                    String directoryPath = scanner.nextLine();

                    System.out.println("Enter filename for CSV (include .csv extension):");
                    String fileName = scanner.nextLine();

                    // Choose between summary or detailed results
                    System.out.println("Select result type:");
                    System.out.println("1. Detailed Results");
                    System.out.println("2. Summary Results");
                    int resultType = scanner.nextInt();
                    scanner.nextLine();

                    switch (resultType) {
                        case 1:
                            student.saveDetailedResultsToCsv(directoryPath, fileName);
                            break;
                        case 2:
                            student.saveSummaryResultsToCsv(directoryPath, fileName);
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                } else {
                    System.out.println("Student not found.");
                }
            } catch (StudentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }


        private static void handleExamResults() {
                    System.out.println("Exam Results:");
                    System.out.println("a. Add new exam result for an existing exam");
                    System.out.println("b. View all exam results");
                    System.out.println("c. View exam results by subject");
                    System.out.println("d. Export all exam results to text file");
                    System.out.println("e. Back to Main Menu");
                    System.out.println("\n NOTE: If you would like to modify or remove existing exam results listed here, please contact your Administrator.");

                    String choice = scanner.nextLine();

                    switch (choice) {
                        case "a":
                            addNewExamResult();
                            break;
                        case "b":
                            System.out.println(studentService.toString());
                            break;
                        case "c":
                            viewExamResultsBySubject();
                            break;
                        case "d":
                            exportAllResultsToCsv();
                            break;
                        case "e":
                            break;
                    }
                }

            //Add new exam results
            private static void addNewExamResult() {

            System.out.println("Enter Student ID:");
            int studentId = scanner.nextInt();
            scanner.nextLine();

            Student student;
            try {
                student = studentService.getStudentById(studentId);
            } catch (StudentException e) {
                System.out.println("Error: "+e.getMessage());
                return;
            }

            System.out.println("Enter Exam ID:");
            String examId = scanner.nextLine();

            Exam exam;
            try {
                exam = examService.getExamById(examId);
            } catch (ExamException e) {
                System.out.println("Error: "+e.getMessage());
                return;
            }

            try {
                if (exam instanceof Essay) {
                    addEssayResult(student, (Essay) exam);
                } else if (exam instanceof MultipleChoice) {
                    addMCResult(student, (MultipleChoice) exam);
                } else {
                    System.out.println("Invalid exam type.");
                }
            } catch (Exception e) {
                System.out.println("Error adding exam result: " + e.getMessage());
            }
        }

        private static void addEssayResult(Student student, Essay essay) {
            System.out.println("Enter Essay Answer:");
            String essayAnswer = scanner.nextLine();

            System.out.println("Enter Grammar Score:");
            int grammarScore = scanner.nextInt();

            System.out.println("Enter Content Score:");
            int contentScore = scanner.nextInt();
            scanner.nextLine();

            try {
                EssayResult essayResult = new EssayResult(student, essay, essayAnswer, grammarScore, contentScore);
                student.addExamResult(essayResult);
                System.out.println("Essay exam result added successfully.");
            } catch (ExamException e) {
                System.out.println("Error adding essay exam result: " + e.getMessage());
            }
        }

        private static void addMCResult(Student student, MultipleChoice mcExam) {
            System.out.println("Enter Number of Correct Answers:");
            int correctAnswers = scanner.nextInt();
            scanner.nextLine();

            try {
                MCResult mcResult = new MCResult(student, mcExam, correctAnswers);
                student.addExamResult(mcResult);
                System.out.println("Multiple choice exam result added successfully.");
            } catch (ExamException e) {
                System.out.println("Error adding multiple choice exam result: " + e.getMessage());
            }
        }

        private static void viewExamResultsBySubject() {
            System.out.println("Enter subject:");
            String subject = scanner.nextLine();

            try {
                examService.validateSubject(subject);
            } catch (ExamException e) {
                System.out.println("Error: " + e.getMessage());
                return; //Exit
            }

            boolean found = false;

            //Loop to search by student then by exam for matching subject
            for (Student student : studentService.getStudents()) {
                for (ExamResult result : student.getExamResults()) {
                    if (result.getExam().getSubject().equalsIgnoreCase(subject)) {
                        System.out.println(result);
                        found = true;
                    }
                }
            }

            if (!found) {
                System.out.println("No exam results found for the subject: " + subject);
            }
        }


        private static void exportAllResultsToCsv() {
            System.out.println("Enter directory path for saving file:");
            String directoryPath = scanner.nextLine();

            System.out.println("Enter filename (include .txt extension):");
            String fileName = scanner.nextLine();

            String csvContent = studentService.toString();

            try {
                CsvFileWriter.writeToCsvFile(directoryPath, fileName, csvContent);
                System.out.println("All exam results exported to: " + Paths.get(directoryPath, fileName));
            } catch (IOException e) {
                System.out.println("Failed to export results: " + e.getMessage());
                e.printStackTrace();
            }
        }


}