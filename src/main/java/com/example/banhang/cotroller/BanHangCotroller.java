package com.example.banhang.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BanHangCotroller {


    @GetMapping("/admin")
    public String hienThi(){
        return "index";
    }



}
