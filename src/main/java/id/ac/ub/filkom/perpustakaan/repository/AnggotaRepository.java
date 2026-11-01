package id.ac.ub.filkom.perpustakaan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import id.ac.ub.filkom.perpustakaan.entity.Anggota;

/** Akses tabel anggota. findAll(), findById(), save() sudah tersedia otomatis. */
public interface AnggotaRepository extends JpaRepository<Anggota, String> {
}