package com.example.gym.dto.services;

import com.example.gym.entities.service.Users;
import com.example.gym.helpers.CustomException;
import com.example.gym.models.services.UserModel;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class UsersService {

    private static UserModel userModel;
    private static ObservableList<Users> users = null;

    static {
        System.out.println("User service called...");
        if (userModel == null) {
            userModel = new UserModel();
        }
    }

    public static void insertUser(Users user) throws SQLException {
        try {
            userModel.insert(user);
        } catch (SQLException e) {
            if (e.getMessage().contains("(UNIQUE constraint failed: users.username")) {
                throw new CustomException("username ka " + user.getUsername() + " horaa lo isticmalay");
            } else if (e.getMessage().contains("(UNIQUE constraint failed: users.phone")) {
                throw new CustomException("lanbar ka " + user.getPhone() + " horaa lo isticmalay");
            } else {
                throw e;
            }
        }
    }

    public static void update(Users user) throws SQLException {
        try {
            userModel.update(user);
        } catch (SQLException e) {
            if (e.getMessage().contains("(UNIQUE constraint failed: users.username")) {
                throw new CustomException("username ka " + user.getUsername() + " horaa lo isticmalay");
            } else if (e.getMessage().contains("(UNIQUE constraint failed: users.phone")) {
                throw new CustomException("lanbar ka " + user.getPhone() + " horaa lo isticmalay");
            } else {
                throw e;
            }
        }
    }

    public static ObservableList<Users> users() throws SQLException {
        if (users == null) {
            users = userModel.fetch();
            users.add(new Users(0, "Luul", "Muuse", "4476619", "Male",
                    "Morning", "lulka **", "4000", null, "super admin"));
        }

        return users;
    }


    //--------------------Helpers------------------
    private static int userIndex(ObservableList<Users> users, int low, int high, int userId) {
        //if array is in order then perform binary search on the array
        if (high >= low) {
            //calculate mid
            int mid = low + (high - low) / 2;
            //if key =intArray[mid] return mid
            if (users.get(mid).getUserId() == userId) {
                return mid;
            }
            //if intArray[mid] > key then key is in left half of array
            if (users.get(mid).getUserId() > userId) {
                return userIndex(users, low, mid - 1, userId);//recursively search for key
            } else       //key is in right half of the array
            {
                return userIndex(users, mid + 1, high, userId);//recursively search for key
            }
        }
        return -1;
    }
}
