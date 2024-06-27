package com.example.banhang.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "KichCo")
@Entity
public class KichCo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long id;
//    @Column (name = "Ma")
//    private  String ma;
    @Column (name = "Ten")
    private String ten;
    @Column (name = "TrangThai")
    private String trangThai;
    @Column (name = "NgayTao")
    private Date ngayTao;
    @Column (name = "NgaySua")
    private Date ngaySua;
}
