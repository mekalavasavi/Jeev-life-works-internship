package librarySys;
public class Librarian extends Member {

    public Librarian(String name, String email, String phone) {
        super(name, email, phone);
        this.maxBooksAllowed = Integer.MAX_VALUE; // no restriction
    }

    public int getMaxAllowedDays() {
        return 60; // librarians can override return deadlines
    }

    public String getMemberType() {
        return "Librarian";
    }
}