package librarySys;
// importing libraries
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class Book {
    private UUID bookID;
    private String title;
    private String author;
    private String genre;
    private boolean isIssued;
    private Member issuedTo;
    private LocalDate dueDate;
    private Queue<Member> reservationQueue;

    public Book(String title, String author, String genre) {
        this.bookID = UUID.randomUUID();
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isIssued = false;
        this.issuedTo = null;
        this.dueDate = null;
        this.reservationQueue = new LinkedList<>();
    }

    // getters and setters
    public UUID getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        this.isIssued = issued;
    }

    public Member getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(Member member) {
        this.issuedTo = member;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate date) {
        this.dueDate = date;
    }

    public Queue<Member> getReservationQueue() {
        return reservationQueue;
    }

    public void addToReservationQueue(Member member) {
        if (!reservationQueue.contains(member)) {
            reservationQueue.add(member);
        }
    }

    public Member getNextReservation() {
        return reservationQueue.poll();
    }
}
