package com.easy.imc.clienteasyimc.models;

import com.easy.imc.clienteasyimc.entities.IMC;

public class API {
    public static String base = "http://localhost:8080";
    public static String imc = base+"/imc";
    public static String histories = base+"/histories";

    public static String categories = base+"/categories";

    public static String descriptions = base+"/descriptions";

    public static String conseils = base+"/conseils";
    public static String unitePoids = base+"/unite-poids";
    public static String uniteTaille = base+"/unite-taille";
    public static String init = base+"/init?reset=true";
    public static String users = base+"/users";

    public static String get = "GET";
    public static String post = "POST";
    public static String update = "UPDATE";

    public static  String getUrlForCalculIMC(){
        return imc;
    }

    public static String getUrlForAllHistories(Boolean recentlyAdded, int limit){
        return histories+"/all?recentlyAdded="+recentlyAdded+"&limit="+limit;
    }

    public static String getUrlForAllCategoriesCounts(){
        return categories+"/counts";
    }

    public static String getUrlForHistoryByMultiCriteria(){
        return histories+"/search";
    }

    public static String getUrlForLogin(){
        return users+"/login";
    }

    public static String getUrlUserUpdate(){
        return users+"/update";
    }

    public static String getUrlUserCreate(){
        return users+"/create";
    }



    public static String getUrlForGetAllUnitePoids(){
        return unitePoids+"/all";
    }

    public static String getUrlForGetAllCategories(){
        return categories+"/all";
    }

    public static String getUrlForGetAllUniteTaille(){
        return uniteTaille+"/all";
    }

    public static String getUrlFindByIdCategoryFromDescriptions(int id){
        return descriptions+"?idCategory="+id;
    }

    public static String getUrlFindByIdCategoryFromConseils(int id){
        return conseils+"?idCategory="+id;
    }
}
