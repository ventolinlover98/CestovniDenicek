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
        nezPojedu.add(new Items("Planost cestovního pasu/občasnkého průkazu", category, false));
        nezPojedu.add(new Items("Cestovní pojištění", category, false));
        nezPojedu.add(new Items("Léky a osobní hygiena", category, false));
        return nezPojedu;
    }

    public List<Items> getNouzoveKontakty(){
        String []data = {"Rodinné kontakty", "Pojišťovna", "Ambasáda", "Místní tísňová linka", "Kontakt na ubytování"};
        return prepareItemsList(Constants.NOUZOVE_KONTAKTY_CAMEL_CASE, data);
    }

    public List<Items> getDoprava(){
        String []data = {"Řidičský průkaz", "Doprava z letiště", "Telefonní číslo na taxi", "Připravená hototvost na mýto", "Parkování"};
        return prepareItemsList(Constants.DOPRAVA_CAMEL_CASE, data);
    }

    public List<Items> getUbytovani(){
        String []data = {"Adresa", "Kontakt", "Check-in"};
        return prepareItemsList(Constants.UBYTOVANI_CAMEL_CASE, data);
    }

    public List<Items> getRozpocet(){
        String []data = {"Hotovost v místní měně"};
        return prepareItemsList(Constants.ROZPOCET_CAMEL_CASE, data);
    }

    public List<Items> getCestovniDokumenty(){
        String []data = {"Pas", "Občasnký průkaz", "Cestovní pojištění", "Vizum"};
        return prepareItemsList(Constants.CESTOVNI_DOKUMENTY_CAMEL_CASE, data);
    }

    public List<Items> getRezervaceAVstupenky(){
        String []data = {"Památky", "Restaurace", "Program v okolí"};
        return prepareItemsList(Constants.REZERVACE_A_VSTUPENKY_CAMEL_CASE, data);
    }

    public List<Items> getSuvenyry(){
        String []data = {"Rodiče", "Kamarádi", "Magnetka do sbírky"};
        return prepareItemsList(Constants.SUVENYRY_CAMEL_CASE, data);
    }

    public List<Items> getRestauraceVOkoli(){
        String []data = {"Doporučené podniky", "Otevírací doby", "Jídla, které chci ochutnat"};
        return prepareItemsList(Constants.RESTAURACE_V_OKOLI_CAMEL_CASE, data);
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
