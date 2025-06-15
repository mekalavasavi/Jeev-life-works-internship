package librarySys;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        try {
            // Creating  members
            Member student = new StudentMember("Tom", "tom@example.com", "1234567890");
            Member teacher = new TeacherMember("Jack", "jack@example.com", "9988776655");
            Member guest = new GuestMember("william", "william@example.com", "7856789091");

            library.registerMember(student);
            library.registerMember(teacher);
            library.registerMember(guest);

            // Creating  books
            Book book1 = new Book("The Last Mandate", "Cyrus Elwood", "History");
            Book book2 = new Book("Debugging the Mind", "Kira Holmes", "Software Engineering");
            Book book3 = new Book("World History", "Michael King", "History");

            library.addBook(book1);
            library.addBook(book2);
            library.addBook(book3);

            // Issuing book to student
            library.issueBook(book1, student);

            // Trying to issue same book to teacher
            try {
                library.issueBook(book1, teacher);
            } catch (LibraryOperationException e) {
                System.out.println("Expected Error: " + e.getMessage());
            }

            // Reserving a book for teacher 
            library.reserveBook(book1, teacher);
            
            // Viewing books issued to student
            System.out.println("\nBooks issued to " + student.getName() + ":");
            library.viewIssuedBooks(student);

            // Return book from student 
            library.returnBook(book1, student);

            // Searching for book by keyword
            System.out.println("\nSearch results for keyword 'Debugging':");
            library.searchBooks("Debugging");

            // Viewing overdue books 
            System.out.println("\nOverdue books:");
            library.viewOverdueBooks();

        } catch (LibraryOperationException e) {
            System.out.println("Library Error: " + e.getMessage());
        }
    }
}


