package com.example.banhang.cotroller;

import com.example.banhang.entity.KichThuoc;
import com.example.banhang.entity.MauSac;
import com.example.banhang.entity.SanPham;
import com.example.banhang.entity.SanPhamChiTiet;
import com.example.banhang.repository.ChatLieuRepository;
import com.example.banhang.repository.KichThuocRepository;
import com.example.banhang.repository.MauSacRepository;
import com.example.banhang.repository.SanPhamChiTietRepository;
import com.example.banhang.repository.SanPhamRepository;
import com.example.banhang.repository.ThuongHieuRepository;
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

    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepo;

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
    @GetMapping("/san-pham/add-chitiet/{id}")
    public String hienThiAdd(@PathVariable("id") Long id, Model model) {
        SanPham sanPham = sanPhamRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        model.addAttribute("listUD", sanPham);
        model.addAttribute("listCL", chatLieuRepo.findAll());
        model.addAttribute("listMS", mauSacRepo.findAll());
        model.addAttribute("listTH", thuongHieuRepo.findAll());
        model.addAttribute("listKT", kichThuocRepo.findAll());
        // Lấy ra chỉ các sản phẩm chi tiết của sản phẩm có id tương ứng
        model.addAttribute("listSPCT", sanPhamChiTietRepo.findBySanPham(sanPham));
        return "category/sanphamchitiet";
    }


    @PostMapping("/san-pham/add")
    public String addSanPham(@RequestParam("ma")String ma,
                             @RequestParam("ten")String ten){
        SanPham sanPham = new SanPham();
        sanPham.setTen(ten);
        sanPham.setMa(ma);
        sanPham.setSoLuong(0);
        sanPham.setTrangThai(true);
        sanPham.setNgayTao(Date.valueOf(LocalDate.now()));
        sanPhamRepo.save(sanPham);
        return "redirect:/san-pham";
    }
    @PostMapping("/sanpham-chitiet/add")
    public String addSanPhamCT(@RequestParam("sanPham") Long idSP,
                               @RequestParam("kichCo") Long idKC,
                               @RequestParam("mauSac") Long idMS,
                               @RequestParam("moTa") String moTa,
                               @RequestParam("soLuong") Integer soLuong,
                               @RequestParam("giaBan") Double giaBan,
                               @RequestParam("maVach") String maVach,
                               @RequestParam("hinhAnh") String hinhAnh) {
        // Retrieve SanPham, KichCo, and MauSac from repositories based on ids
        SanPham sanPham = sanPhamRepo.findById(idSP).orElseThrow(() -> new IllegalArgumentException("Invalid SanPham ID: " + idSP));
        KichThuoc kichCo = kichThuocRepo.findById(idKC).orElseThrow(()->new IllegalArgumentException("Invalid SanPham ID: " + idKC));
        MauSac mauSac = mauSacRepo.findById(idMS).orElseThrow(() -> new IllegalArgumentException("Invalid MauSac ID: " + idMS));

        // Create new SanPhamChiTiet object and set its properties
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        sanPhamChiTiet.setSanPham(sanPham);
        sanPhamChiTiet.setKichCo(kichCo);
        sanPhamChiTiet.setMauSac(mauSac);
        sanPhamChiTiet.setMoTa(moTa);
        sanPhamChiTiet.setSoLuong(soLuong);
        sanPhamChiTiet.setGiaBan(giaBan);
        sanPhamChiTiet.setMaVach(maVach);
        sanPhamChiTiet.setHinhAnh(hinhAnh);
        sanPhamChiTiet.setNgayTao(new Date(System.currentTimeMillis())); // Set current date

        // Save SanPhamChiTiet object using repository
        sanPhamChiTietRepo.save(sanPhamChiTiet);
        sanPham.updateSoLuong();
        sanPhamRepo.save(sanPham);
        // Redirect to appropriate page after successful addition
        return "redirect:/san-pham/add-chitiet/"+idSP;
    }
}
