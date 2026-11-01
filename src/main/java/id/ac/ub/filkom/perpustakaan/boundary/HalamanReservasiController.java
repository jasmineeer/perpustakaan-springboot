package id.ac.ub.filkom.perpustakaan.boundary;

import id.ac.ub.filkom.perpustakaan.controller.BuktiReservasi;
import id.ac.ub.filkom.perpustakaan.controller.ReservasiGagalException;
import id.ac.ub.filkom.perpustakaan.controller.ReservasiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * «boundary» HalamanReservasi (bagian penerima request) untuk aktor Anggota.
 * Dipicu dari tautan/tombol "Ajukan Reservasi" pada pinjam.html («extend»).
 * Nomor [n] mengacu ke sequence diagram "Ajukan Reservasi".
 */
@Controller
@RequestMapping("/reservasi")
public class HalamanReservasiController {

    private final ReservasiService reservasiService;   // «control»

    public HalamanReservasiController(ReservasiService reservasiService) {
        this.reservasiService = reservasiService;
    }

    /** [1] ajukanReservasi(idBuku) dari aktor Anggota. */
    @PostMapping
    public String ajukanReservasi(@RequestParam String idAnggota,
                                  @RequestParam String idBuku,
                                  Model model) {
        try {
            // [2] ajukanReservasi(idAnggota, idBuku)
            BuktiReservasi bukti = reservasiService.ajukanReservasi(idAnggota, idBuku);
            model.addAttribute("bukti", bukti);
            return "reservasi";                       // [8] tampilkan bukti reservasi
        } catch (ReservasiGagalException e) {         // [9] pesanGagal
            model.addAttribute("pesan", e.getMessage());
            return "reservasi";                       // [10] tampilkan pesan & opsi pinjam
        }
    }
}
