public class Book {
    private String id;
    private String title;
    private String author;
    private boolean issued;

    public Book(String id, String title, String author, boolean issued) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issued = issued;
    }

    public Book(String id, String title, String author) {
        this(id, title, author, false);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return issued; }

    public void setIssued(boolean issued) { this.issued = issued; }

    // CSV format: id|title|author|issued
    public String toCSV() {
        return escape(id) + "|" + escape(title) + "|" + escape(author) + "|" + (issued ? "1" : "0");
    }

    public static Book fromCSV(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 4) return null;
        return new Book(unescape(parts[0]), unescape(parts[1]), unescape(parts[2]), parts[3].equals("1"));
    }

    private static String escape(String s) {
        return s.replace("|", "&#124;");
    }
    private static String unescape(String s) {
        return s.replace("&#124;", "|");
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Title: " + title + " | Author: " + author + " | Issued: " + (issued ? "Yes" : "No");
    }
}
