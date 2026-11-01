package id.ac.ub.filkom.perpustakaan.controller;

import id.ac.ub.filkom.perpustakaan.entity.Reservasi;

import java.time.LocalDate;

/** Data hasil (tipe kembalian) use case Ajukan Reservasi — kelas biasa. */
public class BuktiReservasi {

    private final String idReservasi;
    private final String namaAnggota;
    private final String judulBuku;
    private final LocalDate tanggalReservasi;
    private final String status;

    private BuktiReservasi(String idReservasi, String namaAnggota,
                           String judulBuku, LocalDate tanggalReservasi,
                           String status) {
        this.idReservasi = idReservasi;
        this.namaAnggota = namaAnggota;
        this.judulBuku = judulBuku;
        this.tanggalReservasi = tanggalReservasi;
        this.status = status;
    }

    public static BuktiReservasi dari(Reservasi r) {
        return new BuktiReservasi(r.getIdReservasi(),
                r.getAnggota().getNama(), r.getBuku().getJudul(),
                r.getTanggalReservasi(), r.getStatus());
    }

    public String getIdReservasi() { return idReservasi; }
    public String getNamaAnggota() { return namaAnggota; }
    public String getJudulBuku() { return judulBuku; }
    public LocalDate getTanggalReservasi() { return tanggalReservasi; }
    public String getStatus() { return status; }
}
