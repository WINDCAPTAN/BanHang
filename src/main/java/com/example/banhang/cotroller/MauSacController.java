package com.example.banhang.cotroller;


import com.example.banhang.entity.MauSac;
import com.example.banhang.repository.MauSacRepository;
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
public class MauSacController {

    @Autowired
    private MauSacRepository mauSacRepo;

    @GetMapping("/mau-sac")
    public String hienThiMauSac(Model model){
        model.addAttribute("listMS",mauSacRepo.findAll());
        return "category/mausac";
    }
    @PostMapping("/mau-sac/add")
    public String addMS(@RequestParam("ten") String ten){
        MauSac mauSac = new MauSac();
        mauSac.setTen(ten);
        mauSac.setTrangThai(true);
        mauSac.setNgayTao(Date.valueOf(LocalDate.now()));
        mauSacRepo.save(mauSac);
        return "redirect:/mau-sac";
    }
    @GetMapping("/mau-sac/delete/{id}")
    public String deleteMS(@PathVariable("id")Long id){
        mauSacRepo.deleteById(id);
        return "redirect:/mau-sac";
    }
}
