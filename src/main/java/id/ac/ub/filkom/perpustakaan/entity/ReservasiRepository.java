package id.ac.ub.filkom.perpustakaan.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/** Akses tabel reservasi (implementasi otomatis Spring Data JPA). */
public interface ReservasiRepository extends JpaRepository<Reservasi, String> {

    /** Cegah anggota mengantre dua kali untuk buku yang sama. */
    boolean existsByAnggota_IdAnggotaAndBuku_IdBukuAndStatus(
            String idAnggota, String idBuku, String status);
}
