package com.samansepahvand.cryptoapp.db.repository;

import android.content.Context;

import com.activeandroid.query.Select;
import com.samansepahvand.cryptoapp.bussiness.OperationResult;
import com.samansepahvand.cryptoapp.db.model.Crypto;
import com.samansepahvand.cryptoapp.db.model.CryptoFav;
import com.samansepahvand.cryptoapp.db.model.Users;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Datum;

import java.util.List;

public class UserRepository {

    private static UserRepository userRepository = null;

    private Context mContext;

    private static final String TAG = "cryptoRepository";

    private UserRepository() {

    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            synchronized (UserRepository.class) {
                if (userRepository == null) {
                    userRepository = new UserRepository();
                }
            }
        }
        return userRepository;
    }


    public OperationResult<Boolean> SaveUser(Users users) {

        try {

            Users userExist = new Select().from(Users.class).where("ids=?", users.getId()).executeSingle();

            if (userExist != null) return OperationResult.Failure("Failed to Create User !!");

            Users user = new Users();
            user = users;
            user.save();
            return OperationResult.Success("User Create Success .");


        } catch (Exception e) {
            return OperationResult.Failure(e.getMessage());

        }


    }


    public OperationResult<Users> GetUser(int userId) {

        try {

            Users userExist = new Select().from(Users.class).where("ids=?", userId).executeSingle();

            if (userExist != null) return  new OperationResult<Users>(null,true,null,userExist,null);

            return OperationResult.Failure("User not found !");


        } catch (Exception e) {
            return OperationResult.Failure(e.getMessage());

        }


    }
}





