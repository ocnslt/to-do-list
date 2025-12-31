class Aktivitas {
    private String nama;
    private String tanggal;
    private String waktu;
    private boolean selesai;
    
    public Aktivitas(String nama, String tanggal, String waktu) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.selesai = false;
    }
    
    public String getNama() {
        return nama;
    }
    
    public String getTanggal() {
        return tanggal;
    }
    
    public String getWaktu() {
        return waktu;
    }
    
    public boolean isSelesai() {
        return selesai;
    }
    
    public void setSelesai(boolean selesai) {
        this.selesai = selesai;
    }

    public String toString() {
        String cek = selesai ? "[x]" : "[ ]";       // ternary operator
        return cek + " " + nama + " - " + tanggal + " " + waktu;
    }
}