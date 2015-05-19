package com.uk.greer.sdwapp.domain;

import java.util.List;

/**
 * Created by greepau on 17/05/2015.
 */
public class DataAccess {

    public static <T extends DomainObject> T findById(int id, List<T> list){

        for (T item:list){
            if ( item.getId()==id){
                return item;
            }
        }
        return null;
    }
}
