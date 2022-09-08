package com.yx.elema;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ElemaServerApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123"));
    }

    @Test
    void test() {
        String path = "1";
        String[] menuIds = path.split(",");
        System.out.println(menuIds.length);
        for (String menuId : menuIds
             ) {
            System.out.println(menuId);
        }
    }

}
