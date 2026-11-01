package id.ac.ub.filkom.perpustakaan.controller;

import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.repository.BukuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * «control» use case Kelola Stok Buku (aktor: Pustakawan).
 * Direalisasikan sebagai @Service (BUKAN @Controller).
 * Nomor [n] mengacu ke sequence diagram "Kelola Stok Buku".
 */
@Service
public class StokService {

    private final BukuRepository bukuRepository;

    public StokService(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    /** [2] daftarBuku() -> [3] daftar buku */
    public List<Buku> daftarBuku() {
        return bukuRepository.findAll();
    }

    /** [6] tambahStok(idBuku, jumlah) */
    @Transactional
    public Buku tambahStok(String idBuku, int jumlah) {
        // Persiapan: ambil objek entity dari database (tidak digambar di sequence)
        Buku buku = ambilBuku(idBuku);

        // alt [jumlah <= 0]
        if (jumlah <= 0) {
            throw StokGagalException.jumlahTidakValid();   // [11] pesanGagal
        }

        // alt [jumlah > 0]
        buku.tambahStok(jumlah);                           // [7] tambahStok(jumlah) -> [8] stok bertambah
        return bukuRepository.save(buku);                  // [9] stok terbaru
    }

    /** [14] aturStok(idBuku, stokBaru) */
    @Transactional
    public Buku aturStok(String idBuku, int stokBaru) {
        // Persiapan: ambil objek entity dari database (tidak digambar di sequence)
        Buku buku = ambilBuku(idBuku);

        // alt [stokBaru < 0]
        if (stokBaru < 0) {
            throw StokGagalException.stokNegatif();        // [19] pesanGagal
        }

        // alt [stokBaru >= 0]
        buku.aturStok(stokBaru);                           // [15] aturStok(stokBaru) -> [16] ok
        return bukuRepository.save(buku);                  // [17] stok terbaru
    }

    private Buku ambilBuku(String idBuku) {
        return bukuRepository.findById(idBuku)
                .orElseThrow(() -> StokGagalException.tidakDitemukan("Buku"));
    }
}
