import java.util.ArrayList;

class Hari {
    private String hari;
    private ArrayList<Aktivitas> list;
    
    public Hari(String hari) {
        this.hari = hari;
        this.list = new ArrayList<>();
    }
    
    public String getHari() {
        return hari;
    }
    
    public void tambah(String nama, String tanggal, String waktu) {
        list.add(new Aktivitas(nama, tanggal, waktu));
    }
    
    public void hapus(int index) {
        if (index >= 0 && index < list.size()) {
            list.remove(index);
        }
    }
    
    public Aktivitas get(int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }
    
    public int jumlah() {
        return list.size();
    }
    
    public ArrayList<Aktivitas> getList() {
        return list;
    }
}