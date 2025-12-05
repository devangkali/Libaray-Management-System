import java.util.*;

public class LibraryManagementSystem {
    private static Scanner sc = new Scanner(System.in);
    private static Library lib = new Library();

    public static void main(String[] args) {
        int choice;
        do {
            showMenu();
            choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: addBook(); break;
                case 2: viewBooks(); break;
                case 3: deleteBook(); break;
                case 4: addMember(); break;
                case 5: viewMembers(); break;
                case 6: issueBook(); break;
                case 7: returnBook(); break;
                case 8: viewTransactions(); break;
                case 9: System.out.println("Exiting... Bye!"); break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 9);
    }

    private static void showMenu() {
        System.out.println("\n===== LIBRARY MANAGEMENT =====");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Delete Book");
        System.out.println("4. Add Member");
        System.out.println("5. View Members");
        System.out.println("6. Issue Book");
        System.out.println("7. Return Book");
        System.out.println("8. View Recent Transactions");
        System.out.println("9. Exit");
    }

    private static void addBook() {
        System.out.println("\n--- Add Book ---");
        String id = readString("Enter Book ID (eg B001): ");
        String title = readString("Enter Title: ");
        String author = readString("Enter Author: ");
        Book b = new Book(id, title, author);
        if (lib.addBook(b)) System.out.println("Book added!");
        else System.out.println("Book ID already exists!");
    }

    private static void viewBooks() {
        System.out.println("\n--- All Books ---");
        List<Book> list = lib.listBooks();
        if (list.isEmpty()) System.out.println("No books found.");
        for (Book b : list) System.out.println(b);
    }

    private static void deleteBook() {
        System.out.println("\n--- Delete Book ---");
        String id = readString("Enter Book ID to delete: ");
        if (lib.deleteBook(id)) System.out.println("Deleted.");
        else System.out.println("Book not found.");
    }

    private static void addMember() {
        System.out.println("\n--- Add Member ---");
        String id = readString("Enter Member ID (eg M001): ");
        String name = readString("Enter Name: ");
        String email = readString("Enter Email: ");
        Member m = new Member(id, name, email);
        if (lib.addMember(m)) System.out.println("Member added!");
        else System.out.println("Member ID already exists!");
    }

    private static void viewMembers() {
        System.out.println("\n--- Members ---");
        List<Member> mlist = lib.listMembers();
        if (mlist.isEmpty()) System.out.println("No members.");
        for (Member m : mlist) System.out.println(m);
    }

    private static void issueBook() {
        System.out.println("\n--- Issue Book ---");
        String bookId = readString("Enter Book ID: ");
        String memberId = readString("Enter Member ID: ");
        System.out.println(lib.issueBook(bookId, memberId));
    }

    private static void returnBook() {
        System.out.println("\n--- Return Book ---");
        String bookId = readString("Enter Book ID: ");
        String memberId = readString("Enter Member ID: ");
        System.out.println(lib.returnBook(bookId, memberId));
    }

    private static void viewTransactions() {
        System.out.println("\n--- Recent Transactions ---");
        List<String> logs = lib.getTransactions(20);
        if (logs.isEmpty()) System.out.println("No transactions yet.");
        for (String l : logs) System.out.println(l);
    }

    // ----- helpers -----
    private static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
