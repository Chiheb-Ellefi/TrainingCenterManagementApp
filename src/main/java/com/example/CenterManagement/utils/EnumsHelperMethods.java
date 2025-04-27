package com.example.CenterManagement.utils;

import com.example.CenterManagement.entities.user.Role;

public class EnumsHelperMethods {
    public static boolean isValidRole(Role role) {
        if (role == null) {
            return false;
        }
        for (Role r : Role.values()) {
            if (r == role) {
                return true;
            }
        }
        return false;
    }
}
