package com.example.banhang.repository;

import com.example.banhang.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SanPhamRepository extends JpaRepository<SanPham,Long> {
    @Query(value = "SELECT * FROM SanPham sp WHERE (sp.Ten = ?1 OR ?1 IS NULL OR ?1 LIKE '')" +
            "AND (sp.TrangThai = ?2 OR ?2 IS NULL OR ?2 LIKE '')",nativeQuery = true)
    Page<SanPham> search(String ten, Boolean trangThai, Pageable pageable);
}
