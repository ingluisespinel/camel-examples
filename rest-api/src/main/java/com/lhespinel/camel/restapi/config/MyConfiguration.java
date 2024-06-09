package com.lhespinel.camel.restapi.config;

import com.lhespinel.camel.restapi.components.MyBean;
import com.lhespinel.camel.restapi.repository.FakeUsersRepository;
import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.PropertyInject;

/**
 * Class to configure the Camel application.
 */
@Configuration
public class MyConfiguration {

    @BindToRegistry
    public MyBean myBean(@PropertyInject("hi") String hi, @PropertyInject("bye") String bye) {
        // this will create an instance of this bean with the name of the method (eg myBean)
        return new MyBean(hi, bye);
    }

    @BindToRegistry("fakeUsersRepository")
    public FakeUsersRepository usersRepository(){
        return new FakeUsersRepository();
    }

}
