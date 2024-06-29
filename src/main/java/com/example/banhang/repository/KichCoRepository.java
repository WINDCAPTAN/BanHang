package com.example.banhang.repository;

import com.example.banhang.entity.KichThuoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KichCoRepository extends JpaRepository<KichThuoc, Long> {
}
