package com.example.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
public class TaiKhoanAdmin extends TaiKhoan {

}