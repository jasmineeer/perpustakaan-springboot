package id.ac.ub.filkom.perpustakaan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

/** «entity» Reservasi — tabel reservasi (use case «extend» Ajukan Reservasi). */
@Entity
@Table(name = "reservasi")
public class Reservasi {

    public static final String MENUNGGU = "MENUNGGU";
    public static final String DIBATALKAN = "DIBATALKAN";
    public static final String TERPENUHI = "TERPENUHI";

    @Id
    @Column(name = "id_reservasi", length = 10)
    private String idReservasi;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_anggota")      // FK -> anggota
    private Anggota anggota;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_buku")         // FK -> buku
    private Buku buku;

    @Column(name = "tanggal_reservasi")
    private LocalDate tanggalReservasi;

    @Column(name = "status", length = 20)
    private String status;

    protected Reservasi() { }  // wajib untuk JPA

    /** +buat(anggota, buku) : Reservasi */
    public static Reservasi buat(Anggota anggota, Buku buku) {
        if (anggota == null || buku == null) {
            throw new IllegalArgumentException("Anggota dan buku wajib diisi");
        }
        Reservasi r = new Reservasi();
        // id 9 karakter, contoh: R3F9A1C2B
        r.idReservasi = "R" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        r.anggota = anggota;
        r.buku = buku;
        r.tanggalReservasi = LocalDate.now();
        r.status = MENUNGGU;
        return r;
    }

    /** +isMenunggu() : boolean */
    public boolean isMenunggu() {
        return MENUNGGU.equals(status);
    }

    /** +batalkan() : void — hanya reservasi MENUNGGU yang boleh dibatalkan */
    public void batalkan() {
        if (!isMenunggu()) {
            throw new IllegalStateException("Hanya reservasi berstatus MENUNGGU yang dapat dibatalkan");
        }
        status = DIBATALKAN;
    }

    /** +tandaiTerpenuhi() : void — dipanggil saat buku sudah tersedia untuk anggota */
    public void tandaiTerpenuhi() {
        if (!isMenunggu()) {
            throw new IllegalStateException("Hanya reservasi berstatus MENUNGGU yang dapat dipenuhi");
        }
        status = TERPENUHI;
    }

    public String getIdReservasi() { return idReservasi; }
    public Anggota getAnggota() { return anggota; }
    public Buku getBuku() { return buku; }
    public LocalDate getTanggalReservasi() { return tanggalReservasi; }
    public String getStatus() { return status; }
}
