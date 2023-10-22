package com.samansepahvand.cryptoapp.db.repository;

import android.content.Context;
import android.os.Message;

import com.activeandroid.query.Select;
import com.samansepahvand.cryptoapp.bussiness.OperationResult;
import com.samansepahvand.cryptoapp.db.model.CryptoFav;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Datum;


import java.util.List;

public class CryptoFavRepository {

    private static CryptoFavRepository cryptoFavRepository = null;

    private Context mContext;

    private static final String TAG = "cryptoFavRepository";

    private CryptoFavRepository() {

    }

    public static CryptoFavRepository getInstance() {
        if (cryptoFavRepository == null) {
            synchronized (CryptoFavRepository.class) {
                if (cryptoFavRepository == null) {
                    cryptoFavRepository = new CryptoFavRepository();
                }
            }
        }
        return cryptoFavRepository;
    }

    public List<CryptoFav> GetCryptoFav() {

        return new Select().from(CryptoFav.class).execute();
    }

    public OperationResult<Boolean> SaveCrypto(Datum datum) {

        try{

            CryptoFav cryptoFavExist=new Select().from(CryptoFav.class).where("ids=?",datum.getId()).executeSingle();
            if(cryptoFavExist!=null) return OperationResult.Failure("This coin was already saved!");

            CryptoFav cryptoFav= new CryptoFav();
            cryptoFav.setIds(datum.getId());
            cryptoFav.setName(datum.getName());
            cryptoFav.setSymbol(datum.getSymbol());
            cryptoFav.save();

            return OperationResult.Success("");
        }catch ( Exception e){
            return OperationResult.Failure(e.getMessage());

        }


    }

    public String GetCryptoFavForApi() {
        try{
        List<CryptoFav> list= new Select().from(CryptoFav.class).execute();

        StringBuilder builder=new StringBuilder();
        builder.append("");
        int count=0;
            for (CryptoFav item:list) {
                builder.append(item.getIds()+"");
                count++;
                if (count!=list.size())builder.append(",");

            }
            return builder.toString();
        }catch ( Exception e){
            return null;

        }
    }


    public OperationResult IsFavCrypto(Datum datum) {
        try{

            CryptoFav cryptoFavExist=new Select().from(CryptoFav.class).where("ids=?",datum.getId()).executeSingle();
            if(cryptoFavExist!=null) return OperationResult.Success("");
            return OperationResult.Failure("");

        }catch ( Exception e){
            return OperationResult.Failure(e.getMessage());

        }

    }

    public OperationResult<Boolean> RemoveCryptoFav(Datum datum) {

        try{

            CryptoFav cryptoFavExist=new Select().from(CryptoFav.class).where("ids=?",datum.getId()).executeSingle();
            if(cryptoFavExist!=null) {
                cryptoFavExist.delete();
                return OperationResult.Success("");
            }
            return OperationResult.Failure("");

        }catch ( Exception e){
            return OperationResult.Failure(e.getMessage());

        }

    }
}





