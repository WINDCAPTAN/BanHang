package com.example.banhang.repository;


import com.example.banhang.entity.ChatLieu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatLieuRepository extends JpaRepository<ChatLieu,Long> {
    List<ChatLieu> findAllByOrderByNgayTaoDesc();
}
