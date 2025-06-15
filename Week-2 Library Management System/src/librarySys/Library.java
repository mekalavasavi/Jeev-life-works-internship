package librarySys;
// importing libraries
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Library {
	//list of all books 
    private List<Book> books;
    //list of registered members
    private List<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }
    // Adding a new book to the library
    public void addBook(Book book) throws LibraryOperationException {
        for (Book b : books) {
            if (b.getBookID().equals(book.getBookID())) {
                throw new LibraryOperationException("Book with the same ID already exists.");
            }
        }
        books.add(book);
        System.out.println("Book added successfully.");
    }
    // removes a book from the library
    public void removeBook(Book book) throws LibraryOperationException {
        if (book.isIssued()) {
            throw new LibraryOperationException("Book is currently issued and cannot be removed.");
        }
        books.remove(book);
        System.out.println("Book removed successfully.");
    }
    // Registers a new member
    public void registerMember(Member member) throws LibraryOperationException {
        for (Member m : members) {
            if (m.getEmail().equalsIgnoreCase(member.getEmail()) ||
                m.getPhone().equals(member.getPhone())) {
                throw new LibraryOperationException("Member with the same email or phone already exists.");
            }
        }
        members.add(member);
        System.out.println("Member registered successfully.");
    }
    // Issues a book to member
    public void issueBook(Book book, Member member) throws LibraryOperationException {
        if (book.isIssued()) {
            throw new LibraryOperationException("Book is already issued.");
        }
        if (!member.canIssueMoreBooks()) {
            throw new LibraryOperationException(member.getMemberType() + " has reached their book limit.");
        }

        book.setIssued(true);
        book.setIssuedTo(member);
        LocalDate dueDate = LocalDate.now().plusDays(member.getMaxAllowedDays());
        book.setDueDate(dueDate);
        member.issueBook(book);

        System.out.println("Book issued to " + member.getName() + " until " + dueDate);
    }
    // returns book from member and reserves for next in queue
    public void returnBook(Book book, Member member) throws LibraryOperationException {
        if (!book.isIssued() || !book.getIssuedTo().equals(member)) {
            throw new LibraryOperationException("This member has not issued the book.");
        }

        member.returnBook(book);
        book.setIssued(false);
        book.setIssuedTo(null);
        book.setDueDate(null);
        System.out.println("Book returned successfully.");

        if (!book.getReservationQueue().isEmpty()) {
            Member next = book.getNextReservation();
            System.out.println("Book reserved by " + next.getName() + ". Issuing now...");
            issueBook(book, next);
        }
    }
    // adding a member to reservation queue for book
    public void reserveBook(Book book, Member member) {
        if (!book.isIssued()) {
            System.out.println("Book is available. No need to reserve.");
            return;
        }
        book.addToReservationQueue(member);
        System.out.println(member.getName() + " added to reservation queue.");
    }
    // Searching for a book by its title,author and genre
    public List<Book> searchBooks(String keyword) {
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                b.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                b.getGenre().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(b);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No matching books found.");
        } else {
            for (Book b : results) {
                System.out.println(b.getTitle() + " by " + b.getAuthor() +
                        " [" + (b.isIssued() ? "Issued" : "Available") + "]");
            }
        }
        return results;
    }
    // Viewing issued books
    public void viewIssuedBooks(Member member) {
        List<Book> issued = member.getCurrentlyIssuedBooks();
        if (issued.isEmpty()) {
            System.out.println(member.getName() + " has no books issued.");
            return;
        }

        for (Book b : issued) {
            long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), b.getDueDate());
            System.out.println(b.getTitle() + " | Due on: " + b.getDueDate() +
                    " | Days left: " + daysLeft);
        }
    }
    // displays all overdue books
    public void viewOverdueBooks() {
        boolean found = false;
        for (Book b : books) {
            if (b.isIssued() && b.getDueDate().isBefore(LocalDate.now())) {
                found = true;
                System.out.println("Book: " + b.getTitle() +
                        " | Issued to: " + b.getIssuedTo().getName() +
                        " | Due date: " + b.getDueDate());
            }
        }
        if (!found) {
            System.out.println("No overdue books.");
        }
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public List<Member> getAllMembers() {
        return members;
    }
}

