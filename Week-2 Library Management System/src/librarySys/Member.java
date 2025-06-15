package librarySys;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
// Creating abstract class Member
public abstract class Member {
    private UUID memberID;
    private String name;
    private String email;
    private String phone;
    protected int maxBooksAllowed;
    protected List<Book> currentlyIssuedBooks;

    public Member(String name, String email, String phone) {
        this.memberID = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.currentlyIssuedBooks = new ArrayList<>();
    }
    // getters 
    public UUID getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<Book> getCurrentlyIssuedBooks() {
        return currentlyIssuedBooks;
    }

    public boolean canIssueMoreBooks() {
        return currentlyIssuedBooks.size() < maxBooksAllowed;
    }

    public void issueBook(Book book) {
        currentlyIssuedBooks.add(book);
    }

    public void returnBook(Book book) {
        currentlyIssuedBooks.remove(book);
    }
    // abstract Methods
    public abstract int getMaxAllowedDays();
    public abstract String getMemberType();
}

