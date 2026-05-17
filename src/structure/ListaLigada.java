package structure;
import model.Filme;

public class ListaLigada {
    private NoLista cabeca;

    public NoLista getCabeca() {
        return cabeca; }

    public void setCabeca(NoLista cabeca) {
        this.cabeca = cabeca;
    }

    public NoLista inserir(Filme filme) {
        NoLista novo = new NoLista(filme);
        if (cabeca == null) {
            cabeca = novo;
        } else {
            NoLista temp = cabeca;
            while (temp.getProximo() != null) {
                temp = temp.getProximo();
            }
            temp.setProximo(novo);
        }
        return novo;
    }

    public Filme buscaLinear(int id) {
        int comp = 0;
        NoLista temp = cabeca;
        while (temp != null) {
            comp++;
            if (temp.getFilme().getId() == id) {
                System.out.println("[Busca Linear] Comparações: " + comp);
                return temp.getFilme();
            }
            temp = temp.getProximo();
        }
        System.out.println("[Busca Linear] Filme não encontrado após " + comp + " comparações.");
        return null;
    }
}