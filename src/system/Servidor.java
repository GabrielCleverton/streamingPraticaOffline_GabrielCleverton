package system;
import model.Categoria;
import model.Filme;
import structure.ListaLigada;
import structure.NoLista;
import structure.TabelaHash;

public class Servidor {
    private ListaLigada listaGeral = new ListaLigada();
    private TabelaHash hash = new TabelaHash();
    private ListaLigada[] listasPorCategoria;

    public Servidor() {
        listasPorCategoria = new ListaLigada[Categoria.values().length];
        for (int i = 0; i < listasPorCategoria.length; i++) {
            listasPorCategoria[i] = new ListaLigada();
        }
    }

    public void inserirFilme(Filme f) {
        NoLista noNaLista = listaGeral.inserir(f);
        listasPorCategoria[f.getCategoria().ordinal()].inserir(f);
        hash.inserir(f.getId(), noNaLista);
    }

    public Filme buscarPorNomeLinear(String nome) {
        NoLista temp = listaGeral.getCabeca();
        while (temp != null) {
            if (temp.getFilme().getNome().equalsIgnoreCase(nome.trim())) {
                return temp.getFilme();
            }
            temp = temp.getProximo();
        }
        return null;
    }

    public Filme buscarComHash(int id) {
        NoLista n = hash.buscar(id);
        return (n != null) ? n.getFilme() : null;
    }

    public Filme buscarSemHash(int id) {
        return listaGeral.buscaLinear(id);
    }

    public ListaLigada getListaPorCategoria(Categoria cat) {
        return listasPorCategoria[cat.ordinal()];
    }
}