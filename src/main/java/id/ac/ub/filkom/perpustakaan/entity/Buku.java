package id.ac.ub.filkom.perpustakaan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** «entity» Buku — tabel buku (skema 8a). */
@Entity
@Table(name = "buku")
public class Buku {

    @Id
    @Column(name = "id_buku", length = 10)
    private String idBuku;

    @Column(name = "judul", length = 200)
    private String judul;

    @Column(name = "stok")
    private int stok;

    protected Buku() { }  // wajib untuk JPA

    public Buku(String idBuku, String judul, int stok) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.stok = stok;
    }

    /** +cekStok() : boolean  (dipakai use case Pinjam Buku & Ajukan Reservasi) */
    public boolean cekStok() {
        return stok > 0;
    }

    /** +kurangiStok() : void  (dipakai use case Pinjam Buku) */
    public void kurangiStok() {
        if (stok <= 0) {
            throw new IllegalStateException("Stok sudah habis");
        }
        stok--;
    }

    /** +tambahStok(jumlah : int) : void  (BARU - use case Kelola Stok Buku) */
    public void tambahStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah penambahan stok harus lebih dari 0");
        }
        stok += jumlah;
    }

    /** +aturStok(stokBaru : int) : void  (BARU - koreksi stok oleh Pustakawan) */
    public void aturStok(int stokBaru) {
        if (stokBaru < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }
        stok = stokBaru;
    }

    public String getIdBuku() { return idBuku; }
    public String getJudul() { return judul; }
    public int getStok() { return stok; }
}
