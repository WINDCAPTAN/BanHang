package com.example.banhang.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ChiTietSanPham")
public class SanPhamCT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "GiaBan")
    private String giaBan;
    @Column(name = "MaVach")
    private String maVach;
    @Column(name = "MoTa")
    private  String moTa;
    @Column(name = "HinhAnh")
    private String hinhAnh;

    @Column(name = "SoLuong")
    private String soLuong;
    @Column(name = "TrangThai")
    private String trangThai;
    @Column(name = "NgayTao")
    private Date ngayTao;
    @Column(name = "NgaySua")
    private Date ngaySua;
    @ManyToOne
    @JoinColumn( name = "ID_KichCo")
    private KichCo kichCo;
    @ManyToOne
    @JoinColumn( name = "ID_SanPham")
    private SanPham sanPham;
    @ManyToOne
    @JoinColumn( name = "ID_MauSac")
    private MauSac mauSac;
}
