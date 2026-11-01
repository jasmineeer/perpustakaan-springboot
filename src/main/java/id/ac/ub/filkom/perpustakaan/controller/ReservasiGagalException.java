package id.ac.ub.filkom.perpustakaan.controller;

/** Alur gagal pada use case Ajukan Reservasi. */
public class ReservasiGagalException extends RuntimeException {

    private ReservasiGagalException(String pesan) {
        super(pesan);
    }

    public static ReservasiGagalException tidakDitemukan(String objek) {
        return new ReservasiGagalException(objek + " tidak ditemukan");
    }

    public static ReservasiGagalException stokTersedia() {
        return new ReservasiGagalException(
                "Stok buku masih tersedia, silakan langsung dipinjam");
    }

    public static ReservasiGagalException sudahMengantre() {
        return new ReservasiGagalException(
                "Anda sudah memiliki reservasi aktif untuk buku ini");
    }
}
