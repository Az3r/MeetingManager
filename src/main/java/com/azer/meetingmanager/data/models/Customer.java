package com.azer.meetingmanager.data.models;

public class Customer extends User {
    /**
     * this function always returns false
     */
    @Override
    public boolean getIsAdmin() {
        return false;
    }

    /**
     * this function do nothing
     */
    @Override
    public void setIsAdmin(boolean isAdmin) {
    }

}