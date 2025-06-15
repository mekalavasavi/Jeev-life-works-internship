package librarySys;
// subclass of "Member"
public class TeacherMember extends Member {
    public TeacherMember(String name, String email, String phone) {
        super(name, email, phone);
        this.maxBooksAllowed = 5;
    }

    //Implementing abstract methods
    public int getMaxAllowedDays() {
        return 30;
    }

    public String getMemberType() {
        return "Teacher";
    }
}