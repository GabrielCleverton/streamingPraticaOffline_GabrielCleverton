package structure;

public class TabelaHash {
    private int M = 1009;
    private NoHash[] tabela = new NoHash[M];

    public int getM() {
        return M;
    }
    public void setM(int m) {
        M = m;
    }

    public NoHash[] getTabela() {
        return tabela;
    }
    public void setTabela(NoHash[] tabela) {
        this.tabela = tabela;
    }

    public int hash(int id){
        return id % M;
    }

    public void inserir(int id, NoLista referencia){
        int h = hash(id);
        NoHash no = tabela[h];
        while (no != null){
            if (no.getId() == id){
                return;
            }
            no = no.getProximo();
        }
        NoHash novoNo = new NoHash(id, referencia);
        novoNo.setProximo(tabela[h]);
        tabela[h] = novoNo;
    }

    public NoLista buscar(int id){
        int comparacoes = 0;
        int h = hash(id);
        NoHash no = tabela[h];
        while (no != null){
            comparacoes++;
            if (no.getId() == id){
                System.out.println("Comparações (Tabela Hash): " + comparacoes);
                return no.getReferencia();
            }
            no = no.getProximo();
        }
        return null;
    }
}
