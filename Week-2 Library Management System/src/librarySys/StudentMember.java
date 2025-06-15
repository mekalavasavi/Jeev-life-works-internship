package librarySys;
// Subclass of "Member"
public class StudentMember extends Member {
    public StudentMember(String name, String email, String phone) {
        super(name, email, phone);
        this.maxBooksAllowed = 3;
    }

    // Implementing abstract methods
    public int getMaxAllowedDays() {
        return 14;
    }

    public String getMemberType() {
        return "Student";
    }
}
