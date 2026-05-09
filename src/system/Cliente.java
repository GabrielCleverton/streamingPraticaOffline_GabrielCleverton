package system;

import model.Filme;
import structure.ArvoreAVL;

public class Cliente {
    private ArvoreAVL cache;
    private Servidor servidor;
    private int tamanhoCache = 0;
    private static final int LIMITE = 50;

    public Cliente(Servidor servidor){
        this.cache = new ArvoreAVL();
        this.servidor = servidor;
    }

    public Filme buscar(int id){
        Filme filme = cache.buscar(id);
        if (filme != null){
            System.out.println("Cache hit!");
            return filme;
        }
        System.out.println("Cache miss! Buscando no servidor...");
        filme = servidor.buscarComHash(id);
        if (filme != null){
            if (tamanhoCache >= LIMITE){
                cache.evictLRU();
                tamanhoCache--;
            }
            cache.inserir(filme);
            tamanhoCache++;
        }
        return filme;
    }
}