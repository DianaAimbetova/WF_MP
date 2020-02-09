package com.example.config;



import com.soapexample.xml.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Diana Aimbetova on 2/9/2020
 **/
@Component
public class UserService {
    private static final Map<String, User> users = new HashMap();

    @PostConstruct
    public void initData() {
        final User userOne = new User();
        userOne.setUserId("11");
        userOne.setUserName("Adam");
        userOne.setUserSurname("Smith");
        userOne.setMail("adam.smith@mail.com");
        final User userTwo = new User();
        userTwo.setUserId("22");
        userTwo.setUserName("James");
        userTwo.setUserSurname("Driver");
        userTwo.setMail("james.driver@mail.com");
        final User userThree = new User( );
        userThree.setUserId("33");
        userThree.setUserName("Kate");
        userThree.setUserSurname("Jackson");
        userThree.setMail("kate.jackson@mail.com");
        users.put(userOne.getUserId(), userOne);
        users.put(userTwo.getUserId(), userTwo);
        users.put(userThree.getUserId(), userThree);
    }

    public static User findUser(String userId) {
        return users.get(userId);
    }


}
