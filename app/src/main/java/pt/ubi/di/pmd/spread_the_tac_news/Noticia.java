package pt.ubi.di.pmd.spread_the_tac_news;

public class Noticia {

    String titulo;
    String contexto;
    String autor;
    String data;

    public Noticia() {
        titulo = "";
        contexto = "";
        this.autor = "";
        this.data = "";
    }

    public Noticia(String titulo, String contexto, String autor, String data) {
        this.titulo = titulo;
        this.contexto = contexto;
        this.autor = autor;
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContexto() {
        return contexto;
    }

    public String getAutor() {
        return autor;
    }

    public String getData() {
        return data;
    }

    public void setTitulo(String titulo) {
        titulo = titulo;
    }

    public void setContexto(String contexto) {
        contexto = contexto;
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
                "Titulo='" + titulo + '\'' +
                ", Contexto='" + contexto + '\'' +
                ", autor='" + autor + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
