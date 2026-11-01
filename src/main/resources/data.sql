INSERT INTO buku (id_buku, judul, stok) VALUES ('B001', 'Pemrograman Java', 3);
INSERT INTO buku (id_buku, judul, stok) VALUES ('B002', 'Rekayasa Perangkat Lunak', 0);
INSERT INTO buku (id_buku, judul, stok) VALUES ('B003', 'Basis Data', 2);

INSERT INTO anggota (id_anggota, nama, batas_pinjam) VALUES ('A001', 'Budi Santoso', 2);
INSERT INTO anggota (id_anggota, nama, batas_pinjam) VALUES ('A002', 'Sari Dewi', 1);

CREATE TABLE reservasi (
    id_reservasi      VARCHAR(10) PRIMARY KEY,
    id_anggota        VARCHAR(10) NOT NULL,
    id_buku           VARCHAR(10) NOT NULL,
    tanggal_reservasi DATE        NOT NULL,
    status            VARCHAR(20) NOT NULL,
    CONSTRAINT fk_reservasi_anggota FOREIGN KEY (id_anggota) REFERENCES anggota (id_anggota),
    CONSTRAINT fk_reservasi_buku    FOREIGN KEY (id_buku)    REFERENCES buku (id_buku)
);