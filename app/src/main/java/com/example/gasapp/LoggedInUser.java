package com.example.gasapp;

/*
 * Created by: Adam Basham
 * Horrible logged in user solution that works good enough for a demo app
 */

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class LoggedInUser {
    private static String firstName;
    private static String lastName;
    private static String emailAddress;
    private static String postalAddress;
    private static String phoneNumber;
    private static String password;
    private static boolean isEmployee;

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName, Context ctx, boolean shouldUpdate) {
        LoggedInUser.firstName = firstName;

        if(shouldUpdate) {
            updateUser(ctx);
        }
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName, Context ctx, boolean shouldUpdate) {
        LoggedInUser.lastName = lastName;

        if(shouldUpdate) {
            updateUser(ctx);
        }
    }

    public static String getEmailAddress() {
        return emailAddress;
    }

    public static void setEmailAddress(String emailAddress, Context ctx, boolean shouldUpdate) {
        LoggedInUser.emailAddress = emailAddress;

        if(shouldUpdate) {
            updateUser(ctx);
        }
    }

    public static String getPostalAddress() {
        return postalAddress;
    }

    public static void setPostalAddress(String postalAddress, Context ctx, boolean shouldUpdate) {
        LoggedInUser.postalAddress = postalAddress;

        if(shouldUpdate) {
            updateUser(ctx);
        }
    }

    public static boolean isEmployee() {
        return isEmployee;
    }

    public static void setIsEmployee(boolean isEmployee, Context ctx, boolean shouldUpdate) {
        LoggedInUser.isEmployee = isEmployee;

        if(shouldUpdate) {
            updateUser(ctx);
        }
    }

    public static String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setPhoneNumber(String phoneNumber, Context ctx, boolean shouldUpdate) {
        LoggedInUser.phoneNumber = phoneNumber;

        if(shouldUpdate) {
            updateUser(ctx);
        }
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password, Context ctx, boolean shouldUpdate) {
        LoggedInUser.password = password;

        if(shouldUpdate) {
            updateUser(ctx);
        }
    }

    private static JSONArray getUsersArray(Context ctx) {
        File file = new File(ctx.getExternalFilesDir("/app"), "users.json");
        if(!file.exists()) {
            toast("Error retrieving user!", ctx);
        }

        try {
            return new JSONObject(JSONUtilities.getStringFromFile(file)).getJSONArray("users");
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static int getUserIndex(JSONArray usersArray) {
        try {
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject obj = (JSONObject) usersArray.get(i);

                if(obj.getString("email").equals(emailAddress)) {
                    return i;
                }
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private static void updateUser(Context ctx) {
        try {
            JSONArray usersArray = getUsersArray(ctx);
            int userIndex = getUserIndex(usersArray);

            if(userIndex == -1) {
                toast("Error retrieving/validating user!", ctx);
                return;
            }

            File file = new File(ctx.getExternalFilesDir("/app"), "users.json");
            if(!file.exists()) {
                toast("Error retrieving user!", ctx);
            }

            JSONObject obj = new JSONObject();
            obj.put("firstName", firstName);
            obj.put("lastName", lastName);
            obj.put("email", emailAddress);
            obj.put("address", postalAddress);
            obj.put("password", password);
            obj.put("phone", phoneNumber);
            obj.put("isEmployee", isEmployee);

            //Replace user at array index with "new" one
            usersArray.put(userIndex, obj);

            //Re-write new array to file
            JSONObject obj2 = new JSONObject();
            obj2.put("users", usersArray);

            JSONUtilities.writeJsonFile(file, obj2.toString());
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    private static void toast(String message, Context ctx) {
        Toast t = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        t.show();
    }
}
