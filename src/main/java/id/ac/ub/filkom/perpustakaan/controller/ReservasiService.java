package id.ac.ub.filkom.perpustakaan.controller;

import id.ac.ub.filkom.perpustakaan.entity.Anggota;
import id.ac.ub.filkom.perpustakaan.repository.AnggotaRepository;
import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.repository.BukuRepository;
import id.ac.ub.filkom.perpustakaan.entity.Reservasi;
import id.ac.ub.filkom.perpustakaan.entity.ReservasiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * «control» use case «extend» Ajukan Reservasi (aktor: Anggota).
 * Diperluas dari Pinjam Buku pada alur alt [stok habis].
 * Nomor [n] mengacu ke sequence diagram "Ajukan Reservasi".
 */
@Service
public class ReservasiService {

    private final AnggotaRepository anggotaRepository;
    private final BukuRepository bukuRepository;
    private final ReservasiRepository reservasiRepository;

    public ReservasiService(AnggotaRepository anggotaRepository,
                            BukuRepository bukuRepository,
                            ReservasiRepository reservasiRepository) {
        this.anggotaRepository = anggotaRepository;
        this.bukuRepository = bukuRepository;
        this.reservasiRepository = reservasiRepository;
    }

    /** [2] ajukanReservasi(idAnggota, idBuku) */
    @Transactional
    public BuktiReservasi ajukanReservasi(String idAnggota, String idBuku) {
        // Persiapan: ambil objek entity dari database (tidak digambar di sequence)
        Anggota anggota = anggotaRepository.findById(idAnggota)
                .orElseThrow(() -> ReservasiGagalException.tidakDitemukan("Anggota"));
        Buku buku = bukuRepository.findById(idBuku)
                .orElseThrow(() -> ReservasiGagalException.tidakDitemukan("Buku"));

        // [3] cekStok() -> [4] stok habis / stok tersedia
        // alt [stok tersedia atau sudah mengantre]
        if (buku.cekStok()) {
            throw ReservasiGagalException.stokTersedia();      // [9] pesanGagal
        }
        if (reservasiRepository.existsByAnggota_IdAnggotaAndBuku_IdBukuAndStatus(
                idAnggota, idBuku, Reservasi.MENUNGGU)) {
            throw ReservasiGagalException.sudahMengantre();    // [9] pesanGagal
        }

        // alt [stok habis dan belum mengantre]
        // [5] buat(idAnggota, idBuku) -> [6] reservasi tersimpan
        Reservasi reservasi = Reservasi.buat(anggota, buku);
        reservasiRepository.save(reservasi);

        return BuktiReservasi.dari(reservasi);                 // [7] bukti reservasi
    }
}
