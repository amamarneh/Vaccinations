package com.amarnehsoft.vaccinations;

import android.content.ContentValues;
import android.content.Context;

import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.beans.Stock;
import com.amarnehsoft.vaccinations.beans.Vaccination;
import com.amarnehsoft.vaccinations.database.db2.DBCats;
import com.amarnehsoft.vaccinations.database.db2.DBCorCat;
import com.amarnehsoft.vaccinations.database.db2.DBCorporation;
import com.amarnehsoft.vaccinations.database.db2.DBKindergarten;
import com.amarnehsoft.vaccinations.database.db2.DBStock;
import com.amarnehsoft.vaccinations.database.db2.DBVaccination;

/**
 * Created by alaam on 2/11/2018.
 */

public class Dummy {
    public void add(Context context){
        Cat cat = new Cat();
        cat.setCode("c1");
        cat.setName("cat1");
        DBCats.getInstance(context).deleteAll();
        DBCats.getInstance(context).saveBean(cat);

        Corporation corporation = new Corporation();
        corporation.setCode("c1");
        corporation.setName("cor1");
        corporation.setDesc("desc");
        corporation.setAddress("ad");
        corporation.setContact("contant");

        DBCorporation.getInstance(context).deleteAll();
        DBCorporation.getInstance(context).saveBean(corporation);


        Kindergarten kindergarten = new Kindergarten();
        kindergarten.setCode("k1");
        kindergarten.setName("k");
        kindergarten.setToYear(2);
        kindergarten.setDescription("des");

        DBKindergarten.getInstance(context).deleteAll();
        DBKindergarten.getInstance(context).saveBean(kindergarten);

        Stock stock = new Stock();
        stock.setCode("s1");
        stock.setName("sto1");
        stock.setCorporationCode("c1");
        stock.setCatCode("c1");

        DBStock.getInstance(context).deleteAll();
        DBStock.getInstance(context).saveBean(stock);

        Vaccination vaccination = new Vaccination();
        vaccination.setCode("v1");
        vaccination.setName("v");
        vaccination.setAge(3);
        vaccination.setDesc("desc");
        vaccination.setNewAge(3);
        vaccination.setNotificationId(1);

        DBVaccination.getInstance(context).deleteAll();
        DBVaccination.getInstance(context).saveBean(vaccination);

        DBCorCat.getInstance(context).addBean("c1","c1","c1");
    }
}
