import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

// Enum to define the status of a task
enum TaskStatus {
    PENDING, IN_PROGRESS, COMPLETED
}

// Task Class
class Task {
    private int id;
    private String title;
    private String description;
    private String dueDate;
    private TaskStatus status;
    private int priority;

    public Task(int id, String title, String description, String dueDate, int priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = TaskStatus.PENDING;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public int getPriority() {
        return priority;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task ID: " + id + ", Title: \"" + title + "\", Description: " + description
                + ", Due Date: " + dueDate + ", Status: " + status + ", Priority: " + priority;
    }
}

// Task Management System Class
public class TaskManagementSystem {
    private ArrayList<Task> tasks;
    private Scanner scanner;
    private int taskIdCounter;

    public TaskManagementSystem() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
        taskIdCounter = 1; // Start task IDs from 1
    }

    public void start() {
        System.out.println("=== Welcome to the Task Management System ===");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new task");
            System.out.println("2. View all tasks");
            System.out.println("3. Update task status");
            System.out.println("4. Delete a task");
            System.out.println("5. Search tasks");
            System.out.println("6. Sort tasks by priority");
            System.out.println("7. Sort tasks by due date");
            System.out.println("8. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> updateTaskStatus();
                case 4 -> deleteTask();
                case 5 -> searchTasks();
                case 6 -> sortTasksByPriority();
                case 7 -> sortTasksByDueDate();
                case 8 -> {
                    System.out.println("Exiting... Thank you for using the system!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addTask() {
        System.out.print("Enter the title of the task: ");
        String title = scanner.nextLine();
        System.out.print("Enter the description of the task: ");
        String description = scanner.nextLine();
        System.out.print("Enter the due date (YYYY-MM-DD): ");
        String dueDate = scanner.nextLine();
        System.out.print("Enter the priority (1-5, where 1 is highest priority): ");
        int priority = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character

        Task newTask = new Task(taskIdCounter++, title, description, dueDate, priority);
        tasks.add(newTask);
        System.out.println("Task added successfully with ID: " + newTask.getId());
    }

    private void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            System.out.println("Tasks in the system:");
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private void updateTaskStatus() {
        System.out.print("Enter the ID of the task to update status: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character

        Task task = findTaskById(id);

        if (task == null) {
            System.out.println("Task not found!");
        } else {
            System.out.println("Current status: " + task.getStatus());
            System.out.println("Choose the new status:");
            System.out.println("1. Pending");
            System.out.println("2. In Progress");
            System.out.println("3. Completed");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            switch (choice) {
                case 1 -> task.setStatus(TaskStatus.PENDING);
                case 2 -> task.setStatus(TaskStatus.IN_PROGRESS);
                case 3 -> task.setStatus(TaskStatus.COMPLETED);
                default -> System.out.println("Invalid status choice.");
            }
            System.out.println("Task status updated.");
        }
    }

    private void deleteTask() {
        System.out.print("Enter the ID of the task to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character

        Task task = findTaskById(id);

        if (task == null) {
            System.out.println("Task not found!");
        } else {
            tasks.remove(task);
            System.out.println("Task removed successfully.");
        }
    }

    private void searchTasks() {
        System.out.print("Enter the title or description to search: ");
        String keyword = scanner.nextLine();
        boolean found = false;

        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(task);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks found with the given keyword.");
        }
    }

    private void sortTasksByPriority() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to sort.");
        } else {
            tasks.sort(Comparator.comparingInt(Task::getPriority));
            System.out.println("Tasks sorted by priority.");
        }
    }

    private void sortTasksByDueDate() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks to sort.");
        } else {
            tasks.sort(Comparator.comparing(Task::getDueDate));
            System.out.println("Tasks sorted by due date.");
        }
    }

    private Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        TaskManagementSystem system = new TaskManagementSystem();
        system.start();
    }
}
