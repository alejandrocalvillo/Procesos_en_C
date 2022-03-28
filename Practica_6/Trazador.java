public class Trazador {

    private int id;
    private String tabs;

    public Trazador(int id, int ntabs) {
       this.id = id;
       this.tabs = "";
       Crear_tabs(ntabs); 
    }
    
    public void Crear_tabs(int n) {
        int k;
        for (k = 0; k < n * 2; k++) this.tabs += '\t';
    }

    public void Trazar(String s) {
        System.out.println(tabs + "[" + this.id + "] " + s);
    }

    public void Trazar(String s, int d) {
    	System.out.println(tabs + "[" + this.id + "] " + s + ": " + d);
    }

    public void Trazar(String s1, String s2) {
    	System.out.println(tabs + "[" + this.id + "] " + s1 + ": " + s2);
    }

    public void Trazar(String s1, float f) {
    	System.out.println(tabs + "[" + this.id + "] " + s1 + ": " + f);
    }
    
    public void Trazar(String s, double d) {
    	System.out.println(tabs + "[" + this.id + "] " + s + ": " + d);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}