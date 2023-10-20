package com.samansepahvand.cryptoapp.db.repository;

import android.content.Context;

import com.activeandroid.query.Select;
import com.samansepahvand.cryptoapp.bussiness.OperationResult;
import com.samansepahvand.cryptoapp.db.model.Crypto;
import com.samansepahvand.cryptoapp.db.model.CryptoFav;
import com.samansepahvand.cryptoapp.metamodel.retrofit.Datum;

import java.util.List;

public class CryptoRepository {

    private static CryptoRepository cryptoRepository = null;

    private Context mContext;

    private static final String TAG = "cryptoRepository";

    private CryptoRepository() {

    }

    public static CryptoRepository getInstance() {
        if (cryptoRepository == null) {
            synchronized (CryptoRepository.class) {
                if (cryptoRepository == null) {
                    cryptoRepository = new CryptoRepository();
                }
            }
        }
        return cryptoRepository;
    }


    public List<Crypto> GetCrypto() {

        return new Select().from(Crypto.class).execute();
    }



    public OperationResult<Boolean> SaveCrypto(List<Datum> datum) {

        try{

            for (Datum d:datum ) {
                Crypto cryptoFavExist=new Select().from(Crypto.class).where("ids=?",d.getId()).executeSingle();
                if(cryptoFavExist!=null) continue;


                Crypto crypto= new Crypto();
                crypto.setIds(d.getId());
                crypto.setName(d.getName());
                crypto.setSymbol(d.getSymbol());
                crypto.save();


            }
            return OperationResult.Success("");

        }catch ( Exception e){
            return OperationResult.Failure(e.getMessage());

        }


    }

    public String GetCryptoFavForApi() {
        try{
        List<CryptoFav> list= new Select().from(CryptoFav.class).execute();

        StringBuilder builder=new StringBuilder();
        builder.append("id=");
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


}





