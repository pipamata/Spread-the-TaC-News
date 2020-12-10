package pt.ubi.di.pmd.spread_the_tac_news;

public class Noticia {

    String Titulo;
    String Contexto;
    String autor;
    // imagem
    String data;

    //numero de likes


    public Noticia() {
        Titulo = "";
        Contexto = "";
        this.autor = "";
        this.data = "";
    }
    public Noticia(String titulo, String contexto, String autor, String data) {
        Titulo = titulo;
        Contexto = contexto;
        this.autor = autor;
        this.data = data;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getContexto() {
        return Contexto;
    }

    public String getAutor() {
        return autor;
    }

    public String getData() {
        return data;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public void setContexto(String contexto) {
        Contexto = contexto;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Noticia{" +
                "Titulo='" + Titulo + '\'' +
                ", Contexto='" + Contexto + '\'' +
                ", autor='" + autor + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
