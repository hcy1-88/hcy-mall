package com.hcy;

import org.junit.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2021/10/27 6:42
 */
@SpringBootTest
public class MyTest {

    @Test
    public void testPass(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String xx = "$2a$10$m4VcdFg.maOl3ufrrWUpteJhYyLxgU2607KZeXmd7apbunxvbDbFW";
        System.out.println(passwordEncoder.encode("123"));
//        System.out.println(passwordEncoder.matches("aaa", xx));

    }
}
