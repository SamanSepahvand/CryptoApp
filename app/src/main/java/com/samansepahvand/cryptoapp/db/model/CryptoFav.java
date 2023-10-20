package com.samansepahvand.cryptoapp.db.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = CryptoFav.TABLE_NAME)
public class CryptoFav extends  Model{

        public static final String TABLE_NAME = "CryptoFav";

        public static final String Id = "ids";
        public static final String Symbol = "symbol";
        public static final String Name = "name";


        @Column(name = Id)
          int ids;

        @Column(name = Symbol)
           String symbol;

        @Column(name = Name)
            String name;


        public int getIds() {
                return ids;
        }

        public void setIds(int ids) {
                this.ids = ids;
        }

        public String getSymbol() {
                return symbol;
        }

        public void setSymbol(String symbol) {
                this.symbol = symbol;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
}
