package com.example.banhang.cotroller;

import com.example.banhang.repository.ChatLieuRepository;
import com.example.banhang.repository.KichThuocRepository;
import com.example.banhang.repository.MauSacRepository;
import com.example.banhang.repository.SanPhamRepository;
import com.example.banhang.repository.ThuongHieuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SanPhamController {

    @Autowired
    private SanPhamRepository sanPhamRepo;

    @Autowired
    private ChatLieuRepository chatLieuRepo;

    @Autowired
    private KichThuocRepository kichThuocRepo;

    @Autowired
    private MauSacRepository mauSacRepo;

    @Autowired
    private ThuongHieuRepository thuongHieuRepo;

    @GetMapping("/san-pham")
    public String hienThi(Model model){
        model.addAttribute("listSP",sanPhamRepo.findAll());
        return "category/sanpham";
    }
    @GetMapping("/san-pham/view-add")
    public String hienThiAdd(Model model){
        model.addAttribute("listCL",chatLieuRepo.findAll());
        model.addAttribute("listMS",mauSacRepo.findAll());
        model.addAttribute("listKT",kichThuocRepo.findAll());
        model.addAttribute("listTH",thuongHieuRepo.findAll());
        return "sanphamchitiet";
    }


}
