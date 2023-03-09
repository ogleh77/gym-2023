package com.example.gym.dto;

import com.example.gym.entities.service.Users;
import com.example.gym.helpers.CustomException;
import com.example.gym.models.UserModel;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class UsersService {

    private static UserModel userModel;

    static {
        System.out.println("User service called...");
        if (userModel == null) {
            userModel = new UserModel();
        }
    }

    public static void insertUser(Users user) throws CustomException {
        try {
            userModel.insert(user);
        } catch (SQLException e) {
            if (e.getMessage().contains("(UNIQUE constraint failed: users.username)")) {
                throw new CustomException("username-kan hore ayaa loo isticmalay fadlan dooro mid kale");
            } else {
                throw new CustomException("Khalad baa ka dhacay " + e.getMessage());
            }
        }
    }

    public static ObservableList<Users> users() throws SQLException {
        ObservableList<Users> users = null;
        try {
            users = userModel.fetch();
            //add default user
            users.add(new Users(0, "Luul", "Muuse", "4476619", "Male",
                    "Morning", "lulka **", "4000", null, "super admin"));
        } catch (SQLException e) {
            throw e;
        }
        return users;
    }

}
