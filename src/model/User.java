package model;

public class User {
    /**
     * User ID
     */
    private int userId;
    /**
     * User Name
     */
    private String userName;
    /**
     * User Password
     */
    private String password;

    /**
     * User Constructor
     * @param userId User ID
     * @param userName User Name
     * @param password User's Password
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * @return User ID
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @return User Name
     */
    public String getUserName() {
        return userName;
    }
    /**
     * @return User's Passwords
     */
    public String getPassword() {
        return password;
    }


}


