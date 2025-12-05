import java.io.*;
import java.util.*;

public class Library {
    private HashMap<String, Book> books = new HashMap<>();
    private HashMap<String, Member> members = new HashMap<>();
    private final String booksFile = "books.txt";
    private final String membersFile = "members.txt";
    private final String transactionsFile = "transactions.txt";

    public Library() {
        loadBooks();
        loadMembers();
    }

    // ---------- Books ----------
    public boolean addBook(Book b) {
        if (books.containsKey(b.getId())) return false;
        books.put(b.getId(), b);
        saveBooks();
        log("ADD_BOOK", b.getId(), b.getTitle());
        return true;
    }

    public Book getBook(String id) {
        return books.get(id);
    }

    public List<Book> listBooks() {
        return new ArrayList<>(books.values());
    }

    public boolean deleteBook(String id) {
        Book b = books.remove(id);
        if (b == null) return false;
        saveBooks();
        log("DELETE_BOOK", id, b.getTitle());
        return true;
    }

    // ---------- Members ----------
    public boolean addMember(Member m) {
        if (members.containsKey(m.getMemberId())) return false;
        members.put(m.getMemberId(), m);
        saveMembers();
        log("ADD_MEMBER", m.getMemberId(), m.getName());
        return true;
    }

    public Member getMember(String memberId) {
        return members.get(memberId);
    }

    public List<Member> listMembers() {
        return new ArrayList<>(members.values());
    }

    // ---------- Issue / Return ----------
    public String issueBook(String bookId, String memberId) {
        Book b = books.get(bookId);
        if (b == null) return "Book not found!";
        if (b.isIssued()) return "Book already issued!";
        Member m = members.get(memberId);
        if (m == null) return "Member not found!";
        b.setIssued(true);
        saveBooks();
        log("ISSUE", bookId, memberId + " -> " + b.getTitle());
        return "Book issued to " + m.getName();
    }

    public String returnBook(String bookId, String memberId) {
        Book b = books.get(bookId);
        if (b == null) return "Book not found!";
        if (!b.isIssued()) return "Book is not issued!";
        Member m = members.get(memberId);
        if (m == null) return "Member not found!";
        b.setIssued(false);
        saveBooks();
        log("RETURN", bookId, memberId + " -> " + b.getTitle());
        return "Book returned by " + m.getName();
    }

    // ---------- Persistence ----------
    private void loadBooks() {
        File f = new File(booksFile);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Book b = Book.fromCSV(line);
                if (b != null) books.put(b.getId(), b);
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    private void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(booksFile))) {
            for (Book b : books.values()) {
                bw.write(b.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private void loadMembers() {
        File f = new File(membersFile);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Member m = Member.fromCSV(line);
                if (m != null) members.put(m.getMemberId(), m);
            }
        } catch (IOException e) {
            System.out.println("Error loading members: " + e.getMessage());
        }
    }

    private void saveMembers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(membersFile))) {
            for (Member m : members.values()) {
                bw.write(m.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving members: " + e.getMessage());
        }
    }

    private void log(String action, String id, String desc) {
        String time = new Date().toString();
        String line = time + " | " + action + " | " + id + " | " + desc;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionsFile, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing log: " + e.getMessage());
        }
    }

    public List<String> getTransactions(int limit) {
        List<String> all = new ArrayList<>();
        File f = new File(transactionsFile);
        if (!f.exists()) return all;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                all.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }
        Collections.reverse(all);
        if (limit > 0 && all.size() > limit) return all.subList(0, limit);
        return all;
    }
}
