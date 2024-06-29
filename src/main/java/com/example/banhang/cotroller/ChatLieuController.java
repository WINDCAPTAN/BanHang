package com.example.banhang.cotroller;


import com.example.banhang.entity.ChatLieu;
import com.example.banhang.repository.ChatLieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;

@Controller
public class ChatLieuController {

    @Autowired
    private ChatLieuRepository chatLieuRepo;

    @GetMapping("/chat-lieu")
    public String hienThiChatLieu(Model model){
        model.addAttribute("listCL",chatLieuRepo.findAll());
        return "category/chatlieu";
    }

    @PostMapping("/chat-lieu/add")
    public String addChatLieu(@RequestParam("ten")String ten){
        ChatLieu chatLieu = new ChatLieu();
        chatLieu.setTen(ten);
        chatLieu.setTrangThai(true);
        chatLieu.setNgayTao(Date.valueOf(LocalDate.now()));
        chatLieuRepo.save(chatLieu);
        return "redirect:/chat-lieu";
    }
    @GetMapping("/chat-lieu/delete/{id}")
    public String deleteCL(@PathVariable("id")Long id){
        chatLieuRepo.deleteById(id);
        return "redirect:/chat-lieu";
    }
}
