package id.ac.ub.filkom.perpustakaan.controller;

/** Alur gagal pada use case Kelola Stok Buku. */
public class StokGagalException extends RuntimeException {

    private StokGagalException(String pesan) {
        super(pesan);
    }

    public static StokGagalException tidakDitemukan(String objek) {
        return new StokGagalException(objek + " tidak ditemukan");
    }

    public static StokGagalException jumlahTidakValid() {
        return new StokGagalException("Jumlah tambahan stok harus lebih dari 0");
    }

    public static StokGagalException stokNegatif() {
        return new StokGagalException("Stok tidak boleh negatif");
    }
}
