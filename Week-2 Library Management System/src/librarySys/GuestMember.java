package librarySys;
// Subclass of "Member"
public class GuestMember extends Member {
    public GuestMember(String name, String email, String phone) {
        super(name, email, phone);
        this.maxBooksAllowed = 1;
    }
    
    // Implementing abstract methods
    public int getMaxAllowedDays() {
        return 7;
    }
    public String getMemberType() {
        return "Guest";
    }
}