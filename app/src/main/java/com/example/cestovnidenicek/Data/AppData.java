package com.example.cestovnidenicek.Data;

import android.app.Application;
import android.content.Context;

import com.example.cestovnidenicek.Constants.Constants;
import com.example.cestovnidenicek.Database.RoomDB;
import com.example.cestovnidenicek.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {

    RoomDB database;
    String category;
    Context context;
    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 1;

    public AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

    public List<Items> getNezPojedu(){
        category = "Než Pojedu";
        List<Items> nezPojedu = new ArrayList<Items>();
        nezPojedu.clear();
        nezPojedu.add(new Items("Příklad: Cestovní pojištění", category, false));
        return nezPojedu;
    }

    public List<Items> getNouzoveKontakty(){
        String []data = {"Příklad: Velvyslanectví České republiky:  00-31 (0)70 313 00 31"};
        return prepareItemsList(Constants.NOUZOVE_KONTAKTY_CAMEL_CASE, data);
    }

    public List<Items> getDoprava(){
        String []data = {"Příklad: Lístky na hromadnou dopravu v Amsterdamu (24 hodin) - €9,50"};
        return prepareItemsList(Constants.DOPRAVA_CAMEL_CASE, data);
    }

    public List<Items> getUbytovani(){
        String []data = {"Příklad: Hotel Fogo €80 na noc"};
        return prepareItemsList(Constants.UBYTOVANI_CAMEL_CASE, data);
    }

    public List<Items> getRozpocet(){
        String []data = {"Příklad: Denní rozpočet na jídlo: €40"};
        return prepareItemsList(Constants.ROZPOCET_CAMEL_CASE, data);
    }

    public List<Items> getCestovniDokumenty(){
        String []data = {"Příklad: Občasnký průkaz nebo pas"};
        return prepareItemsList(Constants.CESTOVNI_DOKUMENTY_CAMEL_CASE, data);
    }

    public List<Items> getRezervaceAVstupenky(){
        String []data = {"Příklad: Rijksmuseum €25"};
        return prepareItemsList(Constants.REZERVACE_A_VSTUPENKY_CAMEL_CASE, data);
    }

    public List<Items> getSuvenyry(){
        String []data = {"Příklad: Dárek pro rodinu"};
        return prepareItemsList(Constants.SUVENYRY_CAMEL_CASE, data);
    }

    public List<Items> getRestauraceVOkoli(){
        String []data = {"Příklad: Koya l Korean BBQ & Sushi"};
        return prepareItemsList(Constants.RESTAURACE_V_OKOLI_CAMEL_CASE, data);
    }

    public List<Items> getMujList(){
        String []data = {"Příklad: Powerbanka"};
        return prepareItemsList(Constants.MUJ_LIST_CAMEL_CASE, data);
    }

    public List<Items> prepareItemsList(String category, String[] data){
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();

        for(int i = 0; i < list.size(); i++){
            dataList.add(new Items(list.get(i), category, false));
        }
        return dataList;
    }

    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();
        listOfAllItems.add(getNezPojedu());
        listOfAllItems.add(getNouzoveKontakty());
        listOfAllItems.add(getDoprava());
        listOfAllItems.add(getUbytovani());
        listOfAllItems.add(getRozpocet());
        listOfAllItems.add(getCestovniDokumenty());
        listOfAllItems.add(getRezervaceAVstupenky());
        listOfAllItems.add(getSuvenyry());
        listOfAllItems.add(getRestauraceVOkoli());
        listOfAllItems.add(getMujList());
        return listOfAllItems;
    }

    public void persistAllData(){
       List<List<Items>> listOfAllItems = getAllData();
       for(List<Items>list: listOfAllItems){
           for(Items items: list){
               database.mainDao().saveItem(items);
           }
        }
       System.out.println("Data přidány");
    }
}
