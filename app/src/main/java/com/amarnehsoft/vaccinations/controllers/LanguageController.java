package com.amarnehsoft.vaccinations.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Amarneh on 1/20/2017.
 */

public class LanguageController {

    public static final String LANGUAGE = "LANGUAGE";
    public static final String ARABIC = "ARABIC";
    public static final String ENGLISH = "ENGLISH";

    public static void arabic(Context ctx){
        changeLang(ARABIC,ctx);
    }
    public static void english(Context ctx){
        changeLang(ENGLISH,ctx);
    }

    public static String getDefaultLanguage()
    {
        Locale locale = Locale.getDefault();
        if(locale == Locale.ENGLISH){
            return ENGLISH;
        }
        return ARABIC;
    }

    public static void refreshLang(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(LANGUAGE, MODE_PRIVATE);
        String lang = sharedPrefs.getString(LANGUAGE, null);
        if(lang != null){
            changeLang(lang,context);
        }else{
            changeLang(getDefaultLanguage(),context);
        }
    }

    private static Locale getLocale(String lang){
        switch (lang){
            case ARABIC:
                return new Locale("ar");
            case ENGLISH:
                return Locale.ENGLISH;
        }
        return null;
    }
    public static void changeLang(String languageToLoad, Context context) {
        Locale locale = getLocale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, context.getApplicationContext().getResources().getDisplayMetrics());
        SharedPreferences languagepref = context.getSharedPreferences(LANGUAGE, MODE_PRIVATE);
        SharedPreferences.Editor editor = languagepref.edit();
        editor.putString(LANGUAGE, languageToLoad);
        editor.commit();
    }
}
