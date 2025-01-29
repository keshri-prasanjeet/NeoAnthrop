package com.keshrixlabs.whatsappclone.user;

public enum UserConstants {
    FIND_USER_BY_EMAIL("Users.findUserByEmail"),
    FIND_USERS_EXCEPT_SELF("Users.findUsersExceptSelf"),
    FIND_USER_BY_PUBLIC_ID("Users.findUserByPublicId");

    private final String query;

    UserConstants(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
