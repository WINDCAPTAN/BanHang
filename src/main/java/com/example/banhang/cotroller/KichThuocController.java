package com.example.banhang.cotroller;

import com.example.banhang.entity.KichThuoc;
import com.example.banhang.repository.KichThuocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;

@Controller
public class KichThuocController {

    @Autowired
    private KichThuocRepository kichThuocRepo;

    @GetMapping("/kich-thuoc")
    public String hienThi(Model model){
        model.addAttribute("listKT",kichThuocRepo.findAll());
        return "category/kichthuoc";
    }
    @PostMapping("/kich-thuoc/add")
    public String add(@RequestParam("ten")String ten){
        KichThuoc kichThuoc = new KichThuoc();
        kichThuoc.setTen(ten);
        kichThuoc.setTrangThai(true);
        kichThuoc.setNgayTao(Date.valueOf(LocalDate.now()));
        kichThuocRepo.save(kichThuoc);
        return "redirect:/kich-thuoc";
    }
}
