package id.ac.ub.filkom.perpustakaan.boundary;

import id.ac.ub.filkom.perpustakaan.controller.StokGagalException;
import id.ac.ub.filkom.perpustakaan.controller.StokService;
import id.ac.ub.filkom.perpustakaan.entity.Buku;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * «boundary» HalamanStok (bagian penerima request) untuk aktor Pustakawan.
 * Hanya membaca input, memanggil method milik control, memilih template.
 * Nomor [n] mengacu ke sequence diagram "Kelola Stok Buku".
 */
@Controller
@RequestMapping("/stok")
public class HalamanStokController {

    private final StokService stokService;          // «control»

    public HalamanStokController(StokService stokService) {
        this.stokService = stokService;
    }

    /** [1] bukaHalamanStok() -> [2] daftarBuku() -> [3] -> [4] tampilkan daftar & stok */
    @GetMapping
    public String tampilHalaman(Model model) {
        model.addAttribute("daftarBuku", stokService.daftarBuku());   // [2]
        return "stok";                                                // [4] templates/stok.html
    }

    /** [5] tambahStok(idBuku, jumlah) dari aktor Pustakawan. */
    @PostMapping("/tambah")
    public String tambahStok(@RequestParam String idBuku,
                             @RequestParam int jumlah,
                             RedirectAttributes redirect) {
        try {
            Buku buku = stokService.tambahStok(idBuku, jumlah);       // [6]
            redirect.addFlashAttribute("pesanSukses",
                    "Stok \"" + buku.getJudul() + "\" menjadi " + buku.getStok()); // [10]
        } catch (StokGagalException e) {                              // [11] pesanGagal
            redirect.addFlashAttribute("pesanError", e.getMessage()); // [12]
        }
        return "redirect:/stok";                                      // pola Post-Redirect-Get
    }

    /** [13] aturStok(idBuku, stokBaru) dari aktor Pustakawan. */
    @PostMapping("/atur")
    public String aturStok(@RequestParam String idBuku,
                           @RequestParam int stokBaru,
                           RedirectAttributes redirect) {
        try {
            Buku buku = stokService.aturStok(idBuku, stokBaru);       // [14]
            redirect.addFlashAttribute("pesanSukses",
                    "Stok \"" + buku.getJudul() + "\" dikoreksi menjadi " + buku.getStok()); // [18]
        } catch (StokGagalException e) {                              // [19] pesanGagal
            redirect.addFlashAttribute("pesanError", e.getMessage()); // [20]
        }
        return "redirect:/stok";
    }
}
