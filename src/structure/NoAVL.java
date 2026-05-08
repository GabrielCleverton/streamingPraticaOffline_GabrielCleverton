package structure;

import model.Filme;

public class NoAVL {
    private Filme filme;
    private int ultimoAcesso;
    private int altura;
    private NoAVL dir, esq;

    public NoAVL(Filme filme){
        this.filme = filme;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(int ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public NoAVL getDir() {
        return dir;
    }

    public void setDir(NoAVL dir) {
        this.dir = dir;
    }

    public NoAVL getEsq() {
        return esq;
    }

    public void setEsq(NoAVL esq) {
        this.esq = esq;
    }
}
