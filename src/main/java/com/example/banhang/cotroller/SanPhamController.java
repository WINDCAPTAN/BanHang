package com.example.banhang.cotroller;

import com.example.banhang.entity.SanPham;
import com.example.banhang.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class SanPhamController {

    @Autowired
    private SanPhamRepository sanPhamRepo;

    @GetMapping("/san-pham")
    public String hienThi( @RequestParam(defaultValue = "0")int p , Model model){
        Pageable pageable = PageRequest .of(p,5);
        Page<SanPham> page = sanPhamRepo.findAll(pageable);
        model.addAttribute("listSP",page);
        model.addAttribute("trangHienTai",p);
        model.addAttribute("tongSoTrang", page.getTotalPages());
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
//    @GetMapping("/search")
//    public String search(@RequestParam("ten") String ten,
//                         @RequestParam("trangThai") Boolean trangThai,
//                         Model model){
//        model.addAttribute("listSP",sanPhamRepo.search(ten,trangThai));
//        return "sanpham";
//    }
//@GetMapping("/search")
//public String searchSanPham(@RequestParam(required = false) String ten,
//                            @RequestParam(required = false) Optional<Boolean> trangThai,
//                            @RequestParam(defaultValue = "3") int p,
//                            Model model) {
//        Pageable pageable = PageRequest.of(p,3);
//    Page<SanPham> page;
//    if ((ten == null || ten.isEmpty()) && !trangThai.isPresent()) {
//        page = sanPhamRepo.findAll(pageable);
//    } else {
//        page = sanPhamRepo.search(ten, trangThai.orElse(null), pageable);
//    }
//    model.addAttribute("listSP", pageable);
//    model.addAttribute("trangHienTai",p);
//    model.addAttribute("tongSoTrang",pageable);
//    return "sanpham";
//}
@GetMapping("/search")
public String searchSanPham(@RequestParam(required = false) String ten,
                            @RequestParam(required = false) Boolean trangThai,
                            @RequestParam(defaultValue = "0") int p,
                            Model model) {
    Pageable pageable = PageRequest.of(p, 5);
    Page<SanPham> page;
    if (ten == null && trangThai == null) {
        page = sanPhamRepo.findAll(pageable);
    } else {
        page = sanPhamRepo.search(
                ten != null && !ten.isEmpty() ? ten : null,
                trangThai,
                pageable
        );
    }
    model.addAttribute("listSP", page);
    model.addAttribute("trangHienTai", p);
    model.addAttribute("tongSoTrang", page.getTotalPages());
    return "sanpham";
}


}
