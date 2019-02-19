package statusboard;

public class CrewMemberObject {
    private int id = 0;
    private String rank;
    private String payGrade;
    private String firstName;
    private String lastName;
    private String department;
    private String barcode;
    private boolean status;

    public int getId() {
        return this.id;
    }

    public String getRank() {
        return this.rank;
    }

    public String getPayGrade()  {
        return this.payGrade;
    }
    
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public boolean isStatus() {
        return this.status;
    }

    public CrewMemberObject setId(int id) {
        this.id = id;
        return this;
    }

    public CrewMemberObject setRank(String rank) {
        this.rank = rank;
        return this;
    }

    public CrewMemberObject setPayGrade(String paygrade) {
        this.payGrade = paygrade;
        return this;
    }
    public CrewMemberObject setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CrewMemberObject setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CrewMemberObject setDepartment(String department) {
        this.department = department;
        return this;
    }

    public CrewMemberObject setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }

    public CrewMemberObject setStatus(boolean status) {
        this.status = status;
        return this;
    }
       
}