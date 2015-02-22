package com.uk.greer.sdwapp.domain;

import java.util.Date;

/**
 * Created by greepau on 18/02/2015.
 */
public class Participant {
    private String userName;
    private String firstName;
    private String lastName;
    private Date signUpDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }
}
