package com.samansepahvand.cryptoapp.db.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = Users.TABLE_NAME)
public class Users extends  Model{

        public static final String TABLE_NAME = "Users";

        public static final String Id = "ids";
        public static final String UserName = "userName";
        public static final String Password = "password";
        public static final String FirstName = "firstName";
        public static final String LastName = "lastName";

        public static final String TotalAmountUSDT = "TotalAmountUSDT";

        public static final String RemainAmountUSDT = "RemainTotalAmountUSDT";



        @Column(name = Id)
          int ids;

        @Column(name = UserName)
           String userName;

        @Column(name = Password)
        String password;

        @Column(name = FirstName)
        String firstName;
        @Column(name = LastName)
        String lastName;

        @Column(name = TotalAmountUSDT)
        Double totalAmountUSDT;

        @Column(name = RemainAmountUSDT)
        Double remainAmountUSDT;

        public Double getRemainAmountUSDT() {
                return remainAmountUSDT;
        }

        public void setRemainAmountUSDT(Double remainAmountUSDT) {
                this.remainAmountUSDT = remainAmountUSDT;
        }

        public Double getTotalAmountUSDT() {
                return totalAmountUSDT;
        }


        public void setTotalAmountUSDT(Double totalAmountUSDT) {
                this.totalAmountUSDT = totalAmountUSDT;
        }

        public int getIds() {
                return ids;
        }

        public void setIds(int ids) {
                this.ids = ids;
        }

        public String getUserName() {
                return userName;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
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
}
