import javax.swing.*;       // (JFrame, JPanel, JButton, JTextField, JList, JLabel, JComboBox, dll. ) - * artinya import semua
import java.awt.*;          // (Layout, Color, Font, dll) - komponen pendukung
import java.awt.event.*;    // (ActionListener, ActionEvent, dll)

public class APKGUI extends JFrame {
    private Hari[] minggu;
    private JComboBox<String> comboHari;
    private DefaultListModel<Aktivitas> model;
    private JList<Aktivitas> listAktivitas;
    private JTextField fieldNama, fieldTanggal, fieldWaktu;
    private JLabel labelJumlah;

    // Constructor - Setup window dan semua komponen
    public APKGUI() {
        minggu = new Hari[]{
            new Hari("Senin"), new Hari("Selasa"), new Hari("Rabu"),
            new Hari("Kamis"), new Hari("Jumat"), new Hari("Sabtu"), new Hari("Minggu")
        };
        
        setupWindow();      // Perintah: Atur properti window
        buatKomponen();     // Perintah: Buat semua komponen GUI
        loadData();         // Perintah: Load data awal
        setVisible(true);   // Perintah: Tampilkan window
    }
    
    // Method - Atur properti dasar window
    private void setupWindow() {
        setTitle("TO DO LIST");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);  // Posisi tengah layar
    }
    
    // Method - Buat semua komponen dan tambahkan ke window
    private void buatKomponen() {
        add(buatPanelAtas(), BorderLayout.NORTH);
        add(buatPanelTengah(), BorderLayout.CENTER);
        add(buatPanelBawah(), BorderLayout.SOUTH);
    }
    
    // Method - Buat panel atas (dropdown & label jumlah)
    private JPanel buatPanelAtas() {
        JPanel panel = new JPanel();

        panel.add(new JLabel("Pilih Hari:"));
        comboHari = new JComboBox<>(new String[]{
            "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"
        });

        comboHari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();   // user pilih hari, program panggil loadData()
            }
        });

        panel.add(comboHari);
        
        // Label jumlah
        labelJumlah = new JLabel("0 aktivitas");
        panel.add(labelJumlah);
        return panel;
    }
    
    // Method - Buat panel tengah (list aktivitas)
    private JScrollPane buatPanelTengah() {
        model = new DefaultListModel<>();
        listAktivitas = new JList<>(model);
        return new JScrollPane(listAktivitas);
    }
    
    // Method - Buat panel bawah (input & tombol)
    private JPanel buatPanelBawah() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        
        // Baris 1: Input nama
        panel.add(new JLabel("Nama:"));
        fieldNama = new JTextField();
        panel.add(fieldNama);
        
        // Baris 2: Input tanggal
        panel.add(new JLabel("Tanggal:"));
        fieldTanggal = new JTextField();
        panel.add(fieldTanggal);
        
        // Baris 3: Input waktu
        panel.add(new JLabel("Waktu:"));
        fieldWaktu = new JTextField();
        panel.add(fieldWaktu);
        
        // Baris 4: Tombol-tombol
        panel.add(buatPanelTombol());
        panel.add(new JLabel("")); // Spacer
        
        return panel;
    }
    
    // Method - Buat panel tombol (3 tombol dalam 1 panel)
    private JPanel buatPanelTombol() {
        JPanel panel = new JPanel();
        
        // Tombol Tambah
        JButton btnTambah = new JButton("Tambah");
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambah();    // saat klik, panggil tambah()
            }
        });
        panel.add(btnTambah);
        
        // Tombol Tandai Selesai
        JButton btnSelesai = new JButton("Tandai Selesai");
        btnSelesai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tandaiSelesai();   // saat klik, panggil tandaiSelesai()
            }
        });
        panel.add(btnSelesai);
        
        // Tombol Hapus
        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapus();    // saat klik, panggil hapus()
            }
        });
        panel.add(btnHapus);
        
        return panel;
    }
    
    // Method - Load data aktivitas dari hari yang dipilih ke list
    private void loadData() {
        int index = comboHari.getSelectedIndex();  // Ambil index hari (0-6)
        Hari hari = minggu[index];                 // Ambil objek Hari yang dipilih
        
        model.clear();  // Hapus data lama
        
        // Loop semua aktivitas dan tambah ke model
        for (Aktivitas a : hari.getList()) {
            model.addElement(a);
        }
        
        // Update label jumlah
        labelJumlah.setText(hari.jumlah() + " aktivitas");
    }
    
    // Method - Tambah aktivitas baru
    private void tambah() {
        // Ambil input dari user
        String nama = fieldNama.getText().trim();
        String tanggal = fieldTanggal.getText().trim();
        String waktu = fieldWaktu.getText().trim();
        
        // Validasi: cek input tidak kosong
        if (nama.isEmpty() || tanggal.isEmpty() || waktu.isEmpty()) {
            showMessage("Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Tambah ke hari yang dipilih
        int index = comboHari.getSelectedIndex();
        minggu[index].tambah(nama, tanggal, waktu);
        
        // Clear input
        clearFields();
        
        // Refresh tampilan
        loadData();
        
        showMessage("Aktivitas berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Method - Hapus aktivitas yang dipilih
    private void hapus() {
        int selected = listAktivitas.getSelectedIndex();
        
        // Validasi: cek ada yang dipilih
        if (selected == -1) {
            showMessage("Pilih aktivitas terlebih dahulu!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Konfirmasi hapus
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus aktivitas ini?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Hapus dari data
            int index = comboHari.getSelectedIndex();
            minggu[index].hapus(selected);
            
            // Refresh tampilan
            loadData();
            
            showMessage("Aktivitas berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Method - Tandai aktivitas selesai/belum
    private void tandaiSelesai() {
        int selected = listAktivitas.getSelectedIndex();
        
        // Validasi: cek ada yang dipilih
        if (selected == -1) {
            showMessage("Pilih aktivitas terlebih dahulu!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Toggle status selesai
        int index = comboHari.getSelectedIndex();
        Aktivitas a = minggu[index].get(selected);
        a.setSelesai(!a.isSelesai());  // Kebalikan dari status sekarang
        
        // Refresh tampilan
        loadData();
    }
    
    // Helper Method - Kosongkan semua input field
    private void clearFields() {
        fieldNama.setText("");
        fieldTanggal.setText("");
        fieldWaktu.setText("");
    }
    
    // Helper Method - Tampilkan popup message (untuk hindari duplikasi kode)
    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }

public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable()  {
            @Override
            public void run() {
                new APKGUI();
            }
        });
    }
}