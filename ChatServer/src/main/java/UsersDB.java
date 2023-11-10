public class UsersDB
{
    private String firstName, lastName, userName,password;

    public UsersDB(String firstName , String lastName , String userName , String password)
    {
        this.lastName = lastName;
        this.userName = userName;
        this.firstName = firstName;
        this.password = password;
    }

    public boolean checkPassword(String password)
    {
        return this.password.equals(password);
    }

}

