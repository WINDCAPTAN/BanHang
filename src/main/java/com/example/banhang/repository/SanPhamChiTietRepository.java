package com.example.banhang.repository;


import com.example.banhang.entity.SanPham;
import com.example.banhang.entity.SanPhamChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTiet,Long> {
    List<SanPhamChiTiet> findBySanPham(SanPham sanPham);
}
