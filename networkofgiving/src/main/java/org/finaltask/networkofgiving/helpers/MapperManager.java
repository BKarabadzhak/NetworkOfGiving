package org.finaltask.networkofgiving.helpers;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.finaltask.networkofgiving.dtos.request.CharityRequest;
import org.finaltask.networkofgiving.dtos.request.CharityToAdd;
import org.finaltask.networkofgiving.dtos.request.CustomerToLogin;
import org.finaltask.networkofgiving.dtos.request.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class MapperManager {

    private MapperFactory mapperFactory;

    public MapperManager() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
        this.mapperFactory.classMap(LoginRequest.class, CustomerToLogin.class).
                mapNulls(false).
                mapNullsInReverse(false).
                byDefault().
                //http://eloquentdeveloper.com/2016/09/28/automatically-mapping-java-objects/
                //field("address.id", "addressId").
                        register();
        this.mapperFactory.classMap(CharityRequest.class, CharityToAdd.class).
                mapNulls(false).
                mapNullsInReverse(false).
                byDefault().
                //http://eloquentdeveloper.com/2016/09/28/automatically-mapping-java-objects/
                //field("address.id", "addressId").
                        register();
    }

    public <S, D> D map(S s, Class<D> type) {
        return this.mapperFactory.getMapperFacade().map(s, type);
    }
}
