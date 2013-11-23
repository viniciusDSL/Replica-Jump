package com.vinicius.dsl.replicajump.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences {
     private static String name = "Geo";
     public static void putString(String tag,String valor,Context contexto){
       SharedPreferences pref = contexto.getSharedPreferences(name, Context.MODE_PRIVATE);
       Editor edit = pref.edit();
       edit.putString(tag, valor);
       edit.commit();
     }
     public static void putInt(String tag,int valor,Context contexto){
         SharedPreferences pref = contexto.getSharedPreferences(name, Context.MODE_PRIVATE);
         Editor edit = pref.edit();
         edit.putInt(tag, valor);
         edit.commit();
       }     
     public static void putBoolean(String tag,boolean valor,Context contexto){
         SharedPreferences pref = contexto.getSharedPreferences(name, Context.MODE_PRIVATE);
         Editor edit = pref.edit();
         edit.putBoolean(tag, valor);
         edit.commit();
       }
     public static String getString(String tag,Context contexto){
    	 SharedPreferences pref = contexto.getSharedPreferences(name, Context.MODE_PRIVATE);
    	 return pref.getString(tag, "nulo");
     }
     public static int getInt(String tag,Context contexto){
    	 SharedPreferences pref = contexto.getSharedPreferences(name, Context.MODE_PRIVATE);
    	 return pref.getInt(tag, 0);
     }
     public static boolean getBoolean(String tag,Context contexto){
    	 SharedPreferences pref = contexto.getSharedPreferences(name, Context.MODE_PRIVATE);
    	 return pref.getBoolean(tag, false);
     }
     
}
