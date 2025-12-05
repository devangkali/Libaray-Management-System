public class Member {
    private String memberId;
    private String name;
    private String email;

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    // CSV: memberId|name|email
    public String toCSV() {
        return escape(memberId) + "|" + escape(name) + "|" + escape(email);
    }

    public static Member fromCSV(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) return null;
        return new Member(unescape(parts[0]), unescape(parts[1]), unescape(parts[2]));
    }

    private static String escape(String s) {
        return s.replace("|", "&#124;");
    }
    private static String unescape(String s) {
        return s.replace("&#124;", "|");
    }

    @Override
    public String toString() {
        return "MemberID: " + memberId + " | Name: " + name + " | Email: " + email;
    }
}
