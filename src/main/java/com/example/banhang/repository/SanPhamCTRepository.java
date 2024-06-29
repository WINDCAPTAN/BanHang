package com.example.banhang.repository;

import com.example.banhang.entity.SanPhamCT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SanPhamCTRepository extends JpaRepository<SanPhamCT, Long> {
}
