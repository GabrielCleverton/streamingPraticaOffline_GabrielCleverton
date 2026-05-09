package structure;

import model.Filme;

public class ListaLigada {
    private NoLista cabeca;

    public NoLista getCabeca() {
        return cabeca;
    }

    public void setCabeca(NoLista cabeca) {
        this.cabeca = cabeca;
    }

    public void inserir(Filme filme){
        NoLista novoFilme = new NoLista(filme);
        if (getCabeca() == null){
            setCabeca(novoFilme);
        }else {
            NoLista temp = getCabeca();
            while (temp.getProximo()!= null){
                temp = temp.getProximo();
            }
            temp.setProximo(novoFilme);

        }
    }

    public Filme buscaLinear(int id){
        int comparacoes = 0;
        NoLista temp = getCabeca();
        while (temp!=null){
            comparacoes++;
            if (temp.getFilme().getId() == id){
                System.out.println("Comparações: " + comparacoes);
                return temp.getFilme();
            }
            temp = temp.getProximo();
        }
        return null;
    }
}
