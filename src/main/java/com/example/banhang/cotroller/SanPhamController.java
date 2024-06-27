package com.example.banhang.cotroller;

import com.example.banhang.entity.SanPham;
import com.example.banhang.repository.SanPhamRepository;
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
public class SanPhamController {

    @Autowired
    private SanPhamRepository sanPhamRepo;
// test
    @GetMapping("/san-pham")
    public String hienThi(Model model){
        model.addAttribute("listSP",sanPhamRepo.findAll());
        return "sanpham";
    }
    @PostMapping("/san-pham/add")
    public String add(@RequestParam("ma")String ma,
                      @RequestParam("ten")String ten){
        SanPham sanPham = new SanPham();
        sanPham.setMa(ma);
        sanPham.setTen(ten);
        sanPham.setSoLuong(0);
        sanPham.setTrangThai(true);
        sanPham.setNgayTao(Date.valueOf(LocalDate.now()));
        sanPhamRepo.save(sanPham);
        return "redirect:/san-pham";
    }
    @GetMapping("/san-pham/delete/{id}")
    public String xoa(@PathVariable("id")Long id){
        sanPhamRepo.deleteById(id);
        return "redirect:/san-pham";
    }
}
