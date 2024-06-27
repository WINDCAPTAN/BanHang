package com.example.banhang.cotroller;


import com.example.banhang.repository.SanPhamChiTietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sanpham-chitiet")
public class SanPhamChiTietController {

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepo;

    @GetMapping
    public String hienThi(Model model){
        model.addAttribute("listSPCT",sanPhamChiTietRepo.findAll());
        return "category/sanphamchitiet";
    }
}
