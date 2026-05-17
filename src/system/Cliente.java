package system;
import model.Filme;
import structure.ArvoreAVL;

public class Cliente {
    private ArvoreAVL cache = new ArvoreAVL();
    private Servidor servidor;
    private int tamanhoCache = 0;

    public Cliente(Servidor s) { this.servidor = s; }

    public Filme buscar(int id) {
        Filme f = cache.buscar(id);
        if (f != null) {
            System.out.println("-> [CACHE HIT] Filme " + f.getNome() + " encontrado na AVL.");
            return f;
        }

        System.out.println("-> [CACHE MISS] Buscando ID " + id + " no servidor via Hash...");
        f = servidor.buscarComHash(id);

        if (f != null) {
            if (tamanhoCache >= 50) {
                System.out.println("-> [EVICTION] Cache cheio. Removendo via LRU...");
                cache.evictLRU();
                tamanhoCache--;
            }
            cache.inserir(f);
            tamanhoCache++;
        }
        return f;
    }
}