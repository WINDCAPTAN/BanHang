package com.example.banhang.cotroller;

import com.example.banhang.entity.SanPhamCT;
import com.example.banhang.repository.KichCoRepository;
import com.example.banhang.repository.MauSacRepository;
import com.example.banhang.repository.SanPhamCTRepository;
import com.example.banhang.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SanPhamCTController {

    @Autowired
    KichCoRepository kichCorepo;

    @Autowired
    MauSacRepository mauSacrepo;

    @Autowired
    SanPhamCTRepository sanPhamCTrepo;

    @Autowired
    SanPhamRepository sanPhamrepo;


    @GetMapping("/san-pham-ct")
    private String hienThi(Model model){
        model.addAttribute("listms", mauSacrepo.findAll());
        model.addAttribute("listkc", kichCorepo.findAll());
        model.addAttribute("listspct", sanPhamCTrepo.findAll());
        model.addAttribute("listsp", sanPhamrepo.findAll());
        return "sanphamct";
    }
    @PostMapping("/san-pham-add")
    private String addSanPhamCT(SanPhamCT sanPhamCT){
        sanPhamCTrepo.save(sanPhamCT);

        return "redirect:/san-pham-ct";
    }

}
