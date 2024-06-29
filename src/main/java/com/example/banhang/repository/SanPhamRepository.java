package com.example.banhang.repository;

import com.example.banhang.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SanPhamRepository extends JpaRepository<SanPham,Long> {
    @Query(value = "select * from SanPham sp WHERE (sp.Ten = ?1 OR ?1 is null OR ?1 LIKE '') " +
            "AND (sp.TrangThai = ?2 OR ?2 is null OR ?2 LIKE '')" , nativeQuery = true)
    Page<SanPham> search(String ten, Boolean trangThai, Pageable pageable);


}
