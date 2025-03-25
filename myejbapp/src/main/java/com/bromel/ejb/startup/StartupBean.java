package com.bromel.ejb.startup;


import com.bromel.ejb.entities.User;
import com.bromel.ejb.service.UserService;
import com.bromel.ejb.utils.PasswordUtil;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
@Singleton
@Startup
public class StartupBean {

    @EJB
    private UserService userService;

    @PostConstruct
    public void init() {
        System.out.println(">>>>>>>>>");
        System.out.println("Inspecting default user......");
        System.out.println("<<<<<<<<<");

        // Kiểm tra xem user mặc định đã tồn tại chưa
        if (!userService.isUserExists("admin@gmail.com")) {
            System.out.println("This user do not exist, creating a new one...");

            User user = new User();
            user.setEmail("admin@gmail.com");


            user.setPassword(PasswordUtil.hashPassword("123456"));

            userService.createUser(user);

            System.out.println("Default user created!!!");
        } else {
            System.out.println("Default user already exists, do not create new one...");
        }
    }
}

