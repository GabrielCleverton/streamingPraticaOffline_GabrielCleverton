package system;

import model.Filme;
import structure.ListaLigada;
import structure.NoLista;
import structure.TabelaHash;

public class Servidor {
    private ListaLigada lista;
    private TabelaHash hash;

    public Servidor(){
        lista = new ListaLigada();
        hash = new TabelaHash();
    }

    public void inserirFilme(Filme filme){
        lista.inserir(filme);
        NoLista noLista = lista.getCabeca();
        NoLista temp = noLista;
        while (temp.getProximo() != null){
            temp = temp.getProximo();
        }
        hash.inserir(filme.getId(), temp);
    }

    public Filme buscarComHash(int id){
        NoLista noLista = hash.buscar(id);
        if (noLista == null) return null;
        return noLista.getFilme();
    }

    public Filme buscarSemHash(int id){
        return lista.buscaLinear(id);
    }
}
