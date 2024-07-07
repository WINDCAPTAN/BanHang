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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String hienThi(@RequestParam(required = false) String ten,
                          @RequestParam(required = false) Boolean trangThai,
                          @RequestParam(defaultValue = "0") Integer p, Model model) {
        Pageable pageable = PageRequest.of(p, 3);
        Page<SanPham> page ;
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
        return "category/sanpham";
    }

    @GetMapping("/san-pham/add-chitiet/{id}")
    public String hienThiAdd(@PathVariable("id") Long id, Model model) {
        SanPham sanPham = sanPhamRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        Map<String,Object> reponse = new HashMap<>();
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
    public String addSanPham(@RequestParam("ma") String ma,
                             @RequestParam("ten") String ten) {
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
                               @RequestParam("hinhAnh") MultipartFile file) throws IOException {
        // Retrieve SanPham, KichCo, and MauSac from repositories based on ids
        SanPham sanPham = sanPhamRepo.findById(idSP).orElseThrow(() -> new IllegalArgumentException("Invalid SanPham ID: " + idSP));
        KichThuoc kichCo = kichThuocRepo.findById(idKC).orElseThrow(() -> new IllegalArgumentException("Invalid KichCo ID: " + idKC));
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
        sanPhamChiTiet.setNgayTao(new Date(System.currentTimeMillis())); // Set current date

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            sanPhamChiTiet.setHinhAnh(fileName);

            // Create the directory if it doesn't exist
            Path uploadPath = Paths.get("src/main/resources/static/upload/");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, bytes);
        }
        // Save SanPhamChiTiet object using repository
        sanPhamChiTietRepo.save(sanPhamChiTiet);
        sanPham.updateSoLuong();
        sanPhamRepo.save(sanPham);
        // Redirect to appropriate page after successful addition
        return "redirect:/san-pham/add-chitiet/" + idSP;
    }

    @GetMapping("/api/products/image/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        // Tìm kiếm sản phẩm theo ID
        SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietRepo.findById(id).orElse(null);
        if (sanPhamChiTiet != null && sanPhamChiTiet.getHinhAnh() != null) {
            try {
                // Đường dẫn đến thư mục chứa hình ảnh
                Path imagePath = Paths.get("src/main/resources/static/upload/", sanPhamChiTiet.getHinhAnh());
                byte[] imageBytes = Files.readAllBytes(imagePath);

                // Trả về dữ liệu hình ảnh và thiết lập header cho phản hồi
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // hoặc MediaType.IMAGE_PNG tùy vào định dạng hình ảnh
                        .body(imageBytes);
            } catch (IOException e) {
                // Xử lý lỗi nếu không đọc được tệp
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        // Nếu không tìm thấy sản phẩm hoặc không có hình ảnh, trả về 404 Not Found
        return ResponseEntity.notFound().build();
    }


}
