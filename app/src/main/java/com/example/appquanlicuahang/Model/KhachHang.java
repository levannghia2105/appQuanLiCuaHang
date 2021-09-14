package com.example.appquanlicuahang.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class KhachHang {
    private  int id ;
    private String tenKhachHang ;
    private String ngaySinh ;
    private String sdt ;
    private String diaChi ;
    private int gioiTinh ;
    private String moTa ;
    private  int soLancat = 0 ;

    public int getSoLancat() {
        return soLancat;
    }

    public void setSoLancat(int soLancat) {
        this.soLancat = soLancat;
    }

    public KhachHang(JSONObject o ){
        try {
            this.id = o.getInt("idKH");
            this.tenKhachHang= o.getString("tenKH");
            this.ngaySinh = o.getString("ngaySinh");
            this.sdt = o.getString("sdt");
            this.diaChi = o.getString("diaChi");
            this.gioiTinh = o.getInt("gt");
            this.moTa = o.getString("mota");


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public KhachHang(int id, String tenKhachHang, String ngaySinh, String sdt, String diaChi, int gioiTinh, String moTa) {
        this.id = id;
        this.tenKhachHang = tenKhachHang;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(int gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

}
